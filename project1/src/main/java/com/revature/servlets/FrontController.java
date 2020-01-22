package com.revature.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.revature.model.Employee;
import com.revature.model.Reimbursement;
import com.revature.model.ReimbursementContainer;
import com.revature.service.Service;

@WebServlet(name = "FrontController", urlPatterns = {"/api/*"})
public class FrontController extends HttpServlet{
  
  /**
   * 
   */
  private static final long serialVersionUID = 8089478858331297096L;
  private Service service;
  private ObjectMapper om;
  
  @Override
  public void init() throws ServletException {
    this.service = new Service();
    this.om = new ObjectMapper();
    super.init();
  }

  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp)
      throws ServletException, IOException {
    
    System.out.println("URI:" + req.getRequestURI());
    String[] tokens = req.getRequestURI().split("/");
    
    if(tokens[3].equals("reimbursements")){
      ReimbursementContainer packet = new ReimbursementContainer(service.getAllReimbursements());
      System.out.println("Reached Servlet reimbursements");
      
      resp.getWriter().write(om.writeValueAsString(packet));
      
      if (tokens.length > 4) {
        //get a specific set of reimbursements
        //get by some id or parameter from your DAO
        //check if null with if (reimburse == null) resp.sendError(404);
      } else {
        //get all otherwise
      }
    }else if(tokens[3].equals("employeeInfo")) {
      System.out.println("Reached Servlet employeeInfo");
      //System.out.println(om.writeValueAsString(service.getEmployee()));
      resp.getWriter().write(om.writeValueAsString(service.getEmployee()));
    }else if(tokens[3].equals("logout")) {
      logout(req, resp);
    }else if(tokens[3].equals("ManagerReimb")){
      System.out.println("Reached Servlet ManagerReimb");
      ReimbursementContainer packet = new ReimbursementContainer(service.getAllManagerReimbursement());
      resp.getWriter().write(om.writeValueAsString(packet));
    }else if(tokens[3].equals("employeeList")) {
      System.out.println("Reached Servlet employeeList");
      ArrayList<Employee> packet = service.getEmployeesOfManager();
      resp.getWriter().write(om.writeValueAsString(packet));
    }
  }
  
  @Override
  protected void doPost(HttpServletRequest req, HttpServletResponse resp)
      throws ServletException, IOException {
    System.out.println("URI:" + req.getRequestURI());
    String[] tokens = req.getRequestURI().split("/");
    
    switch(tokens[3]) {
      case "login" : 
        login(req, resp);
        break;
      case "editInfo" :
        System.out.println("Reached editInfo");
        Employee temp = om.readValue(req.getReader(), Employee.class);
        service.updateEmployeeInfo(temp);
        break;
      case "newReimb" :
        System.out.println("Reached newReimb");
        Reimbursement newReimb = om.readValue(req.getReader(), Reimbursement.class);
        //System.out.println(newReimb.getReceipt());
        service.addNewReimburse(newReimb);
        break;
      case "updateReimb" :
        System.out.println("reached updateReimb");
        ReimbursementContainer newEntry = om.readValue(req.getReader(), ReimbursementContainer.class);
        System.out.println(newEntry.getReimburse());
        service.updateReimbSet(newEntry);
        break;
    }
  }
  
  @SuppressWarnings("finally")
  private void login(HttpServletRequest req, HttpServletResponse resp)
      throws ServletException, IOException{
    System.out.println("Reached POST servlet.");
    
    Employee employee = om.readValue(req.getReader(), Employee.class);
    
    String user = employee.getUsername();
    String pass = employee.getPassword();
    
    PrintWriter out = resp.getWriter();
    boolean exists = service.validateAccount(user, pass);
    
    try {
    
      if (exists) {
        service.setEmployee(user,pass);
        service.populateEmployee();
        System.out.println(req.getContextPath());
        resp.sendRedirect(req.getContextPath() + "/EmployeeInfo.html");
      }else {
        resp.sendRedirect(req.getContextPath() + "/index.html");
      }
    }finally {
      out.close();
      return;
    }
  }


  private void logout(HttpServletRequest req, HttpServletResponse resp) 
      throws ServletException, IOException{
    service.resetAllContainers();
  }
}
