/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.j.mom;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 *
 * @author jonatan
 */
public class Mom {
  
  private static Mom instance;
  private final Map<String, ConcurrentLinkedQueue<String>> messagesPerTopic;
  
  private JProducer producer;
  
  private final Object mutex = new Object();
  
  public final static synchronized Mom instance() {
    return instance == null ? instance = new Mom() : instance;
  }
  
  public Collection<String> getTopics() {
    return JTopic.instance().topics();
  }
  
  public ConcurrentLinkedQueue<String> getMessages(String topic) {
    return messagesPerTopic.get(topic);
  }
  
  public void sendMessage(String topic, String message) {
    if (producer != null) {
      producer.send(topic, message);
    }
  }
  
  private Mom() {
    synchronized (mutex) {
      List<String> topics = JTopic.instance().topics();
      messagesPerTopic = new ConcurrentHashMap<>(topics.size());
      topics.stream().forEach((topic) -> {
        messagesPerTopic.put(topic, new ConcurrentLinkedQueue<>());
      });
    }
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
