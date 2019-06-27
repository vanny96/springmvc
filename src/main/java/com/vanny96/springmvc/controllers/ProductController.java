package com.vanny96.springmvc.controllers;

import com.vanny96.springmvc.domain.Product;
import com.vanny96.springmvc.services.ProductService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class ProductController{
  
  private ProductService productService;

  @Autowired
  public void setProductService(ProductService productService) {
    this.productService = productService;
  }
  

  @RequestMapping("products")
  public String listProducts(Model model){
    model.addAttribute("products", productService.listAllProducts());

    return "products";
  }

  @RequestMapping("product/{id}")
  public String listProduct(Model model,@PathVariable Integer id){
    
    model.addAttribute("product", productService.getProductById(id));
    return "product";
  }

  @RequestMapping("product/new")
  public String newProduct(Model model){
    model.addAttribute("product", new Product());
    return "productform";
  }

  @RequestMapping(value = "product", method = RequestMethod.POST)
  public String saveOrUpdateProduct(Product product){
    Product savedProduct = productService.saveOrUpdateProduct(product);
    return "redirect:/product/" + savedProduct.getId();
  }

  @RequestMapping(value="product/{id}/edit")
  public String editProduct(Model model, @PathVariable Integer id){
    model.addAttribute("product", productService.getProductById(id));
    return "productform";
  }

  @RequestMapping("/product/{id}/delete")
  public String deleteProduct(@PathVariable Integer id){
    productService.removeProductById(id);
    return "redirect:/products";
  }
}