package com.ECommerce.Controller;

import com.ECommerce.Entity.Customer;
import com.ECommerce.Service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/customer")
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    // 1. Create
    @PostMapping("/")
    public ResponseEntity<Customer> create(@RequestBody Customer customer){
        return new ResponseEntity<>(this.customerService.createCustomer(customer), HttpStatus.CREATED);
    }

    // 2. Get By Id
    @GetMapping("/{customerId}")
    public ResponseEntity<Customer> getById(@PathVariable Integer customerId){
        return new ResponseEntity<>(this.customerService.getCustomerById(customerId), HttpStatus.OK);
    }

    // 3. Get All
    @GetMapping("/")
    public ResponseEntity<List<Customer>> getAll(){
        return new ResponseEntity<>(this.customerService.getAllCustomer(), HttpStatus.OK);
    }

    // 4. Delete
    @DeleteMapping("/{customerId}")
    public ResponseEntity<String> delete(@PathVariable Integer customerId){
        return new ResponseEntity<>(this.customerService.deleteCustomer(customerId), HttpStatus.OK);
    }

}
