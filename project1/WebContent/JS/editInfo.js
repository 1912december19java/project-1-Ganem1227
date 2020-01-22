'use strict'

let save = document.getElementById('save');
let cancel = document.getElementById('cancel');

let firstName = document.getElementById('first-name');
let lastName = document.getElementById('last-name');
let job = document.getElementById('job-position');
let email = document.getElementById('email');

let editInfoUri = 'http://localhost:8080/project1/api/editInfo';

cancel.addEventListener('click', (event)=>{
    event.preventDefault();
    window.location.href = '/project1/EmployeeInfo.html';
})

save.addEventListener('click', (event)=>{
    event.preventDefault();
    saveNewInfo();
    window.location.href = '/project1/EmployeeInfo.html';
})

async function saveNewInfo(){
    let packet = {};
    packet.firstName = firstName.value;
    packet.lastName = lastName.value;
    packet.email = email.value;
    packet.job = job.value;

    console.log("Packet: " + packet);

    let response = await fetch(editInfoUri, {method : 'POST', body : JSON.stringify(packet)});
    
}