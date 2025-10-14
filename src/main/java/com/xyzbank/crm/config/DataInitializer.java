package com.xyzbank.crm.config;

import com.xyzbank.crm.domain.*;
import com.xyzbank.crm.domain.enums.Channel;
import com.xyzbank.crm.domain.enums.InteractionType;
import com.xyzbank.crm.domain.enums.LeadStatus;
import com.xyzbank.crm.domain.enums.ServiceStatus;
import com.xyzbank.crm.repository.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Instant;

@Configuration
public class DataInitializer {
    @Bean
    CommandLineRunner loadData(CustomerRepository customers,
                               InteractionRepository interactions,
                               LeadRepository leads,
                               ServiceRequestRepository serviceRequests) {
        return args -> {
            if (customers.count() > 0) return;

            Customer alice = new Customer();
            alice.setFirstName("Alice");
            alice.setLastName("Johnson");
            alice.setEmail("alice@example.com");
            alice.setPhone("1234567890");
            customers.save(alice);

            Customer bob = new Customer();
            bob.setFirstName("Bob");
            bob.setLastName("Smith");
            bob.setEmail("bob@example.com");
            bob.setPhone("0987654321");
            customers.save(bob);

            Interaction i1 = new Interaction();
            i1.setCustomer(alice);
            i1.setType(InteractionType.INQUIRY);
            i1.setChannel(Channel.WEB);
            i1.setNotes("Asked about credit card.");
            i1.setInteractionTime(Instant.now());
            interactions.save(i1);

            Interaction i2 = new Interaction();
            i2.setCustomer(alice);
            i2.setType(InteractionType.FOLLOW_UP);
            i2.setChannel(Channel.EMAIL);
            i2.setNotes("Sent brochure.");
            interactions.save(i2);

            Interaction i3 = new Interaction();
            i3.setCustomer(bob);
            i3.setType(InteractionType.SUPPORT);
            i3.setChannel(Channel.PHONE);
            i3.setNotes("Reported card issue.");
            interactions.save(i3);

            Lead l1 = new Lead();
            l1.setCustomer(alice);
            l1.setProduct("Credit Card");
            l1.setStatus(LeadStatus.CONTACTED);
            leads.save(l1);

            ServiceRequest s1 = new ServiceRequest();
            s1.setCustomer(bob);
            s1.setSubject("Card not working");
            s1.setDescription("Card declined at POS.");
            s1.setStatus(ServiceStatus.OPEN);
            serviceRequests.save(s1);
        };
    }
}
