const submit_button = document.getElementById("forgot-password-code-input-button") ; 

submit_button.addEventListener("click" , function(event){
    event.preventDefault() ; 

    const code = document.getElementById("enter-code-here").value ; 
    const formData = new URLSearchParams() ; 
    formData.append("code" , code) ; 

    const response = fetch("http://localhost:8080/forgot-password/code-input" , {
        method : "POST" , 
        headers : {
            "Content-Type" : "application/x-www-form-urlencoded"
        },
        body : formData.toString() 
    })
    .then(response => response.text()) 
    .then(data => {
        console.log(data) ; 
        if(data === "Code verified successfully"){
            window.location.href = "create-new-password.html" ; 
            return ; 
        }
        updateForUser(data) ; 
    })
    .catch(err => {
        console.error(err) ; 
        alert("Error occured when verifying code ...") ; 
    })
})

function updateForUser(data){
    const elements = document.getElementsByClassName("user-update-code-input") ; 

    if(elements.length == 0){
        return ; 
    }

    const paragraph = document.createElement("p") ; 
    paragraph.textcontent = data ; 

    elements[0].appendChild(paragraph) ; 
}