package com.matrangola.microcustomer;

import com.matrangola.microcustomer.data.model.Customer;
import com.matrangola.microcustomer.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/customers", produces = {"application/json"})
@SpringBootApplication
public class MicrocustomerApplication {
	private static final SimpleDateFormat BIRTHDAY_TEXT_FORMAT = new SimpleDateFormat("yyyyMMdd");

	public static void main(String[] args) {
		SpringApplication.run(MicrocustomerApplication.class, args);
	}

	private CustomerService customerService;

	@Autowired
	public MicrocustomerApplication(CustomerService customerService) {
		this.customerService = customerService;
	}

	@RequestMapping(path = "/", method = RequestMethod.GET)
	public List<Customer> getAll() {
		return customerService.getCustomers();
	}

	@RequestMapping(path="/{id}", method = RequestMethod.GET)
	public Customer getById(@PathVariable Long id) {
		Customer customer = customerService.getCustomer(id);
		return customer;
	}

	@RequestMapping(path = "/new", method = RequestMethod.POST)
	public Customer add(
			@RequestParam String firstName,
			@RequestParam String lastName,
			@RequestParam(name="birthday", required = false) String birthday) {
		Customer customer = new Customer();
		customer.setFirstName(firstName);
		customer.setLastName(lastName);
		if (birthday != null) {
			try {
				Date bd = BIRTHDAY_TEXT_FORMAT.parse(birthday);
				customer.setBirthday(bd);
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}

		customerService.addCustomer(customer);

		return customer;
	}

	@RequestMapping(path = "/add", method = RequestMethod.PUT)
	public Customer add(@RequestBody Customer customer) {
		customerService.addCustomer(customer);
		return customer;
	}

	@RequestMapping(path = "/older/{age}", method = RequestMethod.GET)
	public List<Customer> older(@PathVariable int age) {
		List<Customer> customers = customerService.getCustomers();
		Calendar old = Calendar.getInstance();
		old.setTime(new Date());
		old.add(Calendar.YEAR, age * -1);

		return customers.stream().filter(customer ->
				customer.getBirthday().before(old.getTime())).collect(Collectors.toList());
	}

	// This is just for example
	@RequestMapping(path={"/foo", "/foo/bar", "*.bar", "dove/*,**/data"})
	public String foo() {
		return "foo mapping success";
	}


}
