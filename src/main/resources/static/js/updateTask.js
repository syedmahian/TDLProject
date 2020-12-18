function updateData(){
  
  let _id = document.querySelector("#commentPostId").value;
  let data = {};
  data["title"] = document.querySelector("#toDoTitle").value;
  data["task"] = document.querySelector("#toDoTask").value;


  fetch("http://localhost:9092/toDo/update/"+_id, {
      method: 'put',
      headers: {
        "Content-type": "application/json; charset=UTF-8"
      },
      body:JSON.stringify(data)
    })
    .then(response => response.json())
    .then(function (data) {
      console.log('Request succeeded with JSON response', data);
      
      let display = document.querySelector("#displayDiv");
            console.log(data);
             display.innerHTML += data.id;
             display.innerHTML += data.title;
             display.innerHTML += data.task;
           
    })
    .catch(function (error) {
      console.log('Request failed', error);
    });
  }

  let updateButton = document.querySelector("#UpdateSubmit").addEventListener('click', updateData);