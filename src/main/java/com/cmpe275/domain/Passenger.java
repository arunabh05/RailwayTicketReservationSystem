package com.cmpe275.domain;

import javax.persistence.*;
import java.util.List;

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

    @OneToMany(fetch = FetchType.EAGER)
    private
    List<Transaction> transactions;

    public Passenger(){}
    public Passenger(String email) {
        this.email = email;
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

    private List<Transaction> getTransactions() {
        return transactions;
    }

    public void setTransactions(List<Transaction> transactions) {
        this.transactions = transactions;
    }

    public void addTransaction(Transaction transaction){
        this.transactions.add(transaction);
    }

    public void removeTransaction(Transaction transaction){
        this.transactions.remove(transaction);
    }

    public void removeAllTransactions() {
        this.getTransactions().clear();
    }
}