package com.xyzbank.crm.service;

import com.xyzbank.crm.domain.Lead;
import com.xyzbank.crm.repository.LeadRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class LeadService {
    private final LeadRepository leadRepository;

    public LeadService(LeadRepository leadRepository) {
        this.leadRepository = leadRepository;
    }

    public List<Lead> findAll() {
        return leadRepository.findAll();
    }

    public Optional<Lead> findById(Long id) {
        return leadRepository.findById(id);
    }

    public Lead save(Lead lead) {
        return leadRepository.save(lead);
    }

    public void deleteById(Long id) {
        leadRepository.deleteById(id);
    }
}
