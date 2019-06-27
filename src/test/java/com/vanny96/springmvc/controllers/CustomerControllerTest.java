package com.vanny96.springmvc.controllers;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import com.vanny96.springmvc.domain.Customer;
import com.vanny96.springmvc.domain.Product;
import com.vanny96.springmvc.services.CustomerService;
import com.vanny96.springmvc.services.ProductService;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertEquals;
import static org.mockito.BDDMockito.*;
import static org.mockito.BDDMockito.any;

public class CustomerControllerTest{
  @Mock
  CustomerService customerService;

  @InjectMocks
  CustomerController customerController;

  private MockMvc mockMvc;

  @Before
  public void setup() {
    MockitoAnnotations.initMocks(this);
    mockMvc = MockMvcBuilders.standaloneSetup(customerController).build();
  }

  @Test
  public void testList() throws Exception {
    List<Customer> customers = new ArrayList<Customer>();
    customers.add(new Customer());
    customers.add(new Customer());

    when(customerService.listAllCustomers()).thenReturn(customers);
    mockMvc.perform(get("/customers"))
            .andExpect(status().isOk())
            .andExpect(view().name("customer/index"))
            .andExpect(model().attribute("customers", hasSize(2)));
  }

  @Test
  public void testView() throws Exception {
    Integer id = 1;

    when(customerService.getCostumerById(id)).thenReturn(new Customer());

    mockMvc.perform(get("/customer/" + id))
            .andExpect(status().isOk())
            .andExpect(view().name("customer/view"))
            .andExpect(model().attribute("customer", instanceOf(Customer.class)));
  }

  @Test
  public void editTest() throws Exception{
    Integer id = 1;

    when(customerService.getCostumerById(id)).thenReturn(new Customer());

    mockMvc.perform(get("/customer/" + id + "/edit"))
            .andExpect(status().isOk())
            .andExpect(view().name("customer/form"))
            .andExpect(model().attribute("customer", instanceOf(Customer.class)));
  }

  @Test
  public void newTest() throws Exception{
    verifyZeroInteractions(customerService);

    mockMvc.perform(get("/customer/new"))
            .andExpect(status().isOk())
            .andExpect(view().name("customer/form"))
            .andExpect(model().attribute("customer", instanceOf(Customer.class)));
  }

  @Test
  public void saveOrUpdate() throws Exception{
    Integer id = 1;
    String firstName = "Giovanni";
    String lastName = "Barbaro";
    String email="test@mail.com";
    String phoneNumber="3333940502";
    String addressLine1="random address";
    String addressLine2="another random address";
    String city="fun town";
    String state="sad state";
    String zipCode="123";

    Customer customer = new Customer();
    customer.setId(id);
    customer.setFirstName(firstName);
    customer.setLastName(lastName);
    customer.setEmail(email);
    customer.setPhoneNumber(phoneNumber);
    customer.setAddressLine1(addressLine1);
    customer.setAddressLine2(addressLine2);
    customer.setCity(city);
    customer.setState(state);
    customer.setZipCode(zipCode);

    when(customerService.saveOrUpdateCustomer(any(Customer.class))).thenReturn(customer);

    mockMvc.perform(post("/customers")
    .param("id", String.valueOf(id))
    .param("firstName", firstName)
    .param("lastName", lastName)
    .param("email", email)
    .param("phoneNumber", phoneNumber)
    .param("addressLine1", addressLine1)
    .param("addressLine2", addressLine2)
    .param("city", city)
    .param("state", state)
    .param("zipCode", zipCode)
    )
            .andExpect(status().is3xxRedirection())
            .andExpect(view().name("redirect:/customer/1"))
            .andExpect(model().attribute("customer", instanceOf(Customer.class)))
            .andExpect(model().attribute("customer", hasProperty("id", is(id))))
            .andExpect(model().attribute("customer", hasProperty("firstName", is(firstName))))
            .andExpect(model().attribute("customer", hasProperty("lastName", is(lastName))))
            .andExpect(model().attribute("customer", hasProperty("email", is(email))))
            .andExpect(model().attribute("customer", hasProperty("phoneNumber", is(phoneNumber))))
            .andExpect(model().attribute("customer", hasProperty("addressLine1", is(addressLine1))))
            .andExpect(model().attribute("customer", hasProperty("addressLine2", is(addressLine2))))
            .andExpect(model().attribute("customer", hasProperty("city", is(city))))
            .andExpect(model().attribute("customer", hasProperty("state", is(state))))
            .andExpect(model().attribute("customer", hasProperty("zipCode", is(zipCode))))
            ;
            
    ArgumentCaptor<Customer> boundCustomer = ArgumentCaptor.forClass(Customer.class);
    verify(customerService).saveOrUpdateCustomer(boundCustomer.capture());

    assertEquals(id, boundCustomer.getValue().getId());
    assertEquals(firstName, boundCustomer.getValue().getFirstName());
    assertEquals(lastName,  boundCustomer.getValue().getLastName());
    assertEquals(email, boundCustomer.getValue().getEmail());
    assertEquals(phoneNumber, boundCustomer.getValue().getPhoneNumber());
    assertEquals(addressLine1, boundCustomer.getValue().getAddressLine1());
    assertEquals(addressLine2, boundCustomer.getValue().getAddressLine2());
    assertEquals(city, boundCustomer.getValue().getCity());
    assertEquals(state, boundCustomer.getValue().getState());
    assertEquals(zipCode, boundCustomer.getValue().getZipCode());
  }

  @Test
  public void delete() throws Exception {

    mockMvc.perform(get("/customer/1/delete"))
            .andExpect(status().is3xxRedirection())
            .andExpect(view().name("redirect:/customers"));

    verify(customerService, times(1)).deleteCustomer(1);
            
  }
}