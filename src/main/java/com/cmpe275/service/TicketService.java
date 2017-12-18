package com.cmpe275.service;

import com.cmpe275.domain.Search;
import com.cmpe275.domain.Ticket;
import com.cmpe275.repository.TicketRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * @author arunabh.shrivastava
 */
@Service
public class TicketService{

    private final
    TicketRepository ticketRepository;

    @Autowired
    public TicketService(TicketRepository ticketRepository) {
        this.ticketRepository = ticketRepository;
    }

    boolean isTrainAvailable(Search train, String dateOfJourney, int numberOfPassengers){

        DateFormat df = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy");
        Date date = null;

        try {
            date = df.parse(dateOfJourney);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        List<Ticket> ticketList = ticketRepository.findAllByDateOfJourneyAndTrain(date, train);

        if(ticketList == null || ticketList.size() == 0){
            return true;
        }
        int numberOfTicketsBooked = 0;
        for(Ticket ticket : ticketList){
            numberOfTicketsBooked+=ticket.getNumberOfPassengers();
        }
        return numberOfPassengers + numberOfTicketsBooked <= ticketList.get(0).getTrain().getTrain().getCapacity();
    }
}
