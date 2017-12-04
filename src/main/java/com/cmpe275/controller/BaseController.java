package com.cmpe275.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * Created by arunabh.shrivastava on 12/1/2017.
 */

@Controller(value = "/auth")
public class BaseController {

    @GetMapping(value = "/login")
    public String getHomePage(){
        return "Home";
    }
}
