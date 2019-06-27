package com.vanny96.springmvc.services;

import java.util.List;

import com.vanny96.springmvc.domain.Customer;

public interface CustomerService {
  List<Customer> listAllCustomers();

  Customer getCostumerById(Integer id);

  Customer saveOrUpdateCustomer(Customer customer);

  Customer deleteCustomer(Integer id);
}