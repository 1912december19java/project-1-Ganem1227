package com.revature.servlets;

import java.io.IOException;
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
    System.out.println("Reached GET");
    System.out.println(req);
    System.out.println("Reached GET with " + req.getMethod() + " and " + req.getRequestURI());
    System.out.println("Reached GET with " + req.getParameterMap().keySet());
    
    System.out.println("Username: " + req.getParameter("username") + " | password: " + req.getParameter("password"));
    
    Controller.service.validateAccount(req.getParameter("username"), req.getParameter("password"));
  }
  
  @Override
  protected void doPost(HttpServletRequest req, HttpServletResponse resp)
      throws ServletException, IOException {
    System.out.println("Reached POST servlet.");
    Employee employee = om.readValue(req.getReader(), Employee.class);
    Controller.service.setEmployee(employee);
    System.out.println("Received: " + employee);
  }
}
