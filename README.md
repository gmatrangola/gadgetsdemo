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

# Demo 5: Code Review

1. path = “/customers”, consumes and produces default for class
2. value -> path
3. Add RequestMethod

## CustomerController.java

```java
@RestController
@RequestMapping(value = "/customers", produces = {"application/json"})
public class CustomerController {
    private static final SimpleDateFormat BIRTHDAY_TEXT_FORMAT = new SimpleDateFormat("YYYYMMdd");

    @Autowired
    CustomerService customerService;

    @RequestMapping(path = "/", method = RequestMethod.GET)
    public List<Customer> get() {
        return customerService.getCustomers();
    }
    // ...
```

# Demo 6: Unit Tests

1. Use IDE to automatically create JUnit for Customer class
2. Create convenience constructor
3. Create a Before condition to set up a cal and customer field for test data
4. Fill in each of the getters and setters with asserts etc.

## CustomerTest.java

```java
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
        assertEquals("test", customer.getFirstName());
    }

    @Test
    public void setFirstName() {
        Customer fn = new Customer();
        fn.setFirstName("first");
        assertEquals("first", fn.getFirstName());
    }

    @Test
    public void getLastName() {
        assertEquals("user", customer.getLastName());
    }

    @Test
    public void setLastName() {
        Customer ln = new Customer();
        ln.setLastName("last");
        assertEquals("last", ln.getLastName());
    }

    @Test
    public void getBirthday() {
        assertEquals(cal.getTime(), customer.getBirthday());
    }

    @Test
    public void setBirthday() {
        Date date = new Date();
        Customer bd = new Customer();
        bd.setBirthday(date);
        assertEquals(date, bd.getBirthday());
    }
}
```

# Demo 7: Rest Testing Part 1

1. Handy reusable base class for controller tests

## ControllerTest.java

```java
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

public abstract class ControllerTest {
    protected static final MediaType JSON_CONTENT_TYPE = new MediaType(MediaType.APPLICATION_JSON.getType(),
            MediaType.APPLICATION_JSON.getSubtype(),
            Charset.forName("utf8"));
    protected MockMvc mockMvc;
    @Autowired
    protected WebApplicationContext webApplicationContext;
    private HttpMessageConverter mappingJackson2HttpMessageConverter;

    @Autowired
    void setConverters(HttpMessageConverter<?>[] converters) {

        mappingJackson2HttpMessageConverter = Arrays.stream(converters)
                .filter(hmc -> hmc instanceof MappingJackson2HttpMessageConverter)
                .findAny()
                .orElse(null);

        assertNotNull("the JSON message converter must not be null",this.mappingJackson2HttpMessageConverter);
    }

    protected String json(Object o) throws IOException {
        MockHttpOutputMessage mockHttpOutputMessage = new MockHttpOutputMessage();
        this.mappingJackson2HttpMessageConverter.write(o, MediaType.APPLICATION_JSON, mockHttpOutputMessage);
        return mockHttpOutputMessage.getBodyAsString();
    }

    protected void setup() {
        mockMvc = webAppContextSetup(webApplicationContext).build();
    }
}

```

1. Create a CustomerControllerTest Class in the test classpath in the same package as the real CustomerController.
2. Annotate with test annotations @RunWith, @SpringBootTest, @WebApplicationContext
3. Create a JSON_CONTENT_TYPE MediaType to be used later
4. Autowire the WebApplicationContext
5. Wire up the CustomerRepository to prep for tests.
6. Add test data using the repository in the setup() method
7. Create a setup (annotated with @Before) and initialize the mockMvc
8. Create tests for REST entry points using mockMvc
9. Notice that testAddWithJSON() fails

## CustomerControllerTest

```java
@RunWith(SpringRunner.class)
@SpringBootTest(classes = GadgetsApplication.class)
@WebAppConfiguration
public class CustomerControllerTest extends ControllerTest {
    protected static final MediaType JSON_CONTENT_TYPE = new MediaType(MediaType.APPLICATION_JSON.getType(),
            MediaType.APPLICATION_JSON.getSubtype(),
            Charset.forName("utf8"));

    private MockMvc mockMvc;

    @Autowired
    protected CustomerRepository customerRepository;
    private Customer customer1;
    private Customer customer2;

    @Before
    public void setup() {
        mockMvc = webAppContextSetup(webApplicationContext).build();
        Calendar calendar = Calendar.getInstance();
        calendar.set(1990, Calendar.JUNE, 28, 1, 0, 0);
        calendar.setTimeZone(TimeZone.getDefault());
        Date bday = new Date(calendar.getTimeInMillis());
        customer1 = customerRepository.save(new Customer("First1", "Last1", bday));
        customer2 = customerRepository.save(new Customer("First2", "Last2", bday));
    }

    @Test
    public void testGetCustomer() throws Exception {
        mockMvc.perform(get("/customers/" + customer1.getId()).contentType(JSON_CONTENT_TYPE))
                .andExpect(status().isOk())
                .andExpect(content().contentType(JSON_CONTENT_TYPE))
                .andExpect(content().json(json(customer1)));
    }

    @Test
    public void testAddWithJSON() throws Exception {
        Date bday = new Date();
        String jday = json(bday).replace("\"", "");
        mockMvc.perform(put("/customers/add")
                .content(json(new Customer("first", "last", bday)))
                .contentType(JSON_CONTENT_TYPE))
                .andExpect(status().isOk())
                .andExpect(content().contentType(JSON_CONTENT_TYPE))
                .andExpect(jsonPath("$.firstName", is("first")))
                .andExpect(jsonPath("$.lastName", is("last")))
                .andExpect(jsonPath("$.birthday", is(jday)));
    }
}
```
# Demo 8a: Change Date Format

1. Add @JsonFormat to Customer.birthday
2. Retest with testGetCustomer

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
    @JsonFormat(pattern = "MM-dd-yyyy")
    private Date birthday;
    // ...
```

Now `testAddWithJSON` fails because

```java
 Date bday = new Date();
 String jday = json(bday).replace("\"", "");
```

## CustomerControllerTest.java

```java
// ...
    @Test
    public void testAddWithJSON() throws Exception {
        Date bday = new Date();
        Calendar cal = Calendar.getInstance();
        cal.setTime(bday);
        String jday = (cal.get(Calendar.MONTH)+1 )+ "-" + (cal.get(Calendar.DAY_OF_MONTH)+1) + "-" +
                cal.get(Calendar.YEAR);
        mockMvc.perform(put("/customers/add")
                .content(json(new Customer("first", "last", bday)))
                .contentType(JSON_CONTENT_TYPE))
                .andExpect(status().isOk())
                .andExpect(content().contentType(JSON_CONTENT_TYPE))
                .andExpect(jsonPath("$.firstName", is("first")))
                .andExpect(jsonPath("$.lastName", is("last")))
                .andExpect(jsonPath("$.birthday", is(jday)));
    }
// ...
```

# Demo 8b: Relatioinships in JSON

1. Create Gadget Model Class
2. Setup JPA Relation to Customer

## Gadget.java

```java
@Entity
@Table(name = "gadget")
public class Gadget {
    @Id
    @GeneratedValue
    private Long id;

    @Column
    private String name;

    @Column(name = "isOn")
    private boolean on;

    @ManyToOne
    @JoinColumn(name = "owner_id", nullable = true)
    private Customer owner;

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isOn() {
        return on;
    }

    public void setOn(boolean on) {
        this.on = on;
    }

    public Customer getOwner() {
        return owner;
    }

    public void setOwner(Customer owner) {
        this.owner = owner;
    }
}
```

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
    @JsonFormat(pattern = "MM-dd-yyyy")
    private Date birthday;
    @OneToMany(mappedBy = "owner", cascade = ALL, fetch = FetchType.EAGER)
    private Set<Gadget> gadgets;
// ...
```

## GadgetService.java

```java
@RestController
@RequestMapping(value = "/gadgets", produces = {"application/json"})
public class GadgetController {

    @Autowired
    GadgetRepository gadgetRepository;

    @Autowired
    CustomerRepository customerRepository;

    @RequestMapping
    public List<Gadget> get() {
        return gadgetRepository.findAll();
    }

    @RequestMapping("/{id}")
    public Gadget getById(@PathVariable("id") Long id) {
        return gadgetRepository.findById(id).get();
    }

    @RequestMapping(path = "/add", method = RequestMethod.PUT)
    public Gadget add(@RequestBody Gadget gadget) {
        return gadgetRepository.save(gadget);
    }

    @RequestMapping(path = "/assignUser", method = RequestMethod.GET)
    public void assignUser(@RequestParam Long gadgetId, @RequestParam Long customerId) {
        Optional<Customer> customer = customerRepository.findById(customerId);
        Optional<Gadget> gadget = gadgetRepository.findById(gadgetId);
        if (customer.isPresent()) {
            if (gadget.isPresent()) {
                gadget.get().setOwner(customer.get());
                gadgetRepository.save(gadget.get());
            }
        }
    }
}
```

## Gadget.http

```
PUT http://127.0.0.1:8080/customers/add
Content-Type: application/json

{
  "firstName": "Alice",
  "lastName": "Merton",
  "birthday": "1993-09-13"
}

###

PUT http://127.0.0.1:8080/gadgets/add
Content-Type: application/json

{
  "name": "light",
  "on": "true"
}

###

GET http://127.0.0.1:8080/gadgets/assignUser?gadgetId=2&customerId=1

###

# Check IDs in database first
GET http://127.0.0.1:8080/gadgets/2

###
```

# Demo 8d: Fix Recursion

## Gadget.java

```java
@Entity
@Table(name = "gadget")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class,  property = "id")
public class Gadget {
    @Id
    @GeneratedValue
    private Long id;

    @Column
    private String name;

    @Column(name = "isOn")
    private boolean on;

    @ManyToOne
    @JoinColumn(name = "owner_id", nullable = true)
    @JsonIdentityReference(alwaysAsId = true)
    private Customer owner;
    // ...
```

## Customer.java

```java
@Entity
@Table(name = "customer")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class,  property = "id")
public class Customer {
    @Id
    @GeneratedValue
    private Long id;
    @Column
    private String firstName;
    @Column
    private String lastName;
    @Column
    @JsonFormat(pattern = "MM-dd-yyyy")
    private Date birthday;
    @OneToMany(mappedBy = "owner", cascade = ALL, fetch = FetchType.EAGER)
    @JsonIdentityReference(alwaysAsId = true)
    private Set<Gadget> gadgets;
//...
```

# Demo 9: Custom JSON

1. Create Color Class with red, green, and blue fields in the model package
2. Add ORM annotations for @Entitiy, @Table, @Id, etc
3. Add color field to Gadget with @ManyToOne and @JoinColumn references
4. Create ColorSeralizer and ColorDeseralizer classes
5. Add `@JsonSerialize(using = ColorSeralizer.class) and @JsonDeserialize(using = ColorDeseralizer.class) annotations to Color`

## Color.java

```java
@Entity
@Table(name = "color")
@JsonSerialize(using = ColorSeralizer.class)
@JsonDeserialize(using = ColorDeserializer.class)
public class Color {
    @Id
    @GeneratedValue
    private Long id;

    private int red;
    private int green;
    private int blue;

    public Long getId() {
        return id;
    }

    public int getRed() {
        return red;
    }

    public void setRed(int red) {
        this.red = red;
    }
// ...
```

## Gadget.java

```java
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="color_id", nullable = true)
    private Color color;
```

### Test without custom serializer

## ColorGadget.http

```json
PUT http://127.0.0.1:8080/gadgets/add
Content-Type: application/json

{
  "name": "Christmaslight",
  "on": "true",
  "color" : {
    "red" : "0",
    "green" : "255",
    "blue" : "0"
  }
}

###
```

### data.serialization package

## ColorSerializer.java

```java
public class ColorSerializer extends JsonSerializer<Color> {
    @Override
    public void serialize(Color value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
        gen.writeStartObject();
        gen.writeStringField("rgb", String.format("%02X%02X%02X", value.getRed(), value.getGreen(), value.getBlue()));
        gen.writeEndObject();
    }
}
```

## ColorDeserializer.java

```java
public class ColorDeserializer extends JsonDeserializer<Color> {
    @Override
    public Color deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JsonProcessingException {
        JsonNode node = p.getCodec().readTree(p);
        String rgb = node.get("rgb").textValue();
        Color color = new Color();
        color.setRed(Integer.valueOf(rgb.substring(0,2), 16));
        color.setGreen(Integer.valueOf(rgb.substring(2,4), 16));
        color.setBlue(Integer.valueOf(rgb.substring(4,6), 16));
        return color;
    }
}
```

### Test with new Serializer

## RgbGadget.http

```json
PUT http://127.0.0.1:8080/gadgets/add
Content-Type: application/json

{
  "name": "Christmaslight",
  "on": "true",
  "color" : { "rgb": "00FF00"}
}
```

#