package com.cmpe275.repository;

import com.cmpe275.domain.Passenger;
import org.springframework.data.repository.CrudRepository;

/**
 * @author arunabh.shrivastava
 */
public interface PassengerRepository extends CrudRepository<Passenger, Long> {
}
