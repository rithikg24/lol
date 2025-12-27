package com.xyzbank.crm.domain;

import com.xyzbank.crm.domain.enums.Channel;
import com.xyzbank.crm.domain.enums.InteractionType;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import java.time.Instant;

@Entity
@Table(name = "interactions")
public class Interaction extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    private Customer customer;

    @Enumerated(EnumType.STRING)
    @NotNull
    private InteractionType type;

    @Enumerated(EnumType.STRING)
    @NotNull
    private Channel channel;

    private String notes;

    @NotNull
    private Instant interactionTime = Instant.now();

    public Long getId() { return id; }
    public Customer getCustomer() { return customer; }
    public void setCustomer(Customer customer) { this.customer = customer; }
    public InteractionType getType() { return type; }
    public void setType(InteractionType type) { this.type = type; }
    public Channel getChannel() { return channel; }
    public void setChannel(Channel channel) { this.channel = channel; }
    public String getNotes() { return notes; }
    public void setNotes(String notes) { this.notes = notes; }
    public Instant getInteractionTime() { return interactionTime; }
    public void setInteractionTime(Instant interactionTime) { this.interactionTime = interactionTime; }
}
