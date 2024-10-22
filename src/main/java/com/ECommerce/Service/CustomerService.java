package com.ECommerce.Service;


import com.ECommerce.Entity.Customer;

import java.util.List;

public interface CustomerService {

    // 1. Create Customer
    public Customer createCustomer(Customer customer);

    // 2. Get By Id
    public Customer getCustomerById(Integer customerId);

    // 3. List of Customer
    public List<Customer> getAllCustomer();

    // 4. Delete Customer
    public String deleteCustomer(Integer customerId);

//    // 5. Update Customer
//    public Customer updateCustomer(Customer customer);

}
