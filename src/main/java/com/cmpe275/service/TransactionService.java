package com.cmpe275.service;

import com.cmpe275.domain.*;
import com.cmpe275.repository.PassengerRepository;
import com.cmpe275.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * @author arunabh.shrivastava
 */

@Service
public class TransactionService {

    private final
    PassengerRepository passengerRepository;
    private final
    TicketService ticketService;
    private final
    TransactionRepository transactionRepository;

    @Autowired
    public TransactionService(PassengerRepository passengerRepository, TicketService ticketService,
                              TransactionRepository transactionRepository) {
        this.passengerRepository = passengerRepository;
        this.ticketService = ticketService;
        this.transactionRepository = transactionRepository;
    }

    public void makeTransaction(Long userId, Set<Long> passengers, Set<Ticket> tickets){
    }
}
