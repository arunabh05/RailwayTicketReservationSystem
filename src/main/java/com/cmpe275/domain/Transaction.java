package com.cmpe275.domain;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
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

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable( name = "PASSENGER_TRANSACTION", joinColumns = @JoinColumn( name = "transaction_id"),
            inverseJoinColumns = @JoinColumn(name = "passenger_id"))
    @JsonIdentityInfo(generator=ObjectIdGenerators.PropertyGenerator.class, property="id")
    private Set<Passenger> passengers;

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

    public Transaction(Set<Passenger> passengers, Set<Ticket> tickets, long price, String duration) {
        this.passengers = passengers;
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

    public Set<Passenger> getPassengers() {
        return passengers;
    }

    public void setPassengers(Set<Passenger> passengers) {
        this.passengers = passengers;
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
        this.price = price + TRANSACTION_FEE;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }
}