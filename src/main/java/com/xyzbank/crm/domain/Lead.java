package com.xyzbank.crm.domain;

import com.xyzbank.crm.domain.enums.LeadStatus;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "leads")
public class Lead extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    private Customer customer;

    @NotBlank
    private String product; // e.g., "Home Loan", "Credit Card"

    @Enumerated(EnumType.STRING)
    @NotNull
    private LeadStatus status = LeadStatus.NEW;

    private String notes;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Customer getCustomer() { return customer; }
    public void setCustomer(Customer customer) { this.customer = customer; }
    public String getProduct() { return product; }
    public void setProduct(String product) { this.product = product; }
    public LeadStatus getStatus() { return status; }
    public void setStatus(LeadStatus status) { this.status = status; }
    public String getNotes() { return notes; }
    public void setNotes(String notes) { this.notes = notes; }
}
