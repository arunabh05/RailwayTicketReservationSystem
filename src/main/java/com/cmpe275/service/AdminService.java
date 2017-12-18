package com.cmpe275.service;

import com.cmpe275.domain.Passenger;
import com.cmpe275.domain.Train;
import com.cmpe275.repository.PassengerRepository;
import com.cmpe275.repository.TicketRepository;
import com.cmpe275.repository.TrainRepository;
import com.cmpe275.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by vedant on 12/17/17.
 */

@Service
public class AdminService {

    private final TransactionRepository transactionRepository;
    private final TicketRepository ticketRepository;
    private final TrainRepository trainRepository;
    private final PassengerRepository passengerRepository;

    @Autowired
    public AdminService(TransactionRepository transactionRepository, TicketRepository ticketRepository, TrainRepository trainRepository, PassengerRepository passengerRepository) {
        this.transactionRepository = transactionRepository;
        this.ticketRepository = ticketRepository;
        this.trainRepository = trainRepository;
        this.passengerRepository = passengerRepository;
    }


    public void reset() {

        for(Passenger passenger: passengerRepository.findAll()) {
            passenger.removeAllTransactions();
        }

        ticketRepository.deleteAll();
        transactionRepository.deleteAll();
    }


    public boolean updateTrainCapacity(Long capacity) {

        if(capacity >= 5 && capacity <=1000) {
            for(Train train: trainRepository.findAll()) {
                train.setCapacity(capacity);
                trainRepository.save(train);
            }
            return true;
        }else{
            return false;
        }

    }

}
