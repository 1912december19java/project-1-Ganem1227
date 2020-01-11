package com.revature.main;

import com.revature.controller.Controller;
import com.revature.service.Service;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
      Controller controller = new Controller();
    }
    
    public static void testEmail() {
      Service service = new Service();
      service.sendEmail();
    }
}
