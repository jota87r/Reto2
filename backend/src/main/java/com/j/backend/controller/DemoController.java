/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.j.backend.controller;

import com.j.backend.domain.Message;
import com.j.mom.Topics;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ConcurrentLinkedQueue;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author jonatan
 */
@RestController
public class DemoController {

  @RequestMapping(value = "/topics", method = RequestMethod.GET)
  @ResponseBody
  public Collection<String> getTopics() {
    return Topics.instance().getTopics();
  }
  
  @RequestMapping(value = "/topics/{topic}/messages/head", method = RequestMethod.GET)
  @ResponseBody
  public Message getMessage(@PathVariable("topic") String topic) {
    ConcurrentLinkedQueue<String> messages = Topics.instance().getMessages(topic);
    
    if (messages == null) throw new RuntimeException("Queue does not exists!");
    
    return new Message(topic, messages.peek());
  }
  
  @RequestMapping(value = "/topics/{topic}", method = RequestMethod.GET)
  @ResponseBody
  public List<Message> getAllMessage(@PathVariable("topic") String topic) {
    ConcurrentLinkedQueue<String> messages = Topics.instance().getMessages(topic);
    
    if (messages == null) throw new RuntimeException("Queue does not exists!");
    
    List<Message> results = new LinkedList<>();
    messages.stream().forEach((message) -> {results.add(new Message(topic, message));});
    return results;
  }
  
  @RequestMapping(value = "/messages", method = RequestMethod.PUT)
  @ResponseBody
  public void putMessage(@RequestBody Message message) {
    Topics.instance().sendMessage(message.getQueue(), message.getText());
  }
  
}
