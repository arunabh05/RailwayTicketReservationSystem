package com.cmpe275.domain;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import javax.persistence.*;
import javax.ws.rs.Encoded;
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

    private String password;

    private String firstName;

    private String lastName;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable( name = "PASSENGER_TRANSACTION", joinColumns = @JoinColumn( name = "passenger_id"),
            inverseJoinColumns = @JoinColumn(name = "transaction_id"))
    @JsonIdentityInfo(generator=ObjectIdGenerators.PropertyGenerator.class, property="id")
    Set<Transaction> transactions;

    public Passenger(){}
    public Passenger(String email, String password, Set<Transaction> transactionSet) {
        this.email = email;
        this.password = password;
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

    public void addTransaction(Transaction transaction){
        this.transactions.add(transaction);
    }

    public void removeTransaction(Transaction transaction){
        this.transactions.remove(transaction);
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}