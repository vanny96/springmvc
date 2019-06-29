package com.vanny96.springmvc.bootstrap;

import java.math.BigDecimal;

import com.vanny96.springmvc.domain.Customer;
import com.vanny96.springmvc.domain.Product;
import com.vanny96.springmvc.services.CustomerService;
import com.vanny96.springmvc.services.ProductService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

@Component
public class SpringJPABootstrap implements ApplicationListener<ContextRefreshedEvent>{

  private ProductService productService;
  private CustomerService customerService;

  @Autowired
  public void setProductService(ProductService productService) {
    this.productService = productService;
  }

  @Autowired
  public void setCustomerService(CustomerService customerService) {
    this.customerService= customerService;
  }

  
  @Override
  public void onApplicationEvent(ContextRefreshedEvent event) {
    loadProducts();
    loadCustomers();
  }

  public void loadProducts(){

    Product product1 = new Product();
    product1.setDescription("Product 1");
    product1.setPrice(new BigDecimal("12.99"));
    product1.setImageUrl("http://example.com/product1");
    productService.saveOrUpdateProduct(product1);

    Product product2 = new Product();
    product2.setDescription("Product 2");
    product2.setPrice(new BigDecimal("14.99"));
    product2.setImageUrl("http://example.com/product2");
    productService.saveOrUpdateProduct(product2);

    Product product3 = new Product();
    product3.setDescription("Product 3");
    product3.setPrice(new BigDecimal("34.99"));
    product3.setImageUrl("http://example.com/product3");
    productService.saveOrUpdateProduct(product3);

    Product product4 = new Product();
    product4.setDescription("Product 4");
    product4.setPrice(new BigDecimal("44.99"));
    product4.setImageUrl("http://example.com/product4");
    productService.saveOrUpdateProduct(product4);
    
    Product product5 = new Product();
    product5.setDescription("Product 5");
    product5.setPrice(new BigDecimal("25.99"));
    product5.setImageUrl("http://example.com/product5");
    productService.saveOrUpdateProduct(product5);
  }

  public void loadCustomers(){
    Customer customer1 = new Customer();
    customer1.setFirstName("Joe");
    customer1.setLastName("Coppola");
    customer1.setEmail("test1@mail.it");
    customer1.setPhoneNumber("1");
    customer1.setAddressLine1("first road");
    customer1.setAddressLine2("first road 2");
    customer1.setCity("first city");
    customer1.setState("first state");
    customerService.saveOrUpdateCustomer(customer1);

    Customer customer2 = new Customer();
    customer2.setFirstName("Giovanna");
    customer2.setLastName("Giorno");
    customer2.setEmail("test2@mail.it");
    customer2.setPhoneNumber("2");
    customer2.setAddressLine1("second road");
    customer2.setAddressLine2("second road 2");
    customer2.setCity("second city");
    customer2.setState("second state");
    customerService.saveOrUpdateCustomer(customer2);

    Customer customer3 = new Customer();
    customer3.setFirstName("Bruno");
    customer3.setLastName("Bucciarati");
    customer3.setEmail("test3@mail.it");
    customer3.setPhoneNumber("3");
    customer3.setAddressLine1("third road");
    customer3.setAddressLine2("third road 2");
    customer3.setCity("third city");
    customer3.setState("third state");
    customerService.saveOrUpdateCustomer(customer3);
  }
}