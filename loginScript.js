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
        credentials : "include" , 
        headers : {
            "Content-Type" : "application/x-www-form-urlencoded" 
        },
        body : formData.toString()
    })
    .then(response => response.text())
    .then(data => {
        console.log(data) ; 
        if(data === "Login successful"){
            window.location.href = "home.html" ; 
            return ; 
        }
        userLoginUpdate() ; 
    })
    .catch(err => {
        console.error(err) ; 
        Swal.fire({
            title : 'Database error occured..' , 
            text : 'Server side problem .. Please stay tuned...' , 
            icon : 'error'
        }) ; 
    }) ; 
}) ; 


function userLoginUpdate(){
    const elements = document.getElementsByClassName("login-unsuccessful") ; 

    if(elements.length == 0){
        return ; 
    }

    const span = document.getElementById("login-unsuccessful-span") ; 
    span.textContent = "Incorrect Password entered" ; 
    span.style.display = "block" ; 
    span.style.color = "red" ; 
    span.style.fontSize = "14px" ; 
    span.style.fontFamily = "sans-serif" ; 
    span.style.margin = "10px auto" ; 

    elements[0].appendChild(paragraph) ; 
}