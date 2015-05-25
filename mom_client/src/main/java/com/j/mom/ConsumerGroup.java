/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.j.mom;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import kafka.consumer.ConsumerConfig;
import kafka.consumer.KafkaStream;
import kafka.javaapi.consumer.ConsumerConnector;
import lombok.extern.java.Log;

/**
 *
 * @author jonatan
 */
@Log
public class ConsumerGroup {
  
  private final ConsumerConnector connector;
  private ExecutorService executor;
  
  private final int ammountOfThreadsPerTopic;
  
  ConsumerGroup(Properties properties) {
    ammountOfThreadsPerTopic = Integer.parseInt(properties.getProperty("topics.threads"));
    properties.remove("topics.threads");
    
    connector = kafka.consumer.Consumer.createJavaConsumerConnector(new ConsumerConfig(properties));
  }
  
  void start() {
    List<String> topics = JTopic.instance().topics();
    
    Map<String, Integer> threadsPerTopic = new HashMap<>(topics.size());
    
    topics.stream().forEach((topic) -> {
      threadsPerTopic.put(topic, ammountOfThreadsPerTopic);
    });
    
    Map<String, List<KafkaStream<byte[], byte[]>>> messageStreams = connector.createMessageStreams(threadsPerTopic);
    executor = Executors.newFixedThreadPool(ammountOfThreadsPerTopic * topics.size());
    
    messageStreams.entrySet().stream().forEach((messageStream) -> {
      messageStream.getValue().stream().forEach((stream) -> {executor.submit(new JConsumer(stream, messageStream.getKey()));} );
    });
  }
  
  void stop() {
    if (connector != null) connector.shutdown();
    if (executor != null) executor.shutdown();
    try {
      if (!executor.awaitTermination(5, TimeUnit.SECONDS)) log.log(Level.ALL, "Timeout shutting down thread!");
    } catch (InterruptedException e) {
      log.log(Level.SEVERE, e.getMessage());
    }
  }
}
