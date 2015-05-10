/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.j.frontend.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author jonatan
 */
@RestController
public class DemoController {
  
  @RequestMapping("/hello")
  public String hello() {
    return "hi there";
  }
}
