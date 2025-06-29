const submit_button = document.getElementById("predict-health-button") ; 

submit_button.addEventListener("click" , function(event){
    event.preventDefault() ; 

    const temperature = document.getElementById("temp").value ; 
    const pressure = document.getElementById("pre").value ; 
    const vibration = document.getElementById("vib").value ; 
    const humidity = document.getElementById("humid").value ; 

    const tempUnit = document.getElementById("temp-units").value ; 
    const presUnit = document.getElementById("pre-units").value ; 
    const vibUnit = document.getElementById("vib-units").value ; 
    const humidUnit = document.getElementById("hum-units").value ; 

    const temp = temperatureConverter(temperature , tempUnit) ; 
    const press = pressureConverter(pressure , presUnit) ; 
    const vib = vibrationConverter(vibration , vibUnit) ; 
    const humid = humidityConverter(humidity , humidUnit) ; 

    const formData = new URLSearchParams() ; 
    formData.append("temp" , temp) ; 
    formData.append("pres" , press) ; 
    formData.append("vib" , vib) ; 
    formData.append("humid" , humid) ; 

    const response = fetch("http://localhost:8080/dashboard/input-parameters" , {
        method : "POST" , 
        headers : {
            "Content-Type" : "application/x-www-form-urlencoded" 
        } , 
        body : formData.toString() 
    })
    .then(response => response.text())
    .then(data => {
        console.log(data) ; 
    })
    .catch(err => {
        console.error(err) ; 
    }) ; 
}) ; 

function temperatureConverter(temperature , tempUnit){
    if(tempUnit === 'celcius'){
        return temperature ; 
    }
    if(tempUnit === 'kelvin'){
        return temperature - 273.16 ; 
    }
    return (5*(temperature - 32)) / 9 ; 
}

function pressureConverter(pressure , pressureUnit){
    if(pressureUnit === 'bar'){
        return pressure ; 
    }
    if(pressureUnit === 'psi'){
        return pressure * 0.0689476 ; 
    }
    if(pressureUnit === 'pascals'){
        return pressure / 100000 ; 
    }
    return pressure * 1.01325 ; 
}

function vibrationConverter(vibration , vibrationUnit){
    if(vibrationUnit == 'mm/s'){
        return vibration ; 
    }
    return vibration * 0.1 ; 
}

function humidityConverter(humidity , humidUnit){
    return humidity ; 
}