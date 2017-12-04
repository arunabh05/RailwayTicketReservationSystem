package com.cmpe275.domain;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;

/**
 * Created by arunabh.shrivastava on 12/1/2017.
 */
@Entity
@Table(name = "TICKET")
public class Ticket {

    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    Transaction transaction;
    @ManyToOne(fetch=FetchType.EAGER)
    private Train train;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "from_station", referencedColumnName = "id")
    private Station fromStation;
    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "to_station", referencedColumnName = "id")
    private Station toStation;

    private Long price;
    private String duration;
    private Date dateOfJourney;

    public Ticket(){}

    public Ticket(Train train, Station fromStation, Station toStation, Long price, String duration, Date dateOfJourney) {
        this.train = train;
        this.fromStation = fromStation;
        this.toStation = toStation;
        this.price = price;
        this.duration = duration;
        this.dateOfJourney = dateOfJourney;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Train getTrains() {
        return train;
    }

    public void setTrains(Train trains) {
        this.train = trains;
    }

    public Station getFromStation() {
        return fromStation;
    }

    public void setFromStation(Station fromStation) {
        this.fromStation = fromStation;
    }

    public Station getToStation() {
        return toStation;
    }

    public void setToStation(Station toStation) {
        this.toStation = toStation;
    }

    public Long getPrice() {
        return price;
    }

    public void setPrice(Long price) {
        this.price = price;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public Date getDateOfJourney() {
        return dateOfJourney;
    }

    public void setDateOfJourney(Date dateOfJourney) {
        this.dateOfJourney = dateOfJourney;
    }
}
