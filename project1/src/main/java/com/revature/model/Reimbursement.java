package com.revature.model;

import java.io.File;

public class Reimbursement {
  private String notes;
  private int reimbId;
  private double value;
  private int ownerId;
  private int managerId;
  private String timestamp;
  private String status;
  private String receipt;
  
  public Reimbursement() {
    super();
    notes = "";
    value = 0.0;
    status = "Pending";
    setReimbId(0);
  }
  
  public void clearAllFields() {
    notes = "";
    value = 0.0;
    ownerId = -1;
    managerId = -1;
    timestamp = "";
    status = "Pending";
    setReimbId(0);
  }

  public String getNotes() {
    return notes;
  }

  public void setNotes(String notes) {
    this.notes = notes;
  }

  public double getValue() {
    return value;
  }

  public void setValue(double value) {
    this.value = value;
  }

  public int getOwner_id() {
    return ownerId;
  }

  public void setOwner_id(int owner_id) {
    this.ownerId = owner_id;
  }

  public int getManager_id() {
    return managerId;
  }

  public void setManager_id(int manager_id) {
    this.managerId = manager_id;
  }

  public String getTimestamp() {
    return timestamp;
  }

  public void setTimestamp(String timestamp) {
    this.timestamp = timestamp;
  }

  public String getStatus() {
    return status;
  }

  public void setStatus(String status) {
    this.status = status;
  }

  public String getReceipt() {
    return receipt;
  }

  public void setReceipt(String receipt) {
    this.receipt = receipt;
  }

  public int getReimbId() {
    return reimbId;
  }

  public void setReimbId(int reimbId) {
    this.reimbId = reimbId;
  }
  
  
}
