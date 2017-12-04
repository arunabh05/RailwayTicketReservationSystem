package com.cmpe275.repository;

import com.cmpe275.domain.Train;
import org.springframework.data.repository.CrudRepository;

import java.time.LocalTime;
import java.util.List;

/**
 * @author arunabh.shrivastava
 */
public interface TrainRepository extends CrudRepository<Train, Long> {
    List<Train> findAllByDepartureTimeAfter(LocalTime departureTime);
}
