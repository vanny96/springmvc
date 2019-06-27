package com.vanny96.springmvc.controllers;

import com.vanny96.springmvc.domain.Customer;
import com.vanny96.springmvc.services.CustomerService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class CustomerController{
  private CustomerService customerService;

  @Autowired
  public void setCustomerService(CustomerService customerService) {
    this.customerService = customerService;
  }

  @RequestMapping("/customers")
  public String listAllCustomers(Model model){
    model.addAttribute("customers", customerService.listAllCustomers());
    return "customer/index";
  }

  @RequestMapping("/customer/{id}")
  public String getCustomer(Model model, @PathVariable Integer id){
    model.addAttribute("customer", customerService.getCostumerById(id));
    return "customer/view";
  }

  @RequestMapping("/customer/new")
  public String newCustomer(Model model){
    model.addAttribute("customer", new Customer());
    return "customer/form";
  }

  @RequestMapping(value = "/customers", method = RequestMethod.POST)
  public String saveOrUpdateCustomer(Customer customer){
    Customer savedCustomer = customerService.saveOrUpdateCustomer(customer);
    return "redirect:/customer/" + savedCustomer.getId();
  }

  @RequestMapping("/customer/{id}/edit")
  public String editCustomer(Model model, @PathVariable Integer id){
    model.addAttribute("customer", customerService.getCostumerById(id));
    return "customer/form";
  }

  @RequestMapping("/customer/{id}/delete")
  public String deleteCustomer(@PathVariable Integer id){
    customerService.deleteCustomer(id);
    return "redirect:/customers";
  }
}