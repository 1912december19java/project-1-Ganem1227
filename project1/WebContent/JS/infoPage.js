'use strict'

let name = document.getElementsByClassName("name");
let job = document.getElementById("job");
let email = document.getElementById("email");
let manager = document.getElementById("manager");

let myUri = "http://localhost:8080/project1/api/employeeInfo";

async function populateFields(){
    let response = await fetch(myUri, {method : 'GET'})
    let employeeData = await response.json();
    console.log(employeeData);

    //name.textContent = employeeData.firstName + employeeData.lastName;

    for (let el of name){
        el.textContent = employeeData.firstName + " " + employeeData.lastName;
    }

    job.textContent = employeeData.job;
    email.textContent = employeeData.email;
    manager.textContent = employeeData.managerName;
    //let firstname = await response.first_name;
}

populateFields();