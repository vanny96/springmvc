package com.vanny96.springmvc.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import com.vanny96.springmvc.domain.Customer;
import com.vanny96.springmvc.services.CustomerService;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@ActiveProfiles("dao")
public class CustomerServiceDaoTest{
  private CustomerService customerService;

  @Autowired
  public void setProductService(CustomerService customerService) {
    this.customerService = customerService;
  }

  @Test
  public void testListMethod(){
    List<Customer> customers = customerService.listAllCustomers();

    assertEquals(customers.size(), 3);
  }

  @Test
  public void testGetMethod(){
    Customer customer = customerService.getCostumerById(6);
    
    assertEquals(customer.getId(), 6);
  }

  @Test
  public void testDeleteMethod(){
    List<Customer> customers = customerService.listAllCustomers();
    assertEquals(customers.size(), 3);

    customerService.deleteCustomer(7);
    customers = customerService.listAllCustomers();
    assertEquals(customers.size(), 2);

    Customer customer = new Customer();
    customerService.saveOrUpdateCustomer(customer);
    customers = customerService.listAllCustomers();
    assertEquals(customers.size(), 3);
  }
}