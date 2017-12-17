package com.cmpe275.controller;

import com.cmpe275.domain.Passenger;
import com.cmpe275.repository.PassengerRepository;
import com.cmpe275.service.PassengerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.HashSet;
import java.util.Set;

import static com.cmpe275.constant.Constants.INVALID_SEARCH_REQUEST;

/**
 * Created by arunabh.shrivastava on 12/1/2017.
 */

@Controller(value = "/auth")
@Configuration
@EnableWebSecurity
public class BaseController implements PassengerService {

    @Autowired
    PassengerRepository passengerRepository;

    /*@Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth

                .inMemoryAuthentication()
                .withUser("user")  // #1
                .password("password")
                .roles("USER")
                .and()
                .withUser("admin") // #2
                .password("password")
                .roles("ADMIN","USER");
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web
                .ignoring()
                .antMatchers("/resources/**"); // #3
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                    .antMatchers("/signup","/about","/register").permitAll() // #4
                    .antMatchers("/admin/**").hasRole("ADMIN")
                    .antMatchers("/bookTicket").authenticated()// #6
                    .anyRequest().authenticated() // 7
                    .and()

                .formLogin()  // #8
                    .loginPage("/login") // #9
                    .permitAll();

                // #5
    }*/



    @GetMapping(value = "/login")
    public String getHomePage(){
        return "Home";
    }


    @GetMapping(value = "/register")
    public String getRegisterPage(){
        return "Register";
    }


    @PostMapping(value = "/registerNewUser")
    public ResponseEntity<Object> registerUser(@RequestParam(value = "username") String username,
                                               @RequestParam(value = "password") String password)
    {
        if(username == null || password == null){
            return new ResponseEntity<>(INVALID_SEARCH_REQUEST, HttpStatus.BAD_REQUEST);
        }


        Set<Passenger> p = passengerRepository.findPassengerByEmail(username);
        if(p.size() == 0)
        {
            Passenger passenger = new Passenger(username, password, null);
            passengerRepository.save(passenger);
        }


        return new ResponseEntity<>("das", HttpStatus.OK);
    }

    @GetMapping(value = "/verifyLogin")
    public ResponseEntity<Object> verifyLogin(@RequestParam(value = "username") String username,
                                              @RequestParam(value = "password") String password)
    {
        if(username == null){
            return new ResponseEntity<>(INVALID_SEARCH_REQUEST, HttpStatus.BAD_REQUEST);
        }
        else
        {
            Set<Passenger> p = passengerRepository.findPassengerByEmailAndPassword(username,password);
            if(p.size() != 0)
            {
                /*Set<GrantedAuthority> grantedAuthorities = new HashSet<>();
                grantedAuthorities.add(new GrantedAuthority() {
                    @Override
                    public String getAuthority() {
                        return "USER";
                    }
                });*/
                Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
                String currentPrincipalName = authentication.getName();
                return new ResponseEntity<>(p, HttpStatus.OK);
            }


            return new ResponseEntity<>("No such user found", HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping(value = "/username", method = RequestMethod.GET)
    @ResponseBody
    public String currentUserName(Principal principal) {
        return principal.getName();
    }

}
