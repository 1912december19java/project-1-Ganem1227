'use strict'

let username = document.getElementById("login-user");
let password = document.getElementById("login-pass");
let submit = document.getElementById("login-button");

let myUri = "http://localhost:8080/project1/login";

submit.addEventListener('click',(event)=>{
    event.preventDefault();
    console.log("Login with: username: " + username.value + " | password: " + password.value);
    let user = username.value;
    let pass = password.value;
    if (user != '' && pass != '') {
        sendLogin();
    }
});

async function sendLogin() {
  
    let loginCredits = {};
    loginCredits.username = username.value;
    loginCredits.password = password.value;
  
    console.log(loginCredits);
  
    let response = await fetch(myUri, { method: 'POST', body: JSON.stringify(loginCredits) });
    console.log(response);
  };