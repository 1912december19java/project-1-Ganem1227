package com.revature.model;

import java.util.ArrayList;

public class ReimbursementContainer {
  private ArrayList<Reimbursement> reimburse = new ArrayList<Reimbursement>();
  
  public ReimbursementContainer() {
    super();
  }
  
  public ReimbursementContainer(ArrayList<Reimbursement> newList) {
    reimburse = newList;
  }

  public ArrayList<Reimbursement> getReimburse() {
    return reimburse;
  }

  public void setReimburse(ArrayList<Reimbursement> reimburse) {
    this.reimburse = reimburse;
  }
  
}
