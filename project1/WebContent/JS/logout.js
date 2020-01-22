'use strict'

let logoutButton = (document.getElementsByClassName("logout"))[0];
let logOutUri = "http://localhost:8080/project1/api/logout";
let navbarLinks = document.getElementById("navbar-list");

logoutButton.addEventListener('click', (e)=>{
    doLogOut();
})

async function doLogOut(){
    let response = await fetch(logOutUri, {method : 'GET'});
    response = response.json();
}

window.addManagerOption = function(){
    //console.log("why you no run");
    navbarLinks = document.getElementById("navbar-list");
    if (isManager){
        //console.log("RUN FOR FUCK'S SAKE");
        let newOption = document.createElement("li");
        newOption.innerHTML = '<li class="navbar-item" id="ManagerOption"><a class="nav-link" href="ManagerReimbursement.html">Manager Portal</a></li>';
        navbarLinks.appendChild(newOption);
    }else{
        //console.log("DON'T RUN FOR FUCK'S SAKE");
        if (navbarLinks != null){
            document.removeElement("ManagerOption");
        }
    }
}

window.addManagerOption();