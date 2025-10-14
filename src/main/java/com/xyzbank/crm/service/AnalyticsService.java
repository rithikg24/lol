package com.xyzbank.crm.service;

import com.xyzbank.crm.domain.Customer;
import com.xyzbank.crm.domain.Interaction;
import com.xyzbank.crm.domain.Lead;
import com.xyzbank.crm.domain.ServiceRequest;
import com.xyzbank.crm.domain.enums.LeadStatus;
import com.xyzbank.crm.repository.CustomerRepository;
import com.xyzbank.crm.repository.InteractionRepository;
import com.xyzbank.crm.repository.LeadRepository;
import com.xyzbank.crm.repository.ServiceRequestRepository;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.Instant;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class AnalyticsService {
    private final CustomerRepository customerRepository;
    private final InteractionRepository interactionRepository;
    private final LeadRepository leadRepository;
    private final ServiceRequestRepository serviceRequestRepository;

    public AnalyticsService(CustomerRepository customerRepository,
                            InteractionRepository interactionRepository,
                            LeadRepository leadRepository,
                            ServiceRequestRepository serviceRequestRepository) {
        this.customerRepository = customerRepository;
        this.interactionRepository = interactionRepository;
        this.leadRepository = leadRepository;
        this.serviceRequestRepository = serviceRequestRepository;
    }

    public long totalCustomers() {
        return customerRepository.count();
    }

    public long interactionsInLastDays(int days) {
        Instant cutoff = Instant.now().minus(Duration.ofDays(days));
        return interactionRepository.findAll().stream()
            .filter(i -> i.getInteractionTime() != null && i.getInteractionTime().isAfter(cutoff))
            .count();
    }

    public Map<LeadStatus, Long> leadStatusCounts() {
        return leadRepository.findAll().stream()
            .collect(Collectors.groupingBy(Lead::getStatus, Collectors.counting()));
    }

    public long openServiceRequests() {
        return serviceRequestRepository.findAll().stream()
            .filter(sr -> sr.getStatus() != null && !"CLOSED".equals(sr.getStatus().name()))
            .count();
    }

    public List<String> simpleRecommendationsForTopCustomers(int limit) {
        List<Customer> customers = customerRepository.findAll();
        Map<Long, Long> interactionCounts = interactionRepository.findAll().stream()
            .collect(Collectors.groupingBy(i -> i.getCustomer().getId(), Collectors.counting()));

        // Sort customers by interactions desc
        List<Customer> sorted = customers.stream()
            .sorted((a, b) -> Long.compare(
                interactionCounts.getOrDefault(b.getId(), 0L),
                interactionCounts.getOrDefault(a.getId(), 0L)))
            .limit(limit)
            .toList();

        // Very naive rules-based suggestions
        List<Lead> leads = leadRepository.findAll();
        List<String> results = new ArrayList<>();
        for (Customer customer : sorted) {
            boolean hasActiveCardLead = leads.stream()
                .anyMatch(l -> l.getCustomer().getId().equals(customer.getId()) &&
                                l.getProduct().toLowerCase().contains("card") &&
                                l.getStatus() != LeadStatus.CONVERTED);
            String suggestion = hasActiveCardLead ? "Savings Plus" : "Credit Card";
            results.add(customer.getFirstName() + " " + customer.getLastName() + " → " + suggestion);
        }
        return results;
    }
}
