'use strict'

let name = document.getElementById("name");
let firstName = document.getElementById("firstName");
let lastName = document.getElementById("lastName");
let job = document.getElementById("job");
let email = document.getElementById("email");
let manager = document.getElementById("manager");
let isManager = false;
let employeePhoto = document.getElementById("employee-photo");

let employeeListPanel = document.getElementById("manager-content");
let employeeList = document.getElementById("employee-list");

let editButton = document.getElementById("editProfile");

let myUri = "http://localhost:8080/project1/api/employeeInfo";
let listUri = "http://localhost:8080/project1/api/employeeList";

async function populateFields(){
    let response = await fetch(myUri, {method : 'GET'})
    let employeeData = await response.json();
    console.log(employeeData);

    //name.textContent = employeeData.firstName + employeeData.lastName;

    job.textContent = employeeData.job;
    email.textContent = employeeData.email;
    manager.textContent = employeeData.managerName;
    firstName.textContent = employeeData.firstName;
    lastName.textContent = employeeData.lastName;
    name.textContent = `${employeeData.firstName} ${employeeData.lastName}`;
    employeePhoto.setAttribute('src',employeeData.photo);

    if (employeeData.job === "Manager"){
        isManager = true;
        employeeListPanel.style.visibility = 'visible';
        console.log('IsManager')
        populateEmployeeList();
    }else{
        isManager = false;
        employeeListPanel.style.visibility = 'hidden';
        console.log('IsNotManager');
    }

    window.addManagerOption();
}

populateFields();

editButton.addEventListener('click', (event)=>{
    event.preventDefault();
    window.location.href = '/project1/EditInfo.html';
})

async function populateEmployeeList(){
    let response = await fetch(listUri, {method : 'GET'});
    let list = await response.json();
    console.log(list);

    for(let i of list){
        let listEl = document.createElement("tr");
        listEl.innerHTML = `<td>${i.employeeId}</td><td>${i.firstName} ${i.lastName}</td><td>${i.job}</td><td>${i.email}</td>`;
        employeeList.appendChild(listEl);
    }
}