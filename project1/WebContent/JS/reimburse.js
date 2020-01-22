'use strict'

let table = document.getElementById("reimburse-content");
let add = document.getElementById("addnew");

let pFilter = document.getElementById("pendingFilter");
let rFilter = document.getElementById("resolvedFilter");
let filterButton = document.getElementById("filterButton");

filterButton.addEventListener('click',(event)=>{
  event.preventDefault();
  wipeReimbList();
  loadReimbursements();
})

function wipeReimbList(){
  while(table.children[0]){
    table.children[0].remove();
  }
}

let myUri = 'http://localhost:8080/project1/api/reimbursements';

function populateTable(){
/*
    for(let i = 0; i < 10; i++){
        let newRow = document.createElement("tr");
        newRow.innerHTML = "<td>1/1/1111</td><td>$1.00</td><td>No reason</td><td>Pending</td>";
        table.appendChild(newRow);
    }
    */

    retrieveAllReimbursements();

}

async function retrieveAllReimbursements(){
    let response = await fetch(myUri, {method : 'GET'});
    console.log(response);
    let reimburseList = await response.text();
    
}

let xmlhttp = new XMLHttpRequest();
/*
xmlhttp.onreadystatechange = function() {
  if (xmlhttp.readyState == xmlhttp.DONE && xmlhttp.status == 200) {
    let resp = JSON.parse(xmlhttp.response);
    //resp = resp.json();
    console.log(resp);
  }
};
*/
xmlhttp.open("GET", myUri);
xmlhttp.responseType = 'json';
xmlhttp.send();
let reimbList;

xmlhttp.onload = function () {
  let jsonObj = xmlhttp.response;
  //jsonObj = jsonObj.substr(0, jsonObj.length - 2);
  //jsonObj = JSON.parse(jsonObj);
  reimbList = jsonObj.reimburse;

  loadReimbursements();

};

function loadReimbursements(){
  for (let i of reimbList){

    if((pFilter.checked && rFilter.checked)||(!pFilter.checked && !rFilter.checked)){
      let newRow = document.createElement("tr");
      newRow.innerHTML = `<td>${i.timestamp}</td><td>$${i.value}</td><td>${i.notes}</td><td><img src="${i.receipt}" style="width : 10%"></td><td>${i.status}</td>`;
      table.appendChild(newRow);
    }else{
      if(pFilter.checked && i.status==='Pending'){
        let newRow = document.createElement("tr");
        newRow.innerHTML = `<td>${i.timestamp}</td><td>$${i.value}</td><td>${i.notes}</td><td><img src="${i.receipt}" style="width : 10%"></td><td>${i.status}</td>`;
        table.appendChild(newRow);
      }else if(rFilter.checked && (i.status==="Rejected" || i.status==="Approved")){
        let newRow = document.createElement("tr");
        newRow.innerHTML = `<td>${i.timestamp}</td><td>$${i.value}</td><td>${i.notes}</td><td><img src="${i.receipt}" style="width : 10%"></td><td>${i.status}</td>`;
        table.appendChild(newRow);
      }
    }
    
  }
}

add.addEventListener('click', (event)=>{
  event.preventDefault();
  window.location.href = "/project1/NewReimbursement.html";
})

//populateTable();