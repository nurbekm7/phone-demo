package com.example.phone_demo.services;

import com.example.phone_demo.model.PhoneDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class DeviceDetailsService {
    private static Logger log = LoggerFactory.getLogger(DeviceDetailsService.class);

    @Value("${phone.device.url}")
    private String url;

    private final RestTemplate restTemplate;

    public DeviceDetailsService(RestTemplateBuilder restTemplateBuilder) {
        this.restTemplate = restTemplateBuilder.build();
    }

    public PhoneDTO getPhoneDeviceDetails(String deviceName) throws InterruptedException {
        log.debug("getPhoneDeviceDetails starts");

        PhoneDTO phoneDTO = restTemplate
                .getForObject(url+"/phone/technology?deviceName="+deviceName, PhoneDTO.class);

        log.debug("phoneDTO, {}", phoneDTO);
        return phoneDTO;
    }
}
