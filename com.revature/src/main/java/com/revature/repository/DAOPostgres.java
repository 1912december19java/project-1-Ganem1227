package com.revature.repository;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import com.revature.model.Reimbursement;

public class DAOPostgres implements DAO{
  Connection conn;
  
  public DAOPostgres() {
    super();
    establishConnection();
  }
  
  public int verifyLogin(String user, String pass) {
    
    PreparedStatement stmt;
    ResultSet rs;
    
    try {
      stmt = conn.prepareStatement("SELECT id FROM account WHERE username = ?, passcode = ?");
      stmt.setString(1, user);
      stmt.setString(2, pass);
      stmt.execute();
      
      rs = stmt.getResultSet();
      
      if(rs.next()) {
        return rs.getInt("id");
      }
    }catch(SQLException e) {
      
    }
    
    return -1;
  }

  public String getEmployeeInformation(String field, int id) {
    
    PreparedStatement stmt;
    ResultSet rs;
    
    try {
      stmt = conn.prepareStatement("SELECT ? FROM employees WHERE id = ?");
      stmt.setString(1, field);
      stmt.setInt(2, id);
      
      stmt.execute();
      rs = stmt.getResultSet();
      
      if(rs.next()) {
        return rs.getString(field);
      }
    
      return null;
    } catch(SQLException e) {
      return null;
    }
  }

  public void addReimbursement(Reimbursement newEntry) {
    PreparedStatement stmt;
    
    try {
      stmt = conn.prepareStatement("INSERT INTO reimbursements (employee_id,value,notes,manager_id,status) VALUES (?,?,?,?,?)");
      stmt.setInt(1, newEntry.getOwner_id());
      stmt.setString(2, newEntry.getNotes());
      stmt.setInt(3, newEntry.getManager_id());
      stmt.setString(4, newEntry.getStatus());
      
      stmt.execute();
      
    }catch(SQLException e) {
      e.printStackTrace();
    }
  }

  public boolean resolveReimbursement(String status, int id) {
    PreparedStatement stmt;
    ResultSet rs;
    
    try {
      stmt = conn.prepareStatement("UPDATE reimbursement SET status = ? WHERE employee_id = ?");
      stmt.setString(1, status);
      stmt.setInt(2, id);
      stmt.execute();
      
      rs = stmt.getResultSet();
      
      if(rs.next()) {
        return true;
      }else return false;
      
    }catch(SQLException e) {
      
    }
    return false;
  }

  public boolean testInDatabase(String primaryKey) {
    PreparedStatement stmt;
    ResultSet rs;
    
    try {
      stmt = conn.prepareStatement("SELECT count(id) FROM employees WHERE username = ?");
      stmt.setString(1, primaryKey);
      stmt.execute();
      
      rs = stmt.getResultSet();
      
      if(rs.next()) {
        return true;
      }else return false;
      
      
      
    }catch(SQLException e) {
      e.printStackTrace();
    }
    return false;
  }

  public void establishConnection() {
    try {
      conn = DriverManager.getConnection(
          System.getenv("AWS_URL"), 
          System.getenv("AWS_USERNAME"), 
          System.getenv("AWS_PASSWORD")
          );
      System.out.println("CONNECTED");
    }catch(SQLException e) {
      //e.printStackTrace();
      System.out.println("CONNECTION FAILED");
    }
  }

}
