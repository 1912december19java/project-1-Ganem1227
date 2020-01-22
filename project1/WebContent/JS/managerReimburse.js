'use strict'

let table = document.getElementById("reimburse-content");
let resolvedTable = document.getElementById("resolved-content");
let add = document.getElementById("addnew");
let navbar = document.getElementById("default-navbar");
let currManagerSet;
let displayList = [];

let pFilterButton = document.getElementById("pendingApplyFilters");
let rFilterButton = document.getElementById("resolvedApplyFilters");
let pFieldFilters = document.getElementById("pendingFilter");
let rFieldFilters = document.getElementById("resolvedFilter");

let managerUri = 'http://localhost:8080/project1/api/ManagerReimb';

async function retrieveAllReimbursements(){
    let response = await fetch(myUri, {method : 'GET'});
    console.log(response);
    let reimburseList = await response.text();
}

pFilterButton.addEventListener('click', (event)=>{
  event.preventDefault();
  reloadPendingReimbursements();
})

rFilterButton.addEventListener('click',(event)=>{
  event.preventDefault();
  reloadResolvedReimbursements();
})

function reloadPendingReimbursements(){
  
  while(table.children[0]){
    table.children[0].remove();
  }
  
  loadPendingSet();

}

function reloadResolvedReimbursements(){
  while(resolvedTable.children[0]){
    resolvedTable.children[0].remove();
  }

  loadResolvedSet();
}

function separateSets(){
  for (let i of currManagerSet){
    console.log(i);

    if(i.status === "Pending"){
      pendingList.push(i);
    } else{
      resolvedList.push(i);
    }
  }
}

function loadPendingSet(){

  for(let i of pendingList){
    console.log(pFieldFilters.value);
    if(pFieldFilters.value){
      if(pFieldFilters.value == i.owner_id){
        displayList.push(i);
        let newRow = document.createElement("tr")
        newRow.innerHTML = `<td>${i.owner_id}</td><td>${i.timestamp}</td><td>$${i.value}</td><td>${i.notes}</td><td><a target="_blank" href="#" onClick='openImage(this)'><img src="${i.receipt}" style="width : 50%;"></a></td>
        <td>
        <select class="entry">
        <option value="Pending">Pending</option>
        <option value="Approved">Approve</option>
        <option value="Rejected">Reject</option></select>
        </td>`;
        table.appendChild(newRow);
      }
    }else{
      displayList.push(i);
      let newRow = document.createElement("tr")
        newRow.innerHTML = `<td>${i.owner_id}</td><td>${i.timestamp}</td><td>$${i.value}</td><td>${i.notes}</td><td><a target="_blank" href="#" onClick='openImage(this)'><img src="${i.receipt}" style="width : 50%;"></a></td>
        <td>
        <select class="entry">
        <option value="Pending">Pending</option>
        <option value="Approved">Approve</option>
        <option value="Rejected">Reject</option></select>
        </td>`;
        table.appendChild(newRow);
    }
  }
}

function loadResolvedSet(){
  for(let i of resolvedList){

    if(rFieldFilters.value){
      if(rFieldFilters.value == i.owner_id){
        let newRow = document.createElement("tr")
      newRow.innerHTML = `<td>${i.owner_id}</td><td>${i.timestamp}</td><td>$${i.value}</td><td>${i.notes}</td><td><a target="_blank" href="#" onClick='openImage(this)'><img src="${i.receipt}" style="width : 50%;"></a></td>
      <td>
        ${i.status}
      </td>`;
      resolvedTable.appendChild(newRow);
      }
    }else{
    let newRow = document.createElement("tr")
      newRow.innerHTML = `<td>${i.owner_id}</td><td>${i.timestamp}</td><td>$${i.value}</td><td>${i.notes}</td><td><a target="_blank" href="#" onClick='openImage(this)'><img src="${i.receipt}" style="width : 50%;"></a></td>
      <td>
        ${i.status}
      </td>`;
      resolvedTable.appendChild(newRow);
    }
  }
}



let xmlhttp = new XMLHttpRequest();
let pendingList = [];
let resolvedList = [];

xmlhttp.open("GET", managerUri);
xmlhttp.responseType = 'json';
xmlhttp.send();

xmlhttp.onload = function () {
  let jsonObj = xmlhttp.response;
  currManagerSet = jsonObj.reimburse;
  
  loadDefaultSet();
};

function loadDefaultSet(){
  console.log("loadDefaultSet Run");
  separateSets();
  loadPendingSet();
  loadResolvedSet();
}


add.addEventListener('click', (event)=>{
  event.preventDefault();
  
  let managerList = document.getElementsByClassName("entry");
  console.log(managerList);

  for(let i = 0; i < managerList.length; i++){
    console.log(managerList[i].options[managerList[i].selectedIndex].value);
    if (managerList[i].options[managerList[i].selectedIndex].value != "Pending"){
      pendingList[i].status = managerList[i].options[managerList[i].selectedIndex].value;
    }
  }

    sendUpdates();
  //let response = await fetch('http://localhost:8080/project/api/updateReimb', {method : 'POST' , })

  
})

async function sendUpdates(){
  console.log(pendingList);
  let updatePacket = {};
  updatePacket.reimburse = pendingList;
  let response = await fetch('http://localhost:8080/project1/api/updateReimb', {method : 'POST' , body : JSON.stringify(updatePacket)});
  window.location.href = '/project1/ManagerReimbursement.html';
}

const convertBase64ToFile = function (image) {
  const byteString = atob(image.split(',')[1]);
  const ab = new ArrayBuffer(byteString.length);
  const ia = new Uint8Array(ab);
  for (let i = 0; i < byteString.length; i += 1) {
    ia[i] = byteString.charCodeAt(i);
  }
  const newBlob = new Blob([ab], {
    type: 'image/png',
  });
  console.log(newBlob);
  return newBlob;
};

function openImage(element){
  let newTab = window.open();
  setTimeout(function() {
      let image = element.querySelector("img");
      image.style.width = '50%';
      newTab.document.body.innerHTML = element.innerHTML;
  }, 500);
  return false;
}