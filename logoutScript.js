const logout_btn = document.getElementById("logout-btn") ; 
const jwt = localStorage.getItem("jwttoken") ; 

logout_btn.addEventListener("click" , () => {
    localStorage.removeItem("jwttoken") ; 
    const response = fetch("http://localhost:8080/logout/account" , {
        method : "DELETE" , 
        headers : {
            "Authorization" : "Bearer " + jwt
        }
    })
    window.location.href = "home.html" ; 
    return ; 
})