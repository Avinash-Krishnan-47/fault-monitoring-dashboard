const reset_button = document.getElementById("create-new-password-button") ; 

reset_button.addEventListener("click" , function(event){
    event.preventDefault() ; 

    const password = document.getElementById("new-password-input").value ; 
    const confirmPassword = document.getElementById("confirm-password-input").value ; 

    if(password !== confirmPassword){
        passwordsNotMatching() ; 
        return ; 
    }

    const formData = new URLSearchParams() ; 
    formData.set("password" , confirmPassword) ; 

    const response = fetch("http://localhost:8080/forgot-password/confirm-password" , {
        method : "PUT" , 
        headers : {
            "Content-Type" : "application/x-www-form-urlencoded" 
        } , 
        body : formData.toString()
    })
    .then(response => response.text())
    .then(data => {
        console.log(data) ; 
        window.location.href = 'home.html' ; 
        return ; 
    })
    .catch(err => {
        console.error(err) ; 
    }) ; 
}) ; 

function passwordsNotMatching(){

    const element = document.getElementById("password-and-confirm-password-validation-span") ; 
    if(!element){
        return ; 
    }

    element.textContent = "Passwords do not match !!" ; 
    element.style.display = "block" ; 
    element.style.margin = "10px auto" ; 
    element.style.color = "red" ; 
    element.style.fontSize = "14px" ; 
    element.style.fontFamily = "sans-serif" ; 

    console.log('Passwords do not match') ; 

    return ; 

}