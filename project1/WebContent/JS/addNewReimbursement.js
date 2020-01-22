'use strict'

let photoInput = document.getElementById('receipt');
let amount = document.getElementById('amount');
let notes = document.getElementById('notes');
let submit = document.getElementById('Submit');

let fileUri = "http://localhost:8080/project1/api/newReimb";

async function checkFileInput(){
    
    let file = photoInput.files[0];
    console.log(file);
    
    let blob;
    let fileReader = new FileReader();
    fileReader.readAsDataURL(file);
    fileReader.onloadend =  async () => {
        blob = fileReader.result;
        console.log(blob);
        console.log("blobl",blob);

        let packet = {};
        packet.value = amount.value;
        packet.notes = notes.value;
        packet.receipt = blob;
    
        console.log(packet);
    
        let response = await fetch(fileUri, {method : 'POST', body : JSON.stringify(packet)});
        console.log(response);
        window.location.href = '/project1/Reimburse.html';
    }


}

submit.addEventListener('click', (event)=>{
    checkFileInput();
})


