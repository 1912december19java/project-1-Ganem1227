'use strict'

let username = document.getElementById("login-user");
let password = document.getElementById("login-pass");
let submit = document.getElementById("login-button");

let myUri = "http://localhost:8080/project1/login";

submit.addEventListener('click',(event)=>{
    //event.preventDefault();
    console.log("Login with: username: " + username.value + " | password: " + password.value);
    let user = username.value;
    let pass = password.value;
    if (user != '' && pass != '') {
        sendLogin();
        //getAssignment();
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

function getAssignment(){
    let xhr = new XMLHttpRequest();

    xhr.addEventListener('readystatechange', (e)=>{
        if(xhr.readyState = xhr.DONE){
            if(xhr.status >= 200 && xhr.status < 300){

            }
        }
    })
    xhr.open('GET', myUri);

    //actually send
    xhr.send();
}

function outerFunction(){

    let myValue = 10;

    function innerFunction(){
        return myValue;
    }
    return innerFunction;
}

let myValueGetter = outerFunction();

console.log(myValueGetter());


function counterBuilder(initialValue){
    let currentValue = initialValue;

    function counter(){
        return currentValue++;
    }
    return counter;
}

let counterOne = counterBuilder(0);
let counterTwo = counterbuilder(100);


for (let i = 0; i < 30; i++){
    console.log(counterOne());

}

console.log("counter starting at 100: ");
for(let i = 0; i<10; i++){
    console.log(counterTwo());
}