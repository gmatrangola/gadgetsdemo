#Demo 1a
Spring Initializer http://start.spring.io

## CustomerController.java
```java
@RestController
public class CustomerController {
   @RequestMapping("/makeCustomer")
   public String hello() {
       return "hello world";
   }
}
```

#Demo 1b

## Customer.java
```java
package com.matrangola.gadgets.data.model;

public class Customer {
   private String firstName;
   private String lastName;

   public String getFirstName() {
       return firstName;
   }

   public void setFirstName(String firstName) {
       this.firstName = firstName;
   }

   public String getLastName() {
       return lastName;
   }

   public void setLastName(String lastName) {
       this.lastName = lastName;
   }
}
```

## CustomerController.java
```java
@RestController
public class CustomerController {
   @RequestMapping("/makeCustomer")
   public Customer makeCustomer(
@RequestParam(value="last") String lastName,
                                @RequestParam(value="first") String firstName) {
       Customer customer = new Customer();
       customer.setFirstName(firstName);
       customer.setLastName(lastName);
       return customer;
   }
}
```

# Demo 2

Create a CustomerService Module. Interface and implemenation Class.

## CustomerService.java

```java
public interface CustomerService {
   void addCustomer(Customer customer);
   void updateCustomer(Customer customer);
}

```

## CustomerServiceImpl.java

```java
@Service
public class CustomerServiceImpl implements CustomerService {
   private Set<Customer> customers = new HashSet<>();

   @Override
   public void addCustomer(Customer customer) {
       customers.add(customer);
   }

   @Override
   public void updateCustomer(Customer customer) {
       customers.add(customer);
   }
}

```

# Demo 3a

Java Persistance

## build.gradle

```groovy
implementation('org.springframework.boot:spring-boot-starter-data-jpa')
implementation('mysql:mysql-connector-java')
```

## CustomerRepository.java

```java
public interface CustomerRepository extends JpaRepository<Customer, Long> {
}
```

## CustomerServiceImpl.java

```java
@Service
public class CustomerServiceImpl implements CustomerService {
   @Autowired
   private CustomerRepository customerRepository;

   @Override
   public void addCustomer(Customer customer) {
       customerRepository.save(customer);
   }

   @Override
   public void updateCustomer(Customer customer) {
       customerRepository.save(customer);
   }

   @Override
   public void deleteCustomer(Customer customer) {
       customerRepository.deleteById(customer.getId());
   }
}
```

### Customer.java

```java
@Entity
@Table(name = "customer")
public class Customer {
   @Id
   @GeneratedValue
   private Long id;

   @Column
   private String firstName;
   @Column
   private String lastName;

   public Long getId() {
       return id;
   }

// ...

```

# Demo 3b

Root password, then microservices

```sh
sudo mysql --password
```

## application.properties

```
spring.jpa.hibernate.ddl-auto=create
spring.datasource.url=jdbc:mysql://localhost:3306/gadget
spring.datasource.username=db
spring.datasource.password=spring
```

# Demo 4

Modify Customer to add Birthday so that we can have more data to play with

## Customer.java

```java
@Entity
@Table(name = "customer")
public class Customer {
    @Id
    @GeneratedValue
    private Long id;
    @Column
    private String firstName;
    @Column
    private String lastName;
    @Column
    private Date birthday;

    public Long getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }
}
```

## CustomerService.java

Add a findAll() that passes through the CustomerRepository to get a list of customers to satisfy Customer requests.

```java
public interface CustomerService {
    void addCustomer(Customer customer);

    void updateCustomer(Customer customer);

    void deleteCustomer(Customer customer);

    public List<Customer> getCusotmers();
}
```

## CustomerServiceImpl.java

```java
@Service
public class CustomerServiceImpl implements CustomerService {

    @Autowired
    private CustomerRepository customerRepository;

    @Override
    public void addCustomer(Customer customer) {
        customerRepository.save(customer);
    }

    @Override
    public void updateCustomer(Customer customer) {
        customerRepository.save(customer);
    }

    @Override
    public void deleteCustomer(Customer customer) {
        customerRepository.delete(customer);
    }

    @Override
    public List<Customer> getCusotmers() {
        return customerRepository.findAll();
    }
}
```

## CustomerController.java

Add class level RequestMapping for CustomerController and root mapping to get a list of all customers

```java
@RestController
@RequestMapping(value = "/customers", produces = {"application/json"})
public class CustomerController {
    private static final SimpleDateFormat BIRTHDAY_TEXT_FORMAT = new SimpleDateFormat("YYYYMMdd");

    @Autowired
    CustomerService customerService;

    @RequestMapping("/")
    public List<Customer> get() {
        return customerService.getCustomers();
    }

    @RequestMapping(path = "/new", method = RequestMethod.GET)
    public Customer add(@RequestParam(name = "first") String firstName,
                        @RequestParam(name = "last") String lastName,
                        @RequestParam(name = "birthday", required = false) String birthdayText) {
        Customer customer = new Customer();
        customer.setFirstName(firstName);
        customer.setLastName(lastName);
        if (birthdayText != null) {
            try {
                customer.setBirthday(BIRTHDAY_TEXT_FORMAT.parse(birthdayText));
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        customerService.addCustomer(customer);
        return customer;
    }
}
```

## CustomerController.java

- Add class level RequestMapping for CustomerController and root mapping to get a list of all customers
  Change `/makeCustomer` to `/new`, add `RequestType.GET`
- Add another `new`/ `add` that takes a `Customer` as a `@RequestBody` parameter and has a `RequestType.PUT`
- Make `update()` that has a `RequestType.POST`
- Make `older()` `@RequestMapping` that returns older than `@PathVariable`
- Make `foo()` with crazy `@RequestMapping` value strings for wildcards

```java
@RestController
@RequestMapping(value = "/customers", produces = {"application/json"})
public class CustomerController {
    private static final SimpleDateFormat BIRTHDAY_TEXT_FORMAT = new SimpleDateFormat("YYYYMMdd");

    @Autowired
    CustomerService customerService;

    @RequestMapping("/")
    public List<Customer> get() {
        return customerService.getCustomers();
    }

    @RequestMapping(path = "/new", method = RequestMethod.GET)
    public Customer add(@RequestParam(name = "first") String firstName,
                        @RequestParam(name = "last") String lastName,
                        @RequestParam(name = "birthday", required = false) String birthdayText) {
        Customer customer = new Customer();
        customer.setFirstName(firstName);
        customer.setLastName(lastName);
        if (birthdayText != null) {
            try {
                customer.setBirthday(BIRTHDAY_TEXT_FORMAT.parse(birthdayText));
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

    @RequestMapping(path = "/update", method = RequestMethod.POST)
    public Customer update(@RequestBody Customer customer) {
        // todo update in database
        return customer;
    }

    @RequestMapping(path = "/older/{age}", method = RequestMethod.GET)
    public List<Customer> older(@PathVariable int age) {
        List<Customer> customers = customerService.getCustomers();
        Calendar old = Calendar.getInstance();
        old.setTime(new Date());
        old.add(Calendar.YEAR, age * -1);

        // todo make service and Repository method to do this (if only this were a JPA class)
        return customers.stream().filter(customer ->
                customer.getBirthday().before(old.getTime())).collect(Collectors.toList());
    }

    @RequestMapping(value={"/foo", "/foo/bar", "*.bar", "dove/*,**/data"})
    public String foo() {
        return "foo mapping success";
    }
}
```

## getOlderThan.http

Create test customers with http request

```
GET http://localhost:8080/customers/older/30
Accept: application/json
```

