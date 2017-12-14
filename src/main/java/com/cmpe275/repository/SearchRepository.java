package com.cmpe275.repository;

import com.cmpe275.domain.Search;
import com.cmpe275.domain.Station;
import org.springframework.data.repository.CrudRepository;

import java.util.Set;

/**
 * @author arunabh.shrivastava
 */
public interface SearchRepository extends CrudRepository<Search, Long> {

    Set<Search> findTop5ByFromStationAndToStationAndDepartureTimeAfterOrderByArrivalTime
            (Station fromStation, Station toStation, String departureTime);

    Set<Search> findAllByFromStationAndDepartureTimeAfterAndDepartureTimeBeforeOrderByArrivalTime
            (Station fromStation, String departureTimeAfter, String departureTimeBefore);

    Set<Search> findAllByFromStationAndToStationAndDepartureTimeAfterAndDepartureTimeBeforeOrderByArrivalTime
            (Station fromStation, Station toStation, String departureTimeAfter, String departureTimeBefore);

}
