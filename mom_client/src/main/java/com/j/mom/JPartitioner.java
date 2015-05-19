/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.j.mom;

import kafka.producer.Partitioner;
import kafka.utils.VerifiableProperties;

/**
 *
 * @author jonatan
 */
public class JPartitioner implements Partitioner {
  
  public JPartitioner(VerifiableProperties properties) {
    
  }
  
  @Override
  public int partition(Object key, int numberOfPartitions) {
    return 1;
  }
}
