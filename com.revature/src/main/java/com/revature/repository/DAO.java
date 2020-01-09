package com.revature.repository;

import java.sql.PreparedStatement;
import com.revature.model.Reimbursement;

public interface DAO {
  
  public void establishConnection();
  
  public int verifyLogin(String user, String pass);//returns a userID if found
  
  public String getEmployeeInformation(String field, int id);
  
  public void addReimbursement(Reimbursement newEntry);
  
  public boolean resolveReimbursement(String status, int id);
  
  public boolean testInDatabase(String primaryKey);
  
  public PreparedStatement stageAndCommitStatement(String statement, Object... objects );
}
