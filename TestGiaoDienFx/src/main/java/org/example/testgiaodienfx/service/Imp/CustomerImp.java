package org.example.testgiaodienfx.service.Imp;

import org.example.testgiaodienfx.model.Customers;

import java.util.List;

public interface CustomerImp {
    boolean addCustomer(Customers customers);
    List<Customers> getAllCustomers();
    boolean updateCustomer(Customers customers);
    boolean deleteCustomer(int customerId);
}
