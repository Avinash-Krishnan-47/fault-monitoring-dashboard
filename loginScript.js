const submit_login_button = document.getElementById("login-button") ; 

submit_login_button.addEventListener("click" , function(event){
    event.preventDefault() ; 
    const uname = document.getElementById("login-uname").value ; 
    const pswd = document.getElementById("login-pswd").value ; 

    const formData = new URLSearchParams() ; 
    formData.append("uname" , uname) ; 
    formData.append("pswd" , pswd) ; 

    const response = fetch("http://localhost:8080/login/currentuser" , {
        method : "POST" , 
        headers : {
            "Content-Type" : "application/x-www-form-urlencoded" 
        },
        body : formData.toString()
    })
    .then(response => response.text())
    .then(data => {
        console.log(data) ; 
        if(data.startsWith("Bearer ")){
            localStorage.setItem("jwttoken" , data.substring(7)) ; 
            window.location.href = "home.html" ; 
            return ; 
        }
        userLoginUpdate(data) ; 
    })
    .catch(err => {
        console.err(err) ; 
        alert("Login failed due to server side error !!") ; 
    }) ; 
}) ; 

function userLoginUpdate(data){
    const elements = document.getElementsByClassName("login-unsuccessful") ; 

    if(elements.length == 0){
        return ; 
    }

    const paragraph = document.createElement("p") ; 
    paragraph.textContent = data ; 

    elements[0].appendChild(paragraph) ; 
}