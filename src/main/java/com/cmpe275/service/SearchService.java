package com.cmpe275.service;

import com.cmpe275.domain.Station;
import com.cmpe275.domain.Train;
import com.cmpe275.repository.TrainRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalTime;
import java.util.List;
import java.util.Locale;

/**
 * @author arunabh.shrivastava
 */
@Service
public class SearchService {

    private final
    TrainRepository trainRepository;

    @Autowired
    public SearchService(TrainRepository trainRepository) {
        this.trainRepository = trainRepository;
    }


    public List<Train> getAvaliableTrains(int numberOfPassengers, String departureTime, Long fromStation,
                                          Long toStation, String ticketType, String connections,
                                          boolean roundTrip) {

        List<Train> trains = trainRepository.findAllByDepartureTimeAfter(LocalTime.parse(departureTime));

        for (Train t : trains) {
            System.out.println(t.getName());
        }
        return trains;
    }
}
