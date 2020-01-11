package com.revature.service;

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
  }
  
  //adds new RB to DB
  public void saveNewRB() {
    reimb.setManager_id(employee.getManagerId());
    reimb.setOwner_id(employee.getEmployeeId());
    dao.addReimbursement(reimb);
  }
  
  //tests if the account is valid, if it's valid it will return the userID
  public int validateAccount(String user, String passcode) {
    employee.setEmployeeId(dao.verifyLogin(user, passcode));
    return employee.getEmployeeId();
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
  
  
  
  
}
