package com.revature.repository;

import java.awt.List;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import com.revature.model.Employee;
import com.revature.model.Reimbursement;

public class DAOPostgres implements DAO{
  static Connection conn;
  
  static {
    
  }
  
  public DAOPostgres() {
    super();
    establishConnection();
  }
  
  public int verifyLogin(String user, String pass) {
    
    PreparedStatement stmt;
    ResultSet rs;
    System.out.println("DAO Postgres: username: " + user + " | password: " + pass);
    
    try {
      stmt = conn.prepareStatement("SELECT employee_id FROM accounts WHERE username = ? AND passcode = ?");
      stmt.setString(1, user);
      stmt.setString(2, pass);
      stmt.execute();
      
      rs = stmt.getResultSet();
      
      if(rs.next()) {
        //stmt.close();
        System.out.println("DAO Postgres : " + rs.getInt("employee_id"));
        return rs.getInt("employee_id");
      } else return -1;
    }catch(SQLException e) {
      return -1;
    }
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
        stmt.close();
        return rs.getString(field);
      }
      stmt.close();
    
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
      stmt.close();
      
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
        stmt.close();
        return true;
      }else { 
        stmt.close();
        return false;
      }
      
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
        stmt.close();
        return true;
      }else {
        stmt.close();
        return false;
      }
      
    }catch(SQLException e) {
      e.printStackTrace();
    } finally {
      
    }
    
    return false;
  }

  public void establishConnection() {
    try {
      Class.forName("org.postgresql.Driver");
  } catch (ClassNotFoundException e1) {
      e1.printStackTrace();
  }
    
    try {
      conn = DriverManager.getConnection(
          System.getenv("AWS_URL"), 
          System.getenv("AWS_USERNAME"), 
          System.getenv("AWS_PASSWORD")
          );
      System.out.println("CONNECTED");
    }catch(SQLException e) {
      e.printStackTrace();
      System.out.println("CONNECTION FAILED");
    }
  }

  public PreparedStatement stageAndCommitStatement(String statement, Object... objects) {
    PreparedStatement stmt;
    
    try {
      stmt = conn.prepareStatement(statement);
      for(int i = 0; i < objects.length; i++) {
        
        if(objects[i].getClass().getName().equals("Integer")) {
          stmt.setInt(i+1, (Integer)objects[i]);
        }else if(objects[i].getClass().getName().equals("String")) {
          stmt.setString(i+1, (String)objects[i]);
        }else if(objects[i].getClass().getName().equals("Double")) {
          stmt.setDouble(i+1, (Double)objects[i]);
        }
      }
        stmt.execute();
        return stmt;
      
    }catch(SQLException e) {
      e.printStackTrace();
    }
    return null;
  }
  
  public void closeAllConnections() {
    try {
      conn.close();
    }catch(SQLException e) {
      e.printStackTrace();
    }
  }

  public Employee getAllEmployeeInformation(int id) {
    PreparedStatement stmt;
    ResultSet rs;
    Employee employee = new Employee();
    
    try {
      stmt = conn.prepareStatement("SELECT * FROM employee WHERE id = ?");
      stmt.setInt(1, id);
      stmt.execute();
      
      rs = stmt.getResultSet();
      
      if(rs.next()) {
        employee.setFirstName(rs.getString("first_name"));
        employee.setLastName(rs.getString("last_name"));
        employee.setEmail(rs.getString("email"));
        employee.setJob(rs.getString("job_title"));
        employee.setManagerId(rs.getInt("manager_id"));
        return employee;
      }else {
        return null;
      }
      
    }catch(SQLException e) {
      e.printStackTrace();
      return null;
    }
  }
  
  public String getManagerOfEmployee(int id) {
    PreparedStatement stmt;
    ResultSet rs;
    
    try {
      stmt = conn.prepareStatement("SELECT first_name, last_name FROM employee WHERE id = ?");
      stmt.setInt(1, id);
      stmt.execute();
      
      rs = stmt.getResultSet();
      
      if(rs.next()) {
        System.out.println(rs.getString("first_name") + " " + rs.getString("last_name"));
        return rs.getString("first_name") + " " + rs.getString("last_name");
      }else {
        return "N/A";
      }
    }catch(SQLException e) {
      e.printStackTrace();
      return "N/A";
    }
  }
  
  public ArrayList<Reimbursement> getAllReimbursements(Employee employee){
    PreparedStatement stmt;
    ResultSet rs;
    ArrayList<Reimbursement> result = new ArrayList<Reimbursement>();
    
    try {
      stmt = conn.prepareStatement("SELECT * FROM reimbursements WHERE employee_id = ?");
      stmt.setInt(1, employee.getEmployeeId());
      
      stmt.execute();
      rs = stmt.getResultSet();
      
      while(rs.next()) {
        Reimbursement newReimburse = new Reimbursement();
        newReimburse.setManager_id(employee.getManagerId());
        newReimburse.setNotes(rs.getString("Notes"));
        newReimburse.setOwner_id(rs.getInt("employee_id"));
        newReimburse.setStatus(rs.getString("status"));
        newReimburse.setValue(rs.getDouble("value"));
        newReimburse.setTimestamp(rs.getString("date_submitted"));
        result.add(newReimburse);
      }
      
      return result;
      
    }catch(SQLException e) {
      e.printStackTrace();
      return result;
    }
  }
  
}
