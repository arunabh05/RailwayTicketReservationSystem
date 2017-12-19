package com.cmpe275.controller;

import com.cmpe275.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * @author arunabh.shrivastava
 *
 */
@RestController
public class AdminController {

    private final
    AdminService adminService;

    @Autowired
    public AdminController(AdminService adminService) {
        this.adminService = adminService;
    }


    @RequestMapping(value = "/admin/reset")
    public ResponseEntity<?> reset(){

        adminService.reset();

        return new ResponseEntity<>("All train bookings cleared!", HttpStatus.OK);
    }

    @RequestMapping(value = "/admin/updateTrainCapacity")
    public ResponseEntity<?> updateTrainCapacity(@RequestParam(value = "capacity") Long capacity){

        if(adminService.updateTrainCapacity(capacity)) {
            return new ResponseEntity<>("", HttpStatus.OK);
        }else{
            return new ResponseEntity<>("Incorrect capacity entered! Enter a value between 5 and 1000, inclusive.", HttpStatus.BAD_REQUEST);
        }
    }


    @RequestMapping(value = "/admin/cancelTrain")
    public ResponseEntity<?> cancelTrain(@RequestParam(value = "trainId") Long trainId,
                                         @RequestParam(value = "dateOfJourney") String date){
        adminService.cancelTrain(trainId, date);
        return new ResponseEntity<Object>("Train cancelled.",HttpStatus.OK);
    }

    @RequestMapping(value = "/admin/report/trainReservation")
    public ResponseEntity<?> trainReservationRate(@RequestParam(value = "date")String date){
        Map<String, Integer> perTrainReservationRate = adminService.calculateTrainReservationRate(date);
        return new ResponseEntity<Object>(perTrainReservationRate, HttpStatus.OK);
    }

    @RequestMapping(value = "/admin/report/systemReservation")
    public ResponseEntity<?> systemReservationRate(@RequestParam(value = "date") String date){
        Map<String, Integer> perTrainReservationRate = adminService.calculateSystemReservationRate(date);
        return new ResponseEntity<Object>(perTrainReservationRate, HttpStatus.OK);
    }

    @RequestMapping(value = "/admin/report/ticketReservation")
    public ResponseEntity<?> ticketReservationRate(@RequestParam(value = "date") String date){
        Map<String, Integer> perTrainReservationRate = adminService.calculateTicketReservationRate(date);
        return new ResponseEntity<Object>(perTrainReservationRate, HttpStatus.OK);
    }
}
