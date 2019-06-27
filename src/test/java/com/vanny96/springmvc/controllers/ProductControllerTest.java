package com.vanny96.springmvc.controllers;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import com.vanny96.springmvc.domain.Product;
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


public class ProductControllerTest {
  @Mock
  ProductService productService;

  @InjectMocks
  ProductController productController;

  private MockMvc mockMvc;

  @Before
  public void setup() {
    MockitoAnnotations.initMocks(this);
    mockMvc = MockMvcBuilders.standaloneSetup(productController).build();
  }

  @Test
  public void testList() throws Exception {
    List<Product> products = new ArrayList<Product>();
    products.add(new Product());
    products.add(new Product());

    when(productService.listAllProducts()).thenReturn(products);
    mockMvc.perform(get("/products"))
            .andExpect(status().isOk())
            .andExpect(view().name("product/index"))
            .andExpect(model().attribute("products", hasSize(2)));
  }

  @Test
  public void testView() throws Exception {
    Integer id = 1;

    when(productService.getProductById(id)).thenReturn(new Product());

    mockMvc.perform(get("/product/" + id))
            .andExpect(status().isOk())
            .andExpect(view().name("product/view"))
            .andExpect(model().attribute("product", instanceOf(Product.class)));
  }

  @Test
  public void editTest() throws Exception{
    Integer id = 1;

    when(productService.getProductById(id)).thenReturn(new Product());

    mockMvc.perform(get("/product/" + id + "/edit"))
            .andExpect(status().isOk())
            .andExpect(view().name("product/form"))
            .andExpect(model().attribute("product", instanceOf(Product.class)));
  }

  @Test
  public void newTest() throws Exception{
    verifyZeroInteractions(productService);

    mockMvc.perform(get("/product/new"))
            .andExpect(status().isOk())
            .andExpect(view().name("product/form"))
            .andExpect(model().attribute("product", instanceOf(Product.class)));
  }

  @Test
  public void saveOrUpdate() throws Exception{
    Integer id = 1;
    String description = "description";
    BigDecimal price= new BigDecimal("12.00");
    String url="generic.url";

    Product product = new Product();
    product.setId(id);
    product.setDescription(description);
    product.setPrice(price);
    product.setImageUrl(url);

    when(productService.saveOrUpdateProduct(any(Product.class))).thenReturn(product);

    mockMvc.perform(post("/product")
    .param("id", String.valueOf(id))
    .param("description", description)
    .param("price", "12.00")
    .param("imageUrl", url))
            .andExpect(status().is3xxRedirection())
            .andExpect(view().name("redirect:/product/1"))
            .andExpect(model().attribute("product", instanceOf(Product.class)))
            .andExpect(model().attribute("product", hasProperty("id", is(id))))
            .andExpect(model().attribute("product", hasProperty("description", is(description))));
            
    ArgumentCaptor<Product> boundProduct = ArgumentCaptor.forClass(Product.class);
    verify(productService).saveOrUpdateProduct(boundProduct.capture());
    assertEquals(id, boundProduct.getValue().getId());
    assertEquals(description, boundProduct.getValue().getDescription());
    assertEquals(price, boundProduct.getValue().getPrice());
    assertEquals(url, boundProduct.getValue().getImageUrl());
  }

  @Test
  public void delete() throws Exception {

    mockMvc.perform(get("/product/1/delete"))
            .andExpect(status().is3xxRedirection())
            .andExpect(view().name("redirect:/products"));

    verify(productService, times(1)).removeProductById(1);
            
  }

}