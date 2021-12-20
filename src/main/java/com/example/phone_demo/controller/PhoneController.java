package com.example.phone_demo.controller;

import com.example.phone_demo.model.Phone;
import com.example.phone_demo.model.PhoneDTO;
import com.example.phone_demo.repository.PhoneRepository;
import com.example.phone_demo.services.DeviceDetailsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.web.bind.annotation.*;

import java.util.*;


@RestController
@RequestMapping("/api")
public class PhoneController {

    @Autowired
    PhoneRepository phoneRepository;

    @Autowired
    private DeviceDetailsService deviceDetailsService;

    @Autowired
    private JavaMailSender emailSender;

    @Value("${phone.demo.host}")
    private String host;

    private static Logger log = LoggerFactory.getLogger(PhoneController.class);

    @GetMapping("/healthcheck")
    public ResponseEntity<String> healthcheck() {
      return new ResponseEntity<>("I'm ok", HttpStatus.OK);
    }

    @GetMapping("/phones")
    public ResponseEntity<List<Phone>> getAllPhones(@RequestParam(required = false) String name) {
        try{
            log.info("Received request [getAllPhones] with filter param: " + name);
            List<Phone> phones = new ArrayList<>();

            if(name == null)
                phones.addAll(phoneRepository.findAll());
            else
                phones.addAll(phoneRepository.findByName(name));

            if(phones.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }

            return new ResponseEntity<>(phones, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/phone/{id}")
    public ResponseEntity<Phone> getPhoneById(@PathVariable("id") long id) throws InterruptedException {
        log.info("Received request [getPhoneById] with id: " + id);
        Optional<Phone> phone = phoneRepository.findById(id);
        if(phone.isPresent()) {
            PhoneDTO phoneDTO = deviceDetailsService.getPhoneDeviceDetails(phone.get().getName());
            phone.get().setTechnology(phoneDTO.getTechnology());
            phone.get().set_2gBands(phoneDTO.get_2g_bands());
            phone.get().set_3gBands(phoneDTO.get_3g_bands());
            phone.get().set_4gBands(phoneDTO.get_4g_bands());
            return new ResponseEntity<>(phone.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }


    @PostMapping("/phone")
    public ResponseEntity<Phone> createPhone(@RequestBody Phone phone) {
        try {
            log.info("Received request [createPhone] with phone: " + phone);
            Phone _phone = phoneRepository.save(new Phone(phone.getName(), new Date(), phone.getUserName(),
                    phone.getUserEmail()));

            return new ResponseEntity<>(_phone, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/phone/{id}")
    public ResponseEntity<String> updatePhone(@PathVariable("id") long id, @RequestBody Phone phone) {
        log.info("Received request [updatePhone] with id: " + id + " and phone: " + phone);

        Optional<Phone> phoneData = phoneRepository.findById(id);
        if(phoneData.isPresent() && !phone.getUserEmail().equals("phone-demo@gmail.com")) {
            String hash = UUID.randomUUID().toString();
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom("noreply@demo.com");
            message.setTo(phone.getUserEmail());
            message.setSubject("Verification of phone device booking");
            message.setText("Please click on the link to book the phone device "+phone.getName()+ ":"
                    +host + "phone/verification"
                    +"?email="+phone.getUserEmail()
                    +"&username="+phone.getUserName()
                    +"&available="+phone.isAvailable()
                    +"&hash="+ hash);
            emailSender.send(message);
            phoneData.get().setVerificationHash(hash);
            phoneRepository.save(phoneData.get());
            return new ResponseEntity<>("Please check your email to complete booking", HttpStatus.OK);
        } else if (phoneData.isPresent() && phone.getUserEmail().equals("phone-demo@gmail.com")) {
            phoneData.get().setAvailable(phone.isAvailable());
            phoneData.get().setBookedTime(new Date());
            phoneData.get().setUserEmail(phone.getUserEmail());
            phoneData.get().setUserName(phone.getUserName());
            phoneRepository.save(phoneData.get());
            return new ResponseEntity<>("Phone successfully updated",HttpStatus.OK);
        }
        else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/phone/{id}")
    public ResponseEntity<Phone> deletePhone(@PathVariable("id") long id) {
        try {
            log.info("Received request [deletePhone] with id: " + id);
            phoneRepository.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }



}
