/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.j.mom;

import java.io.IOException;
import java.io.InputStream;
import java.util.LinkedList;
import java.util.List;
import java.util.Properties;
import kafka.javaapi.TopicMetadataRequest;
import kafka.javaapi.consumer.SimpleConsumer;

/**
 *
 * @author jonatan
 */
public class JTopic {
  
  private static JTopic instance;
  
  private final SimpleConsumer consumer;
  private final TopicMetadataRequest request;
  
  public synchronized static JTopic instance() {
    return instance == null ? instance = new JTopic() : instance;
  }
  
  List<String> topics() {
    List<String> topics = new LinkedList<>();
    consumer.send(request).topicsMetadata().stream().forEach((topic) -> {topics.add(topic.topic());});
    return topics;
  }
  
  private JTopic() {
    Properties properties = loadPropertiesFile("client.properties");
    consumer = new SimpleConsumer(properties.getProperty("host"), Integer.parseInt(properties.getProperty("port")), 
            Integer.parseInt(properties.getProperty("soTimeout")), Integer.parseInt(properties.getProperty("bufferSize")), 
            properties.getProperty("clientId"));
    request = new TopicMetadataRequest(new LinkedList<>());
  }
  
  private Properties loadPropertiesFile(String path) {
    Properties properties = new Properties();
    InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream(path);
    if (inputStream == null)
      throw new RuntimeException("There is no configuration file for mom system admin!");
    else {
      try {
        properties.load(inputStream);
      } catch (IOException ex) {
        throw new RuntimeException("Can not load admin properties file");
      }
      return properties;
    }
  }
}
