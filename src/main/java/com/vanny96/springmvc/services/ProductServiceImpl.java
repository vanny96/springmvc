package com.vanny96.springmvc.services;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.vanny96.springmvc.domain.Product;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

@Service
@Profile("implementation")
public class ProductServiceImpl implements ProductService {

  private Map<Integer, Product> products;

  public ProductServiceImpl(){
    products = new HashMap<Integer, Product>();
  }

  @Override
  public List<Product> listAllProducts() {
    return new ArrayList<>(products.values());
  }

  @Override
  public Product getProductById(Integer id) {
    return products.get(id);
  }

  @Override
  public Product saveOrUpdateProduct(Product product) {
    if(product != null){
      if(product.getId() == null){
        product.setId(getNextKey());
      }

      products.put(product.getId(), product);
      return product;
      
    } else {
      throw new RuntimeException("Product can't be null");
    }
  }

  @Override
  public Product removeProductById(Integer id) {
    return products.remove(id); 
  }

  private int getNextKey(){
    int id = 1;
    if(!products.isEmpty()){
      id = Collections.max(products.keySet()) + 1;
    }
    return id;
  }
  
}