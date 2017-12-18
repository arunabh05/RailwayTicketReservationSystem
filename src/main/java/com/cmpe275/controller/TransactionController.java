package com.cmpe275.controller;

import com.cmpe275.domain.Transaction;
import com.cmpe275.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;


/**
 * @author arunabh.shrivastava
 */
@RestController
public class TransactionController {

    private final
    TransactionService transactionService;

    @Autowired
    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }



    @RequestMapping(value = "/api/transaction")
    public ResponseEntity<?> makeTransaction(@RequestParam(value = "userId") Long userId,
                                             @RequestBody Transaction transaction){
        Transaction transaction1 = transactionService.makeTransaction(userId , transaction);
        return new ResponseEntity<>(transaction1, HttpStatus.OK);
    }


    @RequestMapping(value = "/api/deleteTransaction")
    public ResponseEntity<?> deleteTransaction(@RequestParam(value = "transactionId") Long transactionId,
                                               @RequestParam(value = "userId") Long userId) {

        Transaction transaction2 = transactionService.deleteTransaction(userId, transactionId);
        return new ResponseEntity<>(transaction2, HttpStatus.OK);
    }


    @RequestMapping(value = "/api/aaa")
    public ResponseEntity<?> aaa() {


        Transaction transaction = new Transaction();
        List<Object> aa = new ArrayList<>();

        List<String> array = new ArrayList<>();
        array.add("A");
        array.add("B");

        aa.add(transaction);
        aa.add(array);

        return new ResponseEntity<>(aa, HttpStatus.OK);
    }


}
