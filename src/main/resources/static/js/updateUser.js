function updateData(){
  
  let _id = document.querySelector("#commentPostId").value;
  let data = {};
  data["fullName"] = document.querySelector("#toDoTitle").value;
  


  fetch("http://localhost:9092/user/update/"+_id, {
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
             display.innerHTML += "<br>"
             display.innerHTML += data.fullName;
             
           
    })
    .catch(function (error) {
      console.log('Request failed', error);
    });
  }

  let updateButton = document.querySelector("#UpdateSubmit").addEventListener('click', updateData);