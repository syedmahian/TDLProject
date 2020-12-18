//Read All for Users

function readAll(){

  fetch('http://localhost:9092/user/read')
    .then(
      function(response) {
        if (response.status !== 200) {
          console.log('Looks like there was a problem. Status Code: ' +
            response.status);
          return;
        }
  
        // Examine the text in the response
        response.json().then(function(data) {
          console.log(data);
          createPara(data);
        });
      }
    )
    .catch(function(err) {
      console.log('Fetch Error :-S', err);
    });
  
  }
  
  function createPara(data){
    let myDiv = document.getElementById("myDiv");
    myDiv.innerHTML = "";
    for (let i =0; i<data.length;i++) {
    let para = document.createElement("p"); // Create a <p> element
    para.className ="alert alert-warning";
    para.style.color="blue";
    para.innerHTML = `id:${data[i].id}`;
    para.innerHTML += `<br>Username: ${data[i].fullName}`;
    for (let j = 0; j <data[i].toDos.length; j++){
      
      para.innerHTML += `<br>To-Dos: ${data[i].toDos[j].title}`;  
    }
    para.innerHTML += "&nbsp; <button data-id='"+ data[i].id + "' class='delete' onclick='deleteData(" + data[i].id + ")'> Delete</button>";
    
    
    myDiv.appendChild(para);
    }
  }
  
  let readAllButton = document.querySelector("#readAllButton").addEventListener('click', readAll);
  readAll();

//// Delete Tasks Function

function deleteData(id){
  fetch("http://localhost:9092/user/delete/" + id, {
      method: 'delete',
      headers: {
        "Content-type": "application/json; charset=UTF-8"
      }
  
    })
    .then(function (data) {
      console.log('Request succeeded with JSON response', data);
      readAll();
    })
    .catch(function (error) {
      console.log('Request failed', error);
    });
    
}

// Create User Function

// Create ToDo Function

function createTask(data){
    
  let x = {};
  x["fullName"] = document.getElementById("username-input").value;
  
  sendData(x);
  
}

function sendData(data){
fetch("http://localhost:9092/user/create", {
    method: 'post',
    headers: {
      "Content-type": "application/json; charset=UTF-8"
    },
    body:JSON.stringify(data)
  })
  .then(function (data) {
    console.log('Request succeeded with JSON response', data);
    readAll();
  })
  .catch(function (error) {
    console.log('Request failed', error);
  });
}
let createTaskButton = document.querySelector("#createButton").addEventListener('click', createTask);