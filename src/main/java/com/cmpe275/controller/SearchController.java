package com.cmpe275.controller;

import com.cmpe275.domain.Station;
import com.cmpe275.service.SearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author arunabh.shrivastava
 */
@RestController
@RequestMapping(value = "/search")
public class SearchController {

    @Autowired
    SearchService searchService;

    @PostMapping
    public ResponseEntity<?> create(@RequestParam(value = "noOfPassengers",required = false, defaultValue = "1") Integer noOfPassengers,
                                    @RequestParam(value = "departureTime") String departureTime,
                                    @RequestParam(value = "fromStation") Long fromStation,
                                    @RequestParam(value = "toStation") Long toStation,
                                    @RequestParam(value = "ticketType", required = false, defaultValue = "any") String ticketType,
                                    @RequestParam(value = "connection", required = false, defaultValue = "any") String connections,
                                    @RequestParam(value = "roundTrip", required = false, defaultValue = "false") boolean roundTrip) {


        System.out.println(noOfPassengers);
        System.out.println(departureTime);
        System.out.println(fromStation);
        System.out.println(toStation);
        System.out.println(ticketType);
        System.out.println(connections);
        System.out.println(roundTrip);

        searchService.getAvaliableTrains(noOfPassengers,departureTime,fromStation,toStation,ticketType,
                connections,roundTrip);

       return new ResponseEntity<>(true, HttpStatus.OK);
    }
}
