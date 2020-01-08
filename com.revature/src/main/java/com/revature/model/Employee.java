package com.revature.model;

public class Employee {
  private String firstName;
  private String lastName;
  private String email;
  private int employeeId;
  private String job;
  private int managerId;
  
  public Employee() {
    super();
    firstName = "";
    lastName = "";
    email = "";
    job = "";
  }

  public void clearAllFields() {
    firstName = "";
    lastName = "";
    email = "";
    employeeId = -1;
    job = "";
    managerId = -1;
    
  }
  
  public String getFirstName() {
    return firstName;
  }

  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }

  public String getLastName() {
    return lastName;
  }

  public void setLastName(String lastName) {
    this.lastName = lastName;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public int getEmployeeId() {
    return employeeId;
  }

  public void setEmployeeId(int employeeId) {
    this.employeeId = employeeId;
  }

  public String getJob() {
    return job;
  }

  public void setJob(String job) {
    this.job = job;
  }

  public int getManagerId() {
    return managerId;
  }

  public void setManagerId(int managerId) {
    this.managerId = managerId;
  }
  
  
}
