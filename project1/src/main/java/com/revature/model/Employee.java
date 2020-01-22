package com.revature.model;

public class Employee {
  private String firstName;
  private String lastName;
  private String email;
  private int employeeId;
  private String job;
  private int managerId;
  private String managerName;
  private String username;
  private String password;
  private String photo;
  
  public Employee() {
    super();
    firstName = "";
    lastName = "";
    email = "";
    job = "";
    employeeId = -1;
    managerId = -1;
    managerName = "";
  }

  public void clearAllFields() {
    firstName = "";
    lastName = "";
    email = "";
    employeeId = -1;
    job = "";
    managerId = -1;
    managerName = "";
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

  public String getManagerName() {
    return managerName;
  }

  public void setManagerName(String managerName) {
    this.managerName = managerName;
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  @Override
  public String toString() {
    return "Employee [firstName=" + firstName + ", lastName=" + lastName + ", email=" + email
        + ", employeeId=" + employeeId + ", job=" + job + ", managerId=" + managerId
        + ", managerName=" + managerName + ", username=" + username + ", password=" + password
        + "]";
  }

  public String getPhoto() {
    return photo;
  }

  public void setPhoto(String photo) {
    this.photo = photo;
  }
  
}
