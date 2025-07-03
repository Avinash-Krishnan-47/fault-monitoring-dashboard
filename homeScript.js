window.addEventListener("DOMContentLoaded" , () => {
    const reponse = fetch("http://localhost:8080/login/content-load" , {
        method : "GET" , 
        credentials : "include"
    })
    .then(response => response.text())
    .then(data => {
        if(data === 'true'){
            document.getElementById("signup-acc").style.display = 'none' ; 
            document.getElementById("login-acc").style.display = 'none' ; 
            document.getElementById("profile-pic").style.display = 'block' ; 
            document.getElementById("logout-btn").style.display = 'block' ; 
        }
        else{
            document.getElementById("signup-acc").style.display = 'block' ; 
            document.getElementById("login-acc").style.display = 'block' ; 
            document.getElementById("profile-pic").style.display = 'none' ; 
            document.getElementById("logout-btn").style.display = 'none' ; 
        }
    })
    .catch(err => {
        console.error('An error occured with : ' + err) ; 
        document.getElementById("signup-acc").style.display = 'block' ; 
        document.getElementById("login-acc").style.display = 'block' ; 
        document.getElementById("profile-pic").style.display = 'none' ; 
        document.getElementById("logout-btn").style.display = 'none' ; 
    }) ; 
}) ; 