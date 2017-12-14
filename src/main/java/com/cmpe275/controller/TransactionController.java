package com.cmpe275.controller;

import com.cmpe275.domain.Passenger;
import com.cmpe275.domain.Ticket;
import com.cmpe275.domain.Transaction;
import com.cmpe275.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Set;

/**
 * @author arunabh.shrivastava
 */
@RestController
@RequestMapping(value = "/api/transaction")
public class TransactionController {

    @Autowired
    TransactionService transactionService;

    @PostMapping
    public ResponseEntity<?> makeTransaction(@RequestParam(value = "userId") Long userId,
                                             @RequestParam(value = "passengers") List<Long> passengers,
                                             @RequestParam(value = "ticketList") List<Ticket> ticketSet){
       // transactionService.makeTransaction(userId ,passengers, ticketSet);
        return new ResponseEntity<>("", HttpStatus.OK);
    }


}
