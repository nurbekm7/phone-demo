package com.example.phone_demo.model;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Date;

@Data
public class PhoneDTO {

    private String deviceName;
    private String brand;
    private String technology;
    private String _2g_bands;
    private String _3g_bands;
    private String _4g_bands;

}
