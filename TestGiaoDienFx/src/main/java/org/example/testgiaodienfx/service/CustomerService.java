package org.example.testgiaodienfx.service;

import org.example.testgiaodienfx.model.Customers;
import org.example.testgiaodienfx.repository.CustomerRepository;
import org.example.testgiaodienfx.service.Imp.CustomerImp;

import java.util.List;

public class CustomerService implements CustomerImp {
    CustomerRepository customerRepository = new CustomerRepository();
    @Override
    public boolean addCustomer(Customers customers) {
        return customerRepository.addCustomer(customers);
    }

    @Override
    public List<Customers> getAllCustomers() {
        return customerRepository.getAllCustomers();
    }

    @Override
    public boolean updateCustomer(Customers customers) {
        return customerRepository.updateCustomer(customers);
    }

    @Override
    public boolean deleteCustomer(int customerId) {
        return customerRepository.deleteCustomer(customerId);
    }
}
