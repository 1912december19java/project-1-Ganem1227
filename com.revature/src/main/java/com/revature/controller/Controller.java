package com.revature.controller;

import java.util.Scanner;
import com.revature.service.Service;

public class Controller {
  private static Scanner in = new Scanner(System.in);
  private Service service;
  private boolean finish;
  
  public Controller() {
    super();
    service = new Service();
    finish = false;
  }
  
  public void run() {
    while(!finish) {
      
    }
  }
  
  public void close() {
    finish = false;
  }
  
  public void testGenericSql() {
    
  }
}
