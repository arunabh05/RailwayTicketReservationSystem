package com.cmpe275.repository;

import com.cmpe275.domain.Ticket;
import org.springframework.data.repository.CrudRepository;

/**
 * @author arunabh.shrivastava
 */
public interface TicketRepository extends CrudRepository<Ticket, Long> {
}
