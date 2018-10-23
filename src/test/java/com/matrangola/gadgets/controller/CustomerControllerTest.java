package com.matrangola.gadgets.controller;

import com.matrangola.gadgets.ControllerTest;
import com.matrangola.gadgets.GadgetsApplication;
import com.matrangola.gadgets.data.repository.CustomerRepository;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = GadgetsApplication.class)
@WebAppConfiguration
public class CustomerControllerTest extends ControllerTest {


    @Autowired
    protected CustomerRepository customerRepository;

    @Before
    public void setup() {
        super.setup();
    }

}