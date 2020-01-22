package com.revature.service;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.auth.profile.ProfileCredentialsProvider;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.GetObjectRequest;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.S3Object;
import com.revature.model.Employee;
import com.revature.model.Reimbursement;
import com.revature.model.ReimbursementContainer;
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
    employee.setPhoto(temp.getPhoto());
  }
  
  public void resetAllContainers() {
    employee.clearAllFields();
  }
  
  public String getManagerName() {
    return dao.getManagerOfEmployee(employee.getManagerId());
  }
  
  public void updateEmployeeInfo(Employee newEmployee){
    //call to DAO
    System.out.println("Update employee info: entered Service Layer");
    employee.setFirstName(newEmployee.getFirstName());
    employee.setLastName(employee.getLastName());
    employee.setEmail(newEmployee.getEmail());
    employee.setJob(newEmployee.getJob());
    dao.updateEmployeeInfo(employee);
  }
  
  private String saveToBucket(File file) {
    
    Regions clientRegion = Regions.US_EAST_1 ;
    String bucketName = System.getenv("AWS_BUCKET");
    String key = file.getName();
    
    try {
      AmazonS3 s3Client = AmazonS3ClientBuilder.standard()
          .withRegion(clientRegion)
          .withCredentials(new ProfileCredentialsProvider())
          .build();
      
      PutObjectRequest request = new PutObjectRequest(bucketName, key, file);
      ObjectMetadata metadata = new ObjectMetadata();
      metadata.setContentType("image/jpeg image/png");
      metadata.addUserMetadata("x-amz-meta-title", "someTitle");
      request.setMetadata(metadata);
      s3Client.putObject(request);
      
      return key;
    }catch(AmazonServiceException e) {
      e.printStackTrace();
    }
    
    return null;
  }
  
  private File getFileFromBucket(String bucketKey) {
    
    Regions clientRegion = Regions.US_EAST_1 ;
    String bucketName = System.getenv("AWS_BUCKET");
    String key = bucketKey;
    
    S3Object fullObject = null;
    InputStream in;
    File temp;
    try {
      AmazonS3 s3Client = AmazonS3ClientBuilder.standard()
          .withRegion(clientRegion)
          .withCredentials(new ProfileCredentialsProvider())
          .build();
      
      
      fullObject = s3Client.getObject(new GetObjectRequest(bucketName,key));
      in = fullObject.getObjectContent();
      
      temp = File.createTempFile("newFile", "");
      Files.copy(in,temp.toPath(),StandardCopyOption.REPLACE_EXISTING);
      
      return temp;
      
    }catch(AmazonServiceException e) {
      e.printStackTrace();
    }catch(IOException e) {
      e.printStackTrace();
    }
    return null;

  }
  
  public void addNewReimburse(Reimbursement reimb) {
    reimb.setManager_id(employee.getManagerId());
    reimb.setOwner_id(employee.getEmployeeId());
    dao.addReimbursement(reimb);
  }
  
  public ArrayList<Reimbursement> getAllManagerReimbursement(){
    return dao.getAllReimbursementsManager(employee);
  }
  
  public void updateReimbSet(ReimbursementContainer reimbSet) {
    for(int i = 0; i < reimbSet.getReimburse().size(); i++) {
      dao.resolveReimbursement(reimbSet.getReimburse().get(i).getStatus(),reimbSet.getReimburse().get(i).getReimbId());
    }
  }
  
  public ArrayList<Employee> getEmployeesOfManager(){
    return dao.getEmployeesOfManager(employee);
  }
}
