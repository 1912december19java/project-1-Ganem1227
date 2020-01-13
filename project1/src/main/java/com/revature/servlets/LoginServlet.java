package com.revature.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.revature.controller.Controller;
import com.revature.model.Employee;

public class LoginServlet extends HttpServlet{

  /**
   * 
   */
  private static final long serialVersionUID = 5901784448217028279L;
  private static ObjectMapper om = new ObjectMapper();
  
  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp)
      throws ServletException, IOException {
  }
  
  @Override
  protected void doPost(HttpServletRequest req, HttpServletResponse resp)
      throws ServletException, IOException {
    System.out.println("Reached POST servlet.");
    
    System.out.println(req.toString());
    
    Employee employee = om.readValue(req.getReader(), Employee.class);
    
    String user = employee.getUsername();
    String pass = employee.getPassword();
    
    System.out.println("SERVLET: username:" + user);
    System.out.println("SERVLET: password:" + pass);
    
    PrintWriter out = resp.getWriter();
    boolean exists = Controller.service.validateAccount(user, pass);
    
    try {
    
      if (exists) {
        Controller.service.setEmployee(user,pass);
        System.out.println("Logged in!");
        resp.sendRedirect("http://www.google.com");
        System.out.println("Redirect Sent!");
        return;
      }else {
        System.out.println("Log in failed.");
        resp.sendRedirect("/project1/index.html");
        return;
      }
    }finally {
      out.close();
    }
    
  }
}
