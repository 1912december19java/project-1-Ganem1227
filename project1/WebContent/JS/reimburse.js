'use strict'

let table = document.getElementById("reimburse-content");

function populateTable(){

    for(let i = 0; i < 10; i++){
        let newRow = document.createElement("tr");
        newRow.innerHTML = "<td>1/1/1111</td><td>$1.00</td><td>No reason</td><td>Pending</td>";
        table.appendChild(newRow);
    }

}

populateTable();