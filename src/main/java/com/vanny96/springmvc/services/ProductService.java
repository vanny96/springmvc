package com.vanny96.springmvc.services;

import java.util.List;

import com.vanny96.springmvc.domain.Product;

public interface ProductService {
  List<Product> listAllProducts();
}