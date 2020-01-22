package com.revature.repository;

import java.sql.PreparedStatement;
import java.util.ArrayList;
import com.revature.model.Employee;
import com.revature.model.Reimbursement;

public interface DAO {
  
  public void establishConnection();
  
  public int verifyLogin(String user, String pass);//returns a userID if found
  
  public String getEmployeeInformation(String field, int id);
  
  public void addReimbursement(Reimbursement newEntry);
  
  public boolean resolveReimbursement(String status, int id);
  
  public boolean testInDatabase(String primaryKey);
  
  public PreparedStatement stageAndCommitStatement(String statement, Object... objects );
  
  public Employee getAllEmployeeInformation(int id);
  
  public String getManagerOfEmployee(int id);
  
  public ArrayList<Reimbursement> getAllReimbursements(Employee employee);
  
  public void updateEmployeeInfo(Employee newEmployee);
  
  public ArrayList<Employee> getEmployeesOfManager(Employee manager);
  
}
