package com.xyzbank.crm.web;

import com.xyzbank.crm.domain.Customer;
import com.xyzbank.crm.service.CustomerService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/customers")
public class CustomerController {
    private final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @GetMapping
    public String list(Model model) {
        model.addAttribute("customers", customerService.findAll());
        return "customers/list";
    }

    @GetMapping("/new")
    public String createForm(Model model) {
        model.addAttribute("customer", new Customer());
        return "customers/form";
    }

    @PostMapping
    public String create(@Valid @ModelAttribute Customer customer, BindingResult result) {
        if (result.hasErrors()) {
            return "customers/form";
        }
        customerService.save(customer);
        return "redirect:/customers";
    }

    @GetMapping("/{id}/edit")
    public String editForm(@PathVariable Long id, Model model) {
        Customer customer = customerService.findById(id).orElseThrow();
        model.addAttribute("customer", customer);
        return "customers/form";
    }

    @PostMapping("/{id}")
    public String update(@PathVariable Long id, @Valid @ModelAttribute Customer form, BindingResult result) {
        if (result.hasErrors()) {
            return "customers/form";
        }
        Customer existing = customerService.findById(id).orElseThrow();
        existing.setFirstName(form.getFirstName());
        existing.setLastName(form.getLastName());
        existing.setEmail(form.getEmail());
        existing.setPhone(form.getPhone());
        existing.setAddress(form.getAddress());
        customerService.save(existing);
        return "redirect:/customers";
    }

    @PostMapping("/{id}/delete")
    public String delete(@PathVariable Long id) {
        customerService.deleteById(id);
        return "redirect:/customers";
    }
}
