package com.cmpe275.domain;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import javax.persistence.*;
import java.util.Set;

/**
 * @author arunabh.shrivastava
 */
@Entity
@Table(name = "TRANSACTION")
public class Transaction {

    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JsonIgnore
    private Passenger passenger;

    @OneToMany(mappedBy = "transaction")
    private Set<Ticket> tickets;
    private long price;
    private String duration;
    private final long TRANSACTION_FEE = 1;

    public Transaction(){}

    public Transaction(Set<Ticket> tickets, long price, String duration) {
        this.tickets = tickets;
        this.price = TRANSACTION_FEE+price;
        this.duration = duration;
    }

    public Transaction(Passenger passenger, Set<Ticket> tickets, long price, String duration) {
        this.passenger = passenger;
        this.tickets = tickets;
        this.price = TRANSACTION_FEE+price;
        this.duration = duration;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Passenger getPassenger() {
        return passenger;
    }

    public void setPassenger(Passenger passenger) {
        this.passenger = passenger;
    }

    public Set<Ticket> getTickets() {
        return tickets;
    }

    public void setTickets(Set<Ticket> tickets) {
        this.tickets = tickets;
    }

    public long getPrice() {
        return price;
    }

    public void setPrice(long price) {
        this.price = price;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }
}