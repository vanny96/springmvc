package com.vanny96.springmvc.controllers;

import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.junit.Before;
import org.junit.Test;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


public class IndexControllerTest{
  private MockMvc mockMvc;
  private IndexController indexController;

  @Before
  public void setup(){
    indexController = new IndexController();
    mockMvc = MockMvcBuilders.standaloneSetup(indexController).build();
  }

  @Test
  public void TestIndex() throws Exception {
    mockMvc.perform(get("/"))
            .andExpect(status().isOk())
            .andExpect(view().name("index"));
  }
}