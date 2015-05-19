/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.j.backend.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 *
 * @author jonatan
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Message {
  private String queue;
  private String text;
}
