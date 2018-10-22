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
