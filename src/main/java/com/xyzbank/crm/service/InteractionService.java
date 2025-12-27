package com.xyzbank.crm.service;

import com.xyzbank.crm.domain.Interaction;
import com.xyzbank.crm.repository.InteractionRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class InteractionService {
    private final InteractionRepository interactionRepository;

    public InteractionService(InteractionRepository interactionRepository) {
        this.interactionRepository = interactionRepository;
    }

    public List<Interaction> findAll() {
        return interactionRepository.findAll();
    }

    public Optional<Interaction> findById(Long id) {
        return interactionRepository.findById(id);
    }

    public Interaction save(Interaction interaction) {
        return interactionRepository.save(interaction);
    }

    public void deleteById(Long id) {
        interactionRepository.deleteById(id);
    }
}
