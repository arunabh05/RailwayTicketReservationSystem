package com.cmpe275.service;

import com.cmpe275.domain.Transaction;

import java.util.Set;

/**
 * @author arunabh.shrivastava
 */
public interface SearchService {

    Set<Transaction> getAvailableTrains(int numberOfPassengers, String departureTime, Long fromStationId,
                                        Long toStationId, String ticketType, String connections,
                                        boolean roundTrip);
}
