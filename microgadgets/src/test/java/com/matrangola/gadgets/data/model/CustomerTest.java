package com.matrangola.gadgets.data.model;

import org.junit.Before;
import org.junit.Test;

import java.util.Calendar;
import java.util.Date;

public class CustomerTest {

    private Customer customer;
    private Calendar cal;

    @Before
    public void setUp() throws Exception {
        cal = Calendar.getInstance();
        cal.set(1999, Calendar.OCTOBER, 28);
        customer = new Customer("test", "user", cal.getTime());

    }

    @Test
    public void getFirstName() {
        assertEquals("firstName", "test", customer.getFirstName());
    }

    @Test
    public void setFirstName() {
        Customer fn = new Customer("first", "lst");
        fn.setFirstName("first2");
        assertEquals("firstName", "first2", fn.getFirstName());
    }

    @Test
    public void getLastName() {
        assertEquals("lastName", "user", customer.getLastName());
    }

    @Test
    public void setLastName() {
        Customer ln = new Customer("first", "last");
        ln.setLastName("last2");
        assertEquals("lastName", "last2", ln.getLastName());
    }

    @Test
    public void getBirthday() {
        assertEquals("Birthday", cal.getTime(), customer.getBirthday());
    }

    @Test
    public void setBirthday() {
        Date date = new Date();
        Customer bd = new Customer();
        bd.setBirthday(date);
        assertEquals("birthday", date, bd.getBirthday());
    }
}