package com.revature.main;

import com.revature.service.Service;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {

    }
    
    public static void testEmail() {
      Service service = new Service();
      service.sendEmail();
    }
}
