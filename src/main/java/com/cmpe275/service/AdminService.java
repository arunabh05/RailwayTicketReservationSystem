package com.cmpe275.service;

import com.cmpe275.domain.*;
import com.cmpe275.repository.PassengerRepository;
import com.cmpe275.repository.TicketRepository;
import com.cmpe275.repository.TrainRepository;
import com.cmpe275.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by vedant on 12/17/17.
 */

@Service
public class AdminService {

    private final TransactionRepository transactionRepository;
    private final TicketRepository ticketRepository;
    private final TrainRepository trainRepository;
    private final PassengerRepository passengerRepository;
    private final SearchService searchService;
    private final TransactionService transactionService;

    @Autowired
    public AdminService(TransactionRepository transactionRepository, TicketRepository ticketRepository, TrainRepository trainRepository, PassengerRepository passengerRepository, SearchService searchService, TransactionService transactionService) {
        this.transactionRepository = transactionRepository;
        this.ticketRepository = ticketRepository;
        this.trainRepository = trainRepository;
        this.passengerRepository = passengerRepository;
        this.searchService = searchService;
        this.transactionService = transactionService;
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

    public void cancelTrain(Long trainId, String dateOfJourney){

        Date date = null;
        DateFormat df = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy");
        DateFormat df2 = new SimpleDateFormat("yyyy-MM-dd");
        try {
            date = df.parse(dateOfJourney);
        } catch (ParseException e) {
            try {
                date = df2.parse(dateOfJourney);
            } catch (ParseException e1) {
                e1.getMessage();
            }

            for(Ticket ticket: ticketRepository.findAllByDateOfJourney(date)){

                Station fromStation;
                Station toStation;
                String departureTime;
                int numberOfPassenger;
                List<String> passengers = new ArrayList<>();
                Passenger passenger = ticket.getTransaction().getPassenger();
                if(ticket.getTrain().getTrain().getId().equals(trainId)){
                    fromStation = ticket.getTrain().getFromStation();
                    toStation = ticket.getTrain().getToStation();
                    departureTime = ticket.getTrain().getDepartureTime();
                    numberOfPassenger = ticket.getNumberOfPassengers();
                    passengers.addAll(ticket.getTransaction().getListOfPassengers());
                    ticketRepository.delete(ticket.getId());
                    rebookTicketInAnotherTrain(passenger,fromStation, toStation, departureTime, numberOfPassenger, passengers,
                            dateOfJourney);
                }
            }
        }
    }

    private void rebookTicketInAnotherTrain(Passenger passenger, Station fromStation, Station toStation, String departureTime,
                                            int numberOfPassengers, List<String> passengers, String dateOfJourney){

        String ticketType = "any";
        String connections = "any";
        boolean roundTrip = false;

        List<Transaction> transactionList = searchService.getAvailableTrains(numberOfPassengers,departureTime
                ,fromStation.getId(),toStation.getId(),ticketType, connections,roundTrip, dateOfJourney, departureTime,
                dateOfJourney);

        if(transactionList.size()>0){
            Transaction transaction = transactionList.get(0);
            transaction.setPassenger(passenger);
            transaction.setListOfPassengers(passengers);
            printTransaction(transaction);
            transactionService.makeTransaction(passenger.getId(), transaction);
        }
    }

    private void printTransaction(Transaction transaction){
            System.out.println("List of Tickets ::");
            for(Ticket ticket: transaction.getTickets()){
                System.out.println(ticket.getTrain().getFromStation().getName() + "  to  " +ticket.getTrain().getToStation().getName());
                System.out.println(ticket.getTrain().getDepartureTime() + "  to  " +ticket.getTrain().getArrivalTime());
            }
            System.out.println(":::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::");
        }
}
