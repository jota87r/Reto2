/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.j.mom;

import java.util.logging.Level;
import kafka.consumer.ConsumerIterator;
import kafka.consumer.KafkaStream;
import lombok.extern.java.Log;

/**
 *
 * @author jonatan
 */
@Log
public class Consumer implements Runnable {

  private String topic;
  private final KafkaStream stream;
  
  Consumer(KafkaStream stream, String topic) {
    this.stream = stream;
    this.topic = topic;
  }
  
  @Override
  public void run() {
    for (ConsumerIterator<byte[], byte[]> it = stream.iterator(); it.hasNext();) {
      Topics.instance().setMessage(topic, new String(it.next().message()));
    }
  }
}