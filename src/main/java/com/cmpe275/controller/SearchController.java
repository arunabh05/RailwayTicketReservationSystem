package com.cmpe275.controller;

import com.cmpe275.domain.Transaction;
import com.cmpe275.service.SearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import java.util.Set;
import static com.cmpe275.constant.Constants.*;

/**
 * @author arunabh.shrivastava
 */
@RestController
@RequestMapping(value = "/api/search")
public class SearchController {

    private final
    SearchService searchService;

    @Autowired
    public SearchController(SearchService searchService) {
        this.searchService = searchService;
    }

    @PostMapping
    public ResponseEntity<?> search(@RequestParam(value = "noOfPassengers",required = false, defaultValue = "1") Integer noOfPassengers,
                                    @RequestParam(value = "departureTime") String departureTime,
                                    @RequestParam(value = "fromStation") Long fromStation,
                                    @RequestParam(value = "toStation") Long toStation,
                                    @RequestParam(value = "ticketType", required = false, defaultValue = "any") String ticketType,
                                    @RequestParam(value = "connection", required = false, defaultValue = "any") String connections,
                                    @RequestParam(value = "roundTrip", required = false, defaultValue = "false") boolean roundTrip) {

        if(departureTime == null || toStation == null || fromStation == null){
            return new ResponseEntity<>(INVALID_SEARCH_REQUEST, HttpStatus.BAD_REQUEST);
        }
        Set<Transaction> transactions = searchService.getAvailableTrains(noOfPassengers,departureTime,fromStation,toStation,ticketType,
                connections,roundTrip);
       return new ResponseEntity<>(transactions, HttpStatus.OK);
    }
}
