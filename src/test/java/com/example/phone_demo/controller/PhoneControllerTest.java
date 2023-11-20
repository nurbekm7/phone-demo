package com.example.phone_demo.controller;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

import com.example.phone_demo.model.Phone;
import com.example.phone_demo.repository.PhoneRepository;
import com.example.phone_demo.services.DeviceDetailsService;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.time.LocalDate;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.Date;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@ContextConfiguration(classes = {PhoneController.class, DeviceDetailsService.class})
@ActiveProfiles({"dev"})
@ExtendWith(SpringExtension.class)
class PhoneControllerTest {
    @Autowired
    private PhoneController phoneController;

    @MockBean
    private PhoneRepository phoneRepository;

    @MockBean
    private RestTemplateBuilder restTemplateBuilder;

    /**
     * Method under test: {@link PhoneController#createPhone(Phone)}
     */
    @Test
    void testCreatePhone() throws Exception {
        Phone phone = new Phone();
        phone.setAvailable(true);
        phone.setBookedTime(Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));
        phone.setCreatedTime(Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));
        phone.setId(1L);
        phone.setName("Name");
        phone.setTechnology("Technology");
        phone.setUserEmail("jane.doe@example.org");
        phone.setUserName("janedoe");
        phone.setVerificationHash("Verification Hash");
        phone.set_2gBands(" 2g Bands");
        phone.set_3gBands(" 3g Bands");
        phone.set_4gBands(" 4g Bands");
        when(phoneRepository.save(Mockito.<Phone>any())).thenReturn(phone);

        Phone phone2 = new Phone();
        phone2.setAvailable(true);
        phone2.setBookedTime(Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));
        phone2.setCreatedTime(Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));
        phone2.setId(1L);
        phone2.setName("Name");
        phone2.setTechnology("Technology");
        phone2.setUserEmail("jane.doe@example.org");
        phone2.setUserName("janedoe");
        phone2.setVerificationHash("Verification Hash");
        phone2.set_2gBands(" 2g Bands");
        phone2.set_3gBands(" 3g Bands");
        phone2.set_4gBands(" 4g Bands");
        String content = (new ObjectMapper()).writeValueAsString(phone2);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/api/phone")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content);
        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(phoneController)
                .build()
                .perform(requestBuilder);
        actualPerformResult.andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content()
                        .string(
                                "{\"id\":1,\"name\":\"Name\",\"available\":true,\"bookedTime\":0,\"createdTime\":0,\"userName\":\"janedoe\",\"userEmail"
                                        + "\":\"jane.doe@example.org\",\"verificationHash\":\"Verification Hash\",\"technology\":\"Technology\",\"_2gBands\":\""
                                        + " 2g Bands\",\"_3gBands\":\" 3g Bands\",\"_4gBands\":\" 4g Bands\"}"));
    }

    /**
     * Method under test: {@link PhoneController#deletePhone(long)}
     */
    @Test
    void testDeletePhone() throws Exception {
        doNothing().when(phoneRepository).deleteById(Mockito.<Long>any());
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.delete("/api/phone/{id}", 1L);
        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(phoneController)
                .build()
                .perform(requestBuilder);
        actualPerformResult.andExpect(MockMvcResultMatchers.status().isNoContent());
    }

    /**
     * Method under test: {@link PhoneController#deletePhone(long)}
     */
    @Test
    void testDeletePhone2() throws Exception {
        doNothing().when(phoneRepository).deleteById(Mockito.<Long>any());
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.delete("/api/phone/{id}", 1L);
        requestBuilder.characterEncoding("Encoding");
        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(phoneController)
                .build()
                .perform(requestBuilder);
        actualPerformResult.andExpect(MockMvcResultMatchers.status().isNoContent());
    }

    /**
     * Method under test: {@link PhoneController#getAllPhones()}
     */
    @Test
    void testGetAllPhones() throws Exception {
        when(phoneRepository.findAll()).thenReturn(new ArrayList<>());
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/api/phones");
        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(phoneController)
                .build()
                .perform(requestBuilder);
        actualPerformResult.andExpect(MockMvcResultMatchers.status().isNoContent());
    }

    /**
     * Method under test: {@link PhoneController#getAllPhones()}
     */
    @Test
    void testGetAllPhones2() throws Exception {
        Phone phone = new Phone();
        phone.setAvailable(true);
        phone.setBookedTime(Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));
        phone.setCreatedTime(Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));
        phone.setId(1L);
        phone.setName("?");
        phone.setTechnology("?");
        phone.setUserEmail("jane.doe@example.org");
        phone.setUserName("janedoe");
        phone.setVerificationHash("?");
        phone.set_2gBands("?");
        phone.set_3gBands("?");
        phone.set_4gBands("?");

        ArrayList<Phone> phoneList = new ArrayList<>();
        phoneList.add(phone);
        when(phoneRepository.findAll()).thenReturn(phoneList);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/api/phones");
        MockMvcBuilders.standaloneSetup(phoneController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content()
                        .string(
                                "[{\"id\":1,\"name\":\"?\",\"available\":true,\"bookedTime\":0,\"createdTime\":0,\"userName\":\"janedoe\",\"userEmail\""
                                        + ":\"jane.doe@example.org\",\"verificationHash\":\"?\",\"technology\":\"?\",\"_2gBands\":\"?\",\"_3gBands\":\"?\",\"_4gBands"
                                        + "\":\"?\"}]"));
    }

    /**
     * Method under test: {@link PhoneController#getPhoneById(long)}
     */
    @Test
    void testGetPhoneById() throws Exception {
        when(phoneRepository.findById(Mockito.<Long>any())).thenReturn(Optional.empty());
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/api/phone/{id}", 1L);
        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(phoneController)
                .build()
                .perform(requestBuilder);
        actualPerformResult.andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    /**
     * Method under test: {@link PhoneController#healthcheck()}
     */
    @Test
    void testHealthcheck() throws Exception {
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/api/healthcheck");
        MockMvcBuilders.standaloneSetup(phoneController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("text/plain;charset=ISO-8859-1"))
                .andExpect(MockMvcResultMatchers.content().string("I'm ok"));
    }

    /**
     * Method under test: {@link PhoneController#healthcheck()}
     */
    @Test
    void testHealthcheck2() throws Exception {
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/api/healthcheck");
        requestBuilder.characterEncoding("Encoding");
        MockMvcBuilders.standaloneSetup(phoneController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("text/plain;charset=ISO-8859-1"))
                .andExpect(MockMvcResultMatchers.content().string("I'm ok"));
    }

    /**
     * Method under test: {@link PhoneController#updatePhone(long, Phone)}
     */
    @Test
    void testUpdatePhone() throws Exception {
        Phone phone = new Phone();
        phone.setAvailable(true);
        phone.setBookedTime(Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));
        phone.setCreatedTime(Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));
        phone.setId(1L);
        phone.setName("Name");
        phone.setTechnology("Technology");
        phone.setUserEmail("jane.doe@example.org");
        phone.setUserName("janedoe");
        phone.setVerificationHash("Verification Hash");
        phone.set_2gBands(" 2g Bands");
        phone.set_3gBands(" 3g Bands");
        phone.set_4gBands(" 4g Bands");
        Optional<Phone> ofResult = Optional.of(phone);

        Phone phone2 = new Phone();
        phone2.setAvailable(true);
        phone2.setBookedTime(Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));
        phone2.setCreatedTime(Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));
        phone2.setId(1L);
        phone2.setName("Name");
        phone2.setTechnology("Technology");
        phone2.setUserEmail("jane.doe@example.org");
        phone2.setUserName("janedoe");
        phone2.setVerificationHash("Verification Hash");
        phone2.set_2gBands(" 2g Bands");
        phone2.set_3gBands(" 3g Bands");
        phone2.set_4gBands(" 4g Bands");
        when(phoneRepository.save(Mockito.<Phone>any())).thenReturn(phone2);
        when(phoneRepository.findById(Mockito.<Long>any())).thenReturn(ofResult);

        Phone phone3 = new Phone();
        phone3.setAvailable(true);
        phone3.setBookedTime(Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));
        phone3.setCreatedTime(Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));
        phone3.setId(1L);
        phone3.setName("Name");
        phone3.setTechnology("Technology");
        phone3.setUserEmail("jane.doe@example.org");
        phone3.setUserName("janedoe");
        phone3.setVerificationHash("Verification Hash");
        phone3.set_2gBands(" 2g Bands");
        phone3.set_3gBands(" 3g Bands");
        phone3.set_4gBands(" 4g Bands");
        String content = (new ObjectMapper()).writeValueAsString(phone3);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.put("/api/phone/{id}", 1L)
                .contentType(MediaType.APPLICATION_JSON)
                .content(content);
        MockMvcBuilders.standaloneSetup(phoneController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("text/plain;charset=ISO-8859-1"))
                .andExpect(MockMvcResultMatchers.content().string("Phone successfully updated"));
    }

    /**
     * Method under test: {@link PhoneController#updatePhone(long, Phone)}
     */
    @Test
    void testUpdatePhone2() throws Exception {
        Phone phone = new Phone();
        phone.setAvailable(true);
        phone.setBookedTime(Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));
        phone.setCreatedTime(Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));
        phone.setId(1L);
        phone.setName("Name");
        phone.setTechnology("Technology");
        phone.setUserEmail("jane.doe@example.org");
        phone.setUserName("janedoe");
        phone.setVerificationHash("Verification Hash");
        phone.set_2gBands(" 2g Bands");
        phone.set_3gBands(" 3g Bands");
        phone.set_4gBands(" 4g Bands");
        when(phoneRepository.save(Mockito.<Phone>any())).thenReturn(phone);
        when(phoneRepository.findById(Mockito.<Long>any())).thenReturn(Optional.empty());

        Phone phone2 = new Phone();
        phone2.setAvailable(true);
        phone2.setBookedTime(Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));
        phone2.setCreatedTime(Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));
        phone2.setId(1L);
        phone2.setName("Name");
        phone2.setTechnology("Technology");
        phone2.setUserEmail("jane.doe@example.org");
        phone2.setUserName("janedoe");
        phone2.setVerificationHash("Verification Hash");
        phone2.set_2gBands(" 2g Bands");
        phone2.set_3gBands(" 3g Bands");
        phone2.set_4gBands(" 4g Bands");
        String content = (new ObjectMapper()).writeValueAsString(phone2);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.put("/api/phone/{id}", 1L)
                .contentType(MediaType.APPLICATION_JSON)
                .content(content);
        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(phoneController)
                .build()
                .perform(requestBuilder);
        actualPerformResult.andExpect(MockMvcResultMatchers.status().isNotFound());
    }
}

