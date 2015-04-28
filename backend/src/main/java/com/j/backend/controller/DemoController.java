/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.j.backend.controller;

import com.j.backend.domain.Message;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author jonatan
 */
@RestController
public class DemoController {

  @RequestMapping(value = "/messages", method = RequestMethod.PUT)
  public void putMessage(@RequestBody Message message) {
    
  }
  
  @RequestMapping(value = "/messages", method = RequestMethod.GET)
  @ResponseBody
  public Message getMessage(@RequestParam("queue") String queue) {
    return new Message("dummyQ", "dummyMessage");
  }
  
//  @RequestMapping("/messages")
//  public Message getMessage() {
//    return new Message("dummyQ", "dummyMessage");
//  }
}
