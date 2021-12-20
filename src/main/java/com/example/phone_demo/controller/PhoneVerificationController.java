package com.example.phone_demo.controller;

import com.example.phone_demo.model.Phone;
import com.example.phone_demo.repository.PhoneRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.Optional;

@RestController
public class PhoneVerificationController {

    @Autowired
    PhoneRepository phoneRepository;

    private static Logger log = LoggerFactory.getLogger(PhoneVerificationController.class);

    //TODO return beautiful html page
    @GetMapping("/phone/verification")
    public ResponseEntity<String> verifyEmail(@RequestParam("hash") String hash,
                                              @RequestParam("email") String email,
                                              @RequestParam("available") boolean available,
                                              @RequestParam("username") String username)  {
        log.info("Received request [verifyEmail] with hash: " + hash);
        Optional<Phone> phone = phoneRepository.findByVerificationHash(hash);
        if(phone.isPresent()) {
            phone.get().setAvailable(available);
            phone.get().setBookedTime(new Date());
            phone.get().setUserEmail(email);
            phone.get().setUserName(username);
            phoneRepository.save(phone.get());
            return new ResponseEntity<>("Phone successfully booked", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Please try to start booking again",HttpStatus.NOT_FOUND);
        }
    }


}
