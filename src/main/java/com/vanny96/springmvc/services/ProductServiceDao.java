package com.vanny96.springmvc.services;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;

import com.vanny96.springmvc.domain.Product;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

@Service
@Profile("dao")
public class ProductServiceDao implements ProductService {
  private EntityManagerFactory emf;

  @PersistenceUnit
  public void setEmf(EntityManagerFactory emf) {
    this.emf = emf;
  }


  @Override
  public List<Product> listAllProducts() {
    EntityManager em = emf.createEntityManager();

    return em.createQuery("from Product", Product.class).getResultList();
  }

  @Override
  public Product getProductById(Integer id) {
    EntityManager em = emf.createEntityManager();

    return em.find(Product.class, id);
  }

  @Override
  public Product saveOrUpdateProduct(Product product) {
    EntityManager em = emf.createEntityManager();

    em.getTransaction().begin();
    Product savedProduct = em.merge(product);
    em.getTransaction().commit();
    em.close();

    return savedProduct;
  }

  @Override
  public Product removeProductById(Integer id) {
    EntityManager em = emf.createEntityManager();

    em.getTransaction().begin();
    Product removedProduct = em.find(Product.class, id);
    em.remove(removedProduct);
    em.getTransaction().commit();
    em.close();

    return removedProduct;
  }
  
}