package com.xyzbank.crm.service;

import com.xyzbank.crm.domain.ServiceRequest;
import com.xyzbank.crm.repository.ServiceRequestRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ServiceRequestService {
    private final ServiceRequestRepository serviceRequestRepository;

    public ServiceRequestService(ServiceRequestRepository serviceRequestRepository) {
        this.serviceRequestRepository = serviceRequestRepository;
    }

    public List<ServiceRequest> findAll() {
        return serviceRequestRepository.findAll();
    }

    public Optional<ServiceRequest> findById(Long id) {
        return serviceRequestRepository.findById(id);
    }

    public ServiceRequest save(ServiceRequest request) {
        return serviceRequestRepository.save(request);
    }

    public void deleteById(Long id) {
        serviceRequestRepository.deleteById(id);
    }
}
