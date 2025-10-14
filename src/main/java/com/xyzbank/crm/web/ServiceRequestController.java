package com.xyzbank.crm.web;

import com.xyzbank.crm.domain.ServiceRequest;
import com.xyzbank.crm.service.ServiceRequestService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/service-requests")
public class ServiceRequestController {
    private final ServiceRequestService serviceRequestService;

    public ServiceRequestController(ServiceRequestService serviceRequestService) {
        this.serviceRequestService = serviceRequestService;
    }

    @GetMapping
    public String list(Model model) {
        model.addAttribute("requests", serviceRequestService.findAll());
        return "serviceRequests/list";
    }

    @GetMapping("/new")
    public String createForm(Model model) {
        ServiceRequest req = new ServiceRequest();
        req.setCustomer(new com.xyzbank.crm.domain.Customer());
        model.addAttribute("request", req);
        return "serviceRequests/form";
    }

    @PostMapping
    public String create(@Valid @ModelAttribute("request") ServiceRequest request, BindingResult result) {
        if (result.hasErrors()) {
            return "serviceRequests/form";
        }
        serviceRequestService.save(request);
        return "redirect:/service-requests";
    }

    @GetMapping("/{id}/edit")
    public String editForm(@PathVariable Long id, Model model) {
        ServiceRequest request = serviceRequestService.findById(id).orElseThrow();
        model.addAttribute("request", request);
        return "serviceRequests/form";
    }

    @PostMapping("/{id}")
    public String update(@PathVariable Long id, @Valid @ModelAttribute("request") ServiceRequest form, BindingResult result) {
        if (result.hasErrors()) {
            return "serviceRequests/form";
        }
        ServiceRequest existing = serviceRequestService.findById(id).orElseThrow();
        existing.setSubject(form.getSubject());
        existing.setDescription(form.getDescription());
        existing.setStatus(form.getStatus());
        if (form.getCustomer() != null && form.getCustomer().getId() != null) {
            com.xyzbank.crm.domain.Customer c = new com.xyzbank.crm.domain.Customer();
            c.setId(form.getCustomer().getId());
            existing.setCustomer(c);
        }
        serviceRequestService.save(existing);
        return "redirect:/service-requests";
    }

    @PostMapping("/{id}/delete")
    public String delete(@PathVariable Long id) {
        serviceRequestService.deleteById(id);
        return "redirect:/service-requests";
    }
}
