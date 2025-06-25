const submit_button = document.getElementById("forgot-password-button") ; 

submit_button.addEventListener("click" , function(event){
    event.preventDefault() ; 
    const email = document.getElementById("forgot-password-email").value ;
    const formData = new URLSearchParams() ; 
    formData.append("email" , email) ; 

    const response = fetch("http://localhost:8080/forgot-password/codegenerator" , {
        method : "POST" , 
        headers : {
            "Content-Type" : "application/x-www-form-urlencoded" 
        },
        body : formData.toString() 
    })

    .then(response => response.text()) 
    .then(data => {
        console.log(data) ; 
        if(data === "Mail Sent successfully"){
            alert("Code sent successfully to the email") ; 
            window.location.href = "forgot-password-code-input.html" ; 
            return ; 
        }
        updateForgotPassword(data) ; 
    })
    .catch(err => {
        console.error(err) ; 
        alert("Server side problem . Try after sometime") ; 
    }) ; 
}) ; 

function updateForgotPassword(data){
    const elements = document.getElementsByClassName("forgot-password-email-not-exists") ; 

    if(elements.length == 0){
        return ; 
    }

    const paragraph = document.createElement("p") ; 
    paragraph.textContent = data ; 

    elements[0].appendChild(paragraph) ; 
}