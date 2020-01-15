package com.revature.service;

import java.util.ArrayList;
import com.revature.model.Employee;
import com.revature.model.Reimbursement;
import com.revature.repository.DAOPostgres;

public class Service {

  private Reimbursement reimb; 
  private Employee employee;
  private DAOPostgres dao;
  
  
  public Service() {
    reimb = new Reimbursement();
    dao = new DAOPostgres();
    employee = new Employee();
  }
  
  //adds new RB to DB
  public void saveNewRB() {
    reimb.setManager_id(employee.getManagerId());
    reimb.setOwner_id(employee.getEmployeeId());
    dao.addReimbursement(reimb);
  }
  
  public ArrayList<Reimbursement> getAllReimbursements() {
    return dao.getAllReimbursements(employee);
  }
  
  //tests if the account is valid, if it's valid it will return the userID
  public boolean validateAccount(String user, String passcode) {
    System.out.println("Service: user = " + user + " | passcode = " + passcode);
    int curr_id = dao.verifyLogin(user, passcode);
    
    if(curr_id != -1) {
      System.out.println("curr_ID = " + curr_id);
      employee.setEmployeeId(curr_id);
      return true;
    }else return false;
  }
  
  //allows managers to resolve RB
  public void resolveRB(String newStatus) {
    reimb.setStatus(newStatus);
  }
  
  //sets the value of RB
  public void addValue(double newValue) {
    reimb.setValue(newValue);
  }
  
  //sets the notes of the RB
  public void addNotes(String newNote) {
    reimb.setNotes(newNote);
  }
  
  //email, may move to a different class
  public void sendEmail() {
    
  }

  public Employee getEmployee() {
    return employee;
  }

  public void setEmployee(Employee employee) {
    this.employee = employee;
  }
  
  public void setEmployee(String user, String pass) {
    employee.setUsername(user);
    employee.setPassword(pass);
  }
  
  public void populateEmployee() {
    Employee temp = dao.getAllEmployeeInformation(employee.getEmployeeId());
    employee.setFirstName(temp.getFirstName());
    employee.setLastName(temp.getLastName());
    employee.setEmail(temp.getEmail());
    employee.setJob(temp.getJob());
    employee.setManagerId(temp.getManagerId());
    employee.setManagerName(getManagerName());
  }
  
  public String getManagerName() {
    return dao.getManagerOfEmployee(employee.getManagerId());
  }
}
