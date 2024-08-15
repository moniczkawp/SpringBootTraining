package com.neueda.payments;

import com.neueda.payments.aspects.RepoLoggingAspect;
import com.neueda.payments.aspects.ServiceLoggingAspect;
import com.neueda.payments.control.CountryController;
import com.neueda.payments.model.Payment;
import com.neueda.payments.repositories.PaymentsRepository;
import com.neueda.payments.repositories.UserRepository;
import com.neueda.payments.service.BootstrapService;
import com.neueda.payments.service.PaymentsService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@EnableAutoConfiguration(exclude = {DataSourceAutoConfiguration.class, HibernateJpaAutoConfiguration.class})

public class CountryTests {
    @Autowired
    PaymentsService paymentsService;

    @Autowired
    CountryController countryController;

    @MockBean
    private PaymentsRepository paymentsRepository;

    @MockBean
    private UserRepository userRepository;

    @MockBean
    BootstrapService bootstrapService;

    @MockBean
    private RepoLoggingAspect repoLoggingAspect;

    @MockBean
    private ServiceLoggingAspect serviceLoggingAspect;

    @BeforeEach
    public void setup(){
        Payment p1 = new Payment();
        p1.setCountry("IRL");

        Payment p2 = new Payment();
        p2.setCountry("CAN");

        Payment p3 = new Payment();
        p3.setCountry("USA");

        Mockito.when(paymentsRepository.findAll()).thenReturn(List.of(p1,p2,p3));
    }

    @Test
    public void testCountriesInService(){
        Assertions.assertEquals(List.of("CAN","IRL","USA"), paymentsRepository.getCountries());
    }

    public void testController(){
        Assertions.assertEquals(List.of("CAN","IRL","USA"), countryController.getCountries());
    }
}
