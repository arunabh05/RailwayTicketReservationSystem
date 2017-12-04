package com.cmpe275.domain;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import javax.persistence.*;
import java.util.Set;

/**
 * @author arunabh.shrivastava
 *
 */
@Entity
@Table(name = "PASSENGER")
public class Passenger {

    @Id
    @GeneratedValue
    private Long id;
    @Column(unique = true)
    private String email;

    @ManyToMany
    @JoinTable( name = "PASSENGER_TRANSACTION", joinColumns = @JoinColumn( name = "passenger_id"),
            inverseJoinColumns = @JoinColumn(name = "transaction_id"))
    @JsonIdentityInfo(generator=ObjectIdGenerators.PropertyGenerator.class, property="id")
    Set<Transaction> transactions;

    public Passenger(String email, Set<Transaction> transactionSet) {
        this.email = email;
        this.transactions = transactionSet;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Set<Transaction> getTransactions() {
        return transactions;
    }

    public void setTransactions(Set<Transaction> transactions) {
        this.transactions = transactions;
    }
}