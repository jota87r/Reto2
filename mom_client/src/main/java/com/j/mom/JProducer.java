/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.j.mom;

import java.util.Properties;
import kafka.javaapi.producer.Producer;
import kafka.producer.KeyedMessage;
import kafka.producer.ProducerConfig;

/**
 *
 * @author jonatan
 */
public class JProducer implements AutoCloseable {
  
  private final Producer<String, String> producer;
  
  JProducer(Properties properties) {
    producer = new Producer<>(new ProducerConfig(properties));
  }
  
  public void send(String topic, String message) {
//    producer.send(new KeyedMessage<>(topic, topic, message));
    producer.send(new KeyedMessage(topic, message));
  }

  @Override
  public void close() throws Exception {
    producer.close();
  }
}
