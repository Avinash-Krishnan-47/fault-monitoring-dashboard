window.addEventListener("DOMContentLoaded" , () =>{
    const jwt = localStorage.getItem("jwttoken") ; 

    if(jwt == null || jwt.length == 0){
        document.getElementById("signup-acc").style.display = "block" ; 
        document.getElementById("login-acc").style.display = "block" ; 
        document.getElementById("profile-pic").style.display = "none" ; 
        document.getElementById("logout-btn").style.display = "none" ; 
        return ; 
    }
    else{
        const response = fetch("http://localhost:8080/login/content-load" , {
            method : "GET" , 
            headers : {
                "Authorization" : "Bearer " + jwt
            }
        })
        .then(response => response.text()) 
        .then(data => {
            if(data === "true"){
                document.getElementById("signup-acc").style.display = "none" ; 
                document.getElementById("login-acc").style.display = "none" ; 
                document.getElementById("profile-pic").style.display = "block" ; 
                document.getElementById("logout-btn").style.display = "block" ; 
            }
            else{
                document.getElementById("signup-acc").style.display = "block" ; 
                document.getElementById("login-acc").style.display = "block" ; 
                document.getElementById("profile-pic").style.display = "none" ; 
                document.getElementById("logout-btn").style.display = "none" ; 
                localStorage.removeItem("jwttoken") ; 
            }
        })
        .catch(err => {
            console.error(err) ; 
            document.getElementById("signup-acc").style.display = "block" ; 
            document.getElementById("login-acc").style.display = "block" ; 
            document.getElementById("profile-pic").style.display = "none" ; 
            document.getElementById("logout-btn").style.display = "none" ; 
            localStorage.removeItem("jwttoken") ; 
        }) ; 
    } ; 
}) ; 