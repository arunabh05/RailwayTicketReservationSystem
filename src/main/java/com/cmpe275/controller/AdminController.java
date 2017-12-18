package com.cmpe275.controller;

import com.cmpe275.repository.TrainRepository;
import com.cmpe275.service.AdminService;
import com.cmpe275.service.TrainService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by arunabh.shrivastava on 12/1/2017.
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

}
