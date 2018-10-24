package com.matrangola.gadgets.controller;

import com.matrangola.gadgets.ControllerTest;
import com.matrangola.gadgets.GadgetsApplication;
import com.matrangola.gadgets.data.model.Customer;
import com.matrangola.gadgets.data.repository.CustomerRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

import static org.hamcrest.core.Is.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = GadgetsApplication.class)
@WebAppConfiguration
public class CustomerControllerTest extends ControllerTest {


    @Autowired
    protected CustomerRepository customerRepository;
    private Customer customer1;
    private Customer customer2;

    @Before
    public void setup() {
        super.setup();
        Calendar calendar = Calendar.getInstance();
        calendar.set(1990, Calendar.JUNE, 28, 1, 0);
        calendar.setTimeZone(TimeZone.getDefault());
        Date date = new Date(calendar.getTimeInMillis());
        customer1 = customerRepository.save(new Customer("first1", "Last1", date));
        customer2 = customerRepository.save(new Customer("first2", "Last2", date));
    }

    @Test
    public void testGetCustomer() throws Exception {
        mockMvc.perform( get("/customers/" + customer1.getId()).contentType(JSON_CONTENT_TYPE))
                .andExpect(status().isOk())
                .andExpect(content().contentType(JSON_CONTENT_TYPE))
                .andExpect(content().json(json(customer1)));
    }

    @Test
    public void testAddWithJson() throws Exception {
        Date bday = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("MM-dd-yyyy");
        String jdate = sdf.format(bday);
        mockMvc.perform(put("/customers/add")
                .content(json(new Customer("first", "last", bday)))
                .contentType(JSON_CONTENT_TYPE))
                .andExpect(status().isOk())
                .andExpect(content().contentType(JSON_CONTENT_TYPE))
                .andExpect(jsonPath("$.firstName", is("first")))
                .andExpect(jsonPath("$.lastName", is("last")))
                .andExpect(jsonPath("$.birthday", is(jdate)));

    }
}