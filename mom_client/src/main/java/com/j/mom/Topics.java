/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.j.mom;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 *
 * @author jonatan
 */
public class Topics {
  
  private static Topics instance;
  private final Map<String, ConcurrentLinkedQueue<String>> messagesPerTopic;
  
  private JProducer producer;
  
  private final Object mutex = new Object();
  
  public final static synchronized Topics instance() {
    return instance == null ? instance = new Topics() : instance;
  }
  
  public Collection<String> getTopics() {
    return messagesPerTopic.keySet();
  }
  
  public void createTopic() {
    
  }
  
  public ConcurrentLinkedQueue<String> getMessages(String topic) {
    return messagesPerTopic.get(topic);
  }
  
  public void sendMessage(String topic, String message) {
    if (producer != null) {
      producer.send(topic, message);
    }
  }
  
  private Topics() {
    messagesPerTopic = new ConcurrentHashMap<>(MomInitializer.instance().ammountOfTopics());
  }
  
  void setJProducer(JProducer producer) {
    this.producer = producer;
  }
  
  void setMessage(String topic, String message) {
    synchronized (mutex) {
      ConcurrentLinkedQueue<String> messages = messagesPerTopic.get(topic);

      if (messages == null) {
        messagesPerTopic.put(topic, messages = new ConcurrentLinkedQueue<>());
      }

      messages.add(message);
    }
  }
}
