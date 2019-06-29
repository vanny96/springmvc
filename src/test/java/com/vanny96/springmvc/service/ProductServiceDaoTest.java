package com.vanny96.springmvc.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import com.vanny96.springmvc.domain.Product;
import com.vanny96.springmvc.services.ProductService;

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
public class ProductServiceDaoTest{
  private ProductService productService;

  @Autowired
  public void setProductService(ProductService productService) {
    this.productService = productService;
  }

  @Test
  public void testListMethod(){
    List<Product> products = productService.listAllProducts();
    
    int i = 1;
    for(Product product : products){
      assertEquals(product.getId(), i);
      i++;
    }


    assertEquals(products.size(), 5);
  }

  @Test
  public void testGetMethod(){
    Product product = productService.getProductById(1);
    
    assertEquals(product.getId(), 1);
  }

  @Test
  public void testDeleteMethodAndSave(){
    List<Product> products = productService.listAllProducts();
    assertEquals(products.size(), 5);

    productService.removeProductById(2);
    products = productService.listAllProducts();
    assertEquals(products.size(), 4);


    Product product = new Product();
    productService.saveOrUpdateProduct(product);
    products = productService.listAllProducts();
    assertEquals(products.size(), 5);
  }

}