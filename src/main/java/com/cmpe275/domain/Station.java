package com.cmpe275.domain;

import javax.persistence.*;

/**
 *
 * Created by arunabh.shrivastava on 12/1/2017.
 */
@Entity
@Table(name = "STATION")
public class Station {

    @Id
    @GeneratedValue
    private Long id;
    private String name;

    public Station(String name){
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
