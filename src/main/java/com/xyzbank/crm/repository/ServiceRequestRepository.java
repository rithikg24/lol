package com.xyzbank.crm.repository;

import com.xyzbank.crm.domain.ServiceRequest;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ServiceRequestRepository extends JpaRepository<ServiceRequest, Long> {
}
