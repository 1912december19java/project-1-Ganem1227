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
import com.revature.model.Employee;
import com.revature.model.Reimbursement;
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
      //ArrayList<String> packet = new ArrayList<String>();
      ArrayList<Reimbursement> content = service.getAllReimbursements();      //Get all comics example, could be useful for reimbursements
      System.out.println("Reached Servlet reimbursements");
      /*
      for (int i = 0; i < content.size(); i++) {
        packet.add(om.writeValueAsString(content.get(i)));
      }
      */
      
      //System.out.println(packet);
      
      
      
      req.setAttribute("mylist", content);

      req.getRequestDispatcher("Reimburse.html").forward(req, resp);
      resp.sendRedirect("Reimburse.html");
      
      
      
      //resp.getWriter().write(om.writeValueAsString(content));
      
      if (tokens.length > 4) {
        //get a specific set of reimbursements
        //get by some id or parameter from your DAO
        //check if null with if (reimburse == null) resp.sendError(404);
      } else {
        //get all otherwise
      }
      
      resp.getWriter().write(om.writeValueAsString("")); //final step but really you should put it
      //in one of the if blocks.
    }else if(tokens[3].equals("employeeInfo")) {
      System.out.println("Reached Servlet employeeInfo");
      //System.out.println(om.writeValueAsString(service.getEmployee()));
      resp.getWriter().write(om.writeValueAsString(service.getEmployee()));
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
        resp.sendRedirect(req.getContextPath() + "/EmployeeInfo.html");
      }else {
        resp.sendRedirect(req.getContextPath() + "/index.html");
      }
    }finally {
      out.close();
      return;
    }
  }

}
