const submit_button = document.getElementById("sign-up-button");

submit_button.addEventListener("click", function (event) {
    event.preventDefault(); 

    const username = document.getElementById("uname").value;
    const email = document.getElementById("mail").value;
    const password = document.getElementById("pswd").value;
    const confirm_password = document.getElementById("confirm-pswd").value;

    if (password !== confirm_password) {
        document.querySelector(".password-not-matching").textContent = "Passwords do not match." ; 
        return ; 
    }

    const formData = new URLSearchParams() ; 
    formData.append("uname", username) ;
    formData.append("pswd", password) ;
    formData.append("email", email) ;

    fetch("http://localhost:8080/signup/register", {
        method: "POST",
        headers: { 
            "Content-Type": "application/x-www-form-urlencoded" 
        },
        body: formData.toString()
    })
    .then(response => response.text())
    .then(data => {
        Swal.fire({
            text : data ,
            icon : 'success' 
        }) ; 
        if (data === "Registration successful") {
            window.location.href = "login.html" ; 
            return ; 
        }
    })
    .catch(err => {
        console.error(err);
        Swal.fire({
            title : 'Database error occured..' , 
            text : 'Server side problem .. Please stay tuned...' , 
            icon : 'error'
        }) ; 
    });
});