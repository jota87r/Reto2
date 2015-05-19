/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.j.mom;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 *
 * @author jonatan
 */
public class MomInitializer {
  
  private static MomInitializer instance;
  
  private ConsumerGroup consumerGroup;
  
  public final static synchronized MomInitializer instance() {
    return instance == null ? instance = new MomInitializer() : instance;
  }
  
  private MomInitializer() {
  }
  
  public void start() {
    try {
      (consumerGroup = new ConsumerGroup(loadConsumerProperties())).start();
      Topics.instance().setJProducer(new JProducer(loadProducerProperties()));
    } catch(IOException e) {
      throw new RuntimeException(e);
    }
  }
  
  private Properties loadConsumerProperties() throws IOException {
    return loadPropertiesFile("consumer.properties");
  }
  
  private Properties loadProducerProperties() throws IOException {
    return loadPropertiesFile("producer.properties");
  }
  
  private Properties loadPropertiesFile(String path) throws IOException {
    Properties properties = new Properties();
    InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream(path);
    if (inputStream == null)
      throw new FileNotFoundException("There is no configuration file for mom system!");
    else {
      properties.load(inputStream);
      return properties;
    }
  }
  
  int ammountOfTopics() {
    return consumerGroup.ammountOfTopics();
  }
}
