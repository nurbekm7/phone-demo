package com.example.phone_demo.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;



@Entity
@Data
@Table(name = "phones")
@NoArgsConstructor
public class Phone {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(name = "name")
    private String name;

    @Column(name = "available")
    private boolean available = true;

    @Column(name = "booked_time")
    private Date bookedTime;

    @Column(name = "created_time")
    private Date createdTime;

    @Column(name = "user_name")
    private String userName;

    @Column(name = "user_email")
    private String userEmail;

    @Column(name = "verification_hash")
    private String verificationHash;

    private String technology;
    private String _2gBands;
    private String _3gBands;
    private String _4gBands;

    public Phone(String name, Date createdTime, String userName, String userEmail) {
        this.name = name;
        this.createdTime = createdTime;
        this.userName = userName;
        this.userEmail = userEmail;
    }


}

