package com.vanny96.springmvc.services;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.vanny96.springmvc.domain.Customer;

import org.springframework.stereotype.Service;

@Service
public class CustomerServiceImpl implements CustomerService {
  private Map<Integer, Customer> customers;


  public CustomerServiceImpl() {
    this.customers = new HashMap<Integer, Customer>();
  }
  

  @Override
  public List<Customer> listAllCustomers() {
    return new ArrayList<>(customers.values());
  }

  
  public Customer getCostumerById(Integer id) {
    return customers.get(id);
  }

 
  public Customer saveOrUpdateCustomer(Customer customer) {
    if(customer != null){
      if(customer.getId() == null){
        customer.setId(getNextInt());
      }

      customers.put(customer.getId(), customer);
      return customer;

    } else {
      throw new RuntimeException("Customer cannot be null");
    }
  }


  public Customer deleteCustomer(Integer id) {
    return customers.remove(id);
  }

  private int getNextInt(){
    int id = 1;
    if(!customers.isEmpty()){
      id = Collections.max(customers.keySet()) + 1;
    }
    return id;
  }

}