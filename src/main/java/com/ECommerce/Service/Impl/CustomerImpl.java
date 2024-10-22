package com.ECommerce.Service.Impl;

import com.ECommerce.Entity.Customer;
import com.ECommerce.Entity.Enums.Roles;
import com.ECommerce.Exception.ResourceNotFoundException;
import com.ECommerce.Repository.CustomerRepo;
import com.ECommerce.Service.CustomerService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerImpl implements CustomerService {

    public CustomerRepo customerRepo;

    public CustomerImpl(CustomerRepo customerRepo) {
        this.customerRepo = customerRepo;
    }

    @Override
    public Customer createCustomer(Customer customer) {
        customer.setRole(Roles.USER);
        return this.customerRepo.save(customer);
    }

    @Override
    public Customer getCustomerById(Integer customerId) {
        return this.customerRepo.findById(customerId).orElseThrow(() ->
                new ResourceNotFoundException("Customer not found along with the ID : " + customerId));
    }

    @Override
    public List<Customer> getAllCustomer() {
        return this.customerRepo.findAll();
    }

    @Override
    public String deleteCustomer(Integer customerId) {
        this.customerRepo.deleteById(customerId);
        return "Customer deleted successfully";
    }
}
