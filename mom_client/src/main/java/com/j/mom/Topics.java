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
  private Map<String, ConcurrentLinkedQueue<String>> messagesPerTopic;
  
  private final Object mutex = new Object();
  
  public final static synchronized Topics instance() {
    return instance == null ? instance = new Topics() : instance;
  }
  
  private Topics() {
    messagesPerTopic = new ConcurrentHashMap<>(MomInitializer.instance().ammountOfTopics());
  }
  
  public Collection<String> getTopics() {
    return messagesPerTopic.keySet();
  }
  
  public ConcurrentLinkedQueue<String> getMessages(String topic) {
    return messagesPerTopic.get(topic);
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
