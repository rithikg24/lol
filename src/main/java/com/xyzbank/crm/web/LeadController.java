package com.xyzbank.crm.web;

import com.xyzbank.crm.domain.Lead;
import com.xyzbank.crm.service.LeadService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/leads")
public class LeadController {
    private final LeadService leadService;

    public LeadController(LeadService leadService) {
        this.leadService = leadService;
    }

    @GetMapping
    public String list(Model model) {
        model.addAttribute("leads", leadService.findAll());
        return "leads/list";
    }

    @GetMapping("/new")
    public String createForm(Model model) {
        Lead lead = new Lead();
        lead.setCustomer(new com.xyzbank.crm.domain.Customer());
        model.addAttribute("lead", lead);
        return "leads/form";
    }

    @PostMapping
    public String create(@Valid @ModelAttribute Lead lead, BindingResult result) {
        if (result.hasErrors()) {
            return "leads/form";
        }
        leadService.save(lead);
        return "redirect:/leads";
    }

    @GetMapping("/{id}/edit")
    public String editForm(@PathVariable Long id, Model model) {
        Lead lead = leadService.findById(id).orElseThrow();
        model.addAttribute("lead", lead);
        return "leads/form";
    }

    @PostMapping("/{id}")
    public String update(@PathVariable Long id, @Valid @ModelAttribute Lead form, BindingResult result) {
        if (result.hasErrors()) {
            return "leads/form";
        }
        Lead existing = leadService.findById(id).orElseThrow();
        existing.setProduct(form.getProduct());
        existing.setStatus(form.getStatus());
        existing.setNotes(form.getNotes());
        // Bind nested customer id if present
        if (form.getCustomer() != null && form.getCustomer().getId() != null) {
            com.xyzbank.crm.domain.Customer c = new com.xyzbank.crm.domain.Customer();
            c.setId(form.getCustomer().getId());
            existing.setCustomer(c);
        }
        leadService.save(existing);
        return "redirect:/leads";
    }

    @PostMapping("/{id}/delete")
    public String delete(@PathVariable Long id) {
        leadService.deleteById(id);
        return "redirect:/leads";
    }
}
