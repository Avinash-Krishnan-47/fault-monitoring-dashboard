const logout_btn = document.getElementById("logout-btn") ; 

logout_btn.addEventListener("click" , () => {
    const response = fetch("http://localhost:8080/logout/account" , {
        method : "DELETE" , 
        credentials : "include"
    })
    document.getElementById("signup-acc").style.display = 'block' ; 
    document.getElementById("login-acc").style.display = 'block' ; 
    document.getElementById("profile-pic").style.display = 'none' ; 
    document.getElementById("logout-btn").style.display = 'none' ; 
    return ; 
})