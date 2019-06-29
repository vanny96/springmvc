package com.vanny96.springmvc.services;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;

import com.vanny96.springmvc.domain.Customer;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

@Service
@Profile("dao")
public class CustomerServiceDao implements CustomerService {
  EntityManagerFactory emf;

  @PersistenceUnit
  public void setEmf(EntityManagerFactory emf) {
    this.emf = emf;
  }


  @Override
  public List<Customer> listAllCustomers() {
    EntityManager em = emf.createEntityManager();
    List<Customer> customers = em.createQuery("from Customer", Customer.class).getResultList();
    em.close();

    return customers;
  }

  @Override
  public Customer getCostumerById(Integer id) {
    EntityManager em = emf.createEntityManager();
    Customer customer = em.find(Customer.class, id);
    em.close();

    return customer;  
  }

  @Override
  public Customer saveOrUpdateCustomer(Customer customer) {
    EntityManager em = emf.createEntityManager();

    em.getTransaction().begin();
    Customer savedCustomer = em.merge(customer);
    em.getTransaction().commit();
    em.close();

    return savedCustomer;
  }

  @Override
  public Customer deleteCustomer(Integer id) {
    EntityManager em = emf.createEntityManager();

    em.getTransaction().begin();
    Customer removedCustomer = em.find(Customer.class, id);
    em.remove(removedCustomer);
    em.getTransaction().commit();
    em.close();

    return removedCustomer;  }

}