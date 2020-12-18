// Read One Task by ID

// we set a constant to find the values from the search bar
const params = new URLSearchParams(window.location.search);

for(let param of params ){
    console.log("here i am",param)
    let id = param[1];
    console.log(id);
    getData(id)
}
function getDatabyID(){
  
  let id = document.querySelector("#commentPostId").value;
  
    fetch('http://localhost:9092/user/read/'+id)
      .then(
        function(response) {
          if (response.status !== 200) {
            console.log('Looks like there was a problem. Status Code: ' +
              response.status);
            return;
          }
          // Examine the text in the response
          response.json().then(function(data) {
             console.log("MY DATA OBJ",data)
             
             let display = document.querySelector("#displayDiv");
             display.innerHTML += data.id;
             display.innerHTML += "<br>"
             display.innerHTML += data.fullName;
             
    
          });
        }
      )
      .catch(function(err) {
        console.log('Fetch Error :-S', err);
      });
    }
    let readByIDButton = document.querySelector("#readOneSubmit").addEventListener('click', getDatabyID);