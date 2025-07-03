window.addEventListener("DOMContentLoaded" , () => {
    console.log('Entering into the dashboardscript') ; 
    fetch("http://localhost:8080/dashboard/retrieve-data" , {
        method : "GET" , 
        credentials : "include"
    })
    .then(res => {
        if(res.status === 401 || res.status === 403){
            console.log('403 error or 401 error') ; 
            return(Swal.fire({
                title : 'Login required as authorization not provided with' , 
                text : 'Authentication required' , 
                icon : 'error'
            })).then(data => {
                window.location.href = 'login.html' ; 
                return ; 
            })
        }
        return res.json() ; 
    })
    .then(data => {
        if(!data){
            console.log('No data is there') ; 
        }
        for(let i = 0 ; i < data.length ; i++){
            let item = data[i] ; 
            addToTable(item.timestamp , item.temperature , item.pressure , item.vibration , item.humidity , item.statusMonitor) ; 
        }
    })
    .catch(err => {
        console.log('I am into the catch part of dashboardScript') ; 
        Swal.fire({
            title : 'Please login first' , 
            text : err , 
            icon : 'error'
        })
        .then((result) => {
            if(result.isConfirmed){
                window.location.href = "login.html" ; 
                console.error('An error occured upon loading the data : ' + err) ; 
            }
        })
    }) ; 
})

const submit_button = document.getElementById("predict-health-button") ; 

submit_button.addEventListener("click" , function(event){
    event.preventDefault() ; 

    const temperature = document.getElementById("temp").value ; 
    const pressure = document.getElementById("pre").value ; 
    const vibration = document.getElementById("vib").value ; 
    const humidity = document.getElementById("hum").value ; 

    if(!temperature || !pressure || !vibration || !humidity){
        Swal.fire({
            title : 'Insert all the values' ,
            text : 'Please insert all the four values' , 
            icon : 'success'
        }) ; 
        return ; 
    }

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
        credentials : "include" , 
        headers : {
            "Content-Type" : "application/x-www-form-urlencoded" 
        } , 
        body : formData.toString() 
    })
    .then(response => response.text())
    .then(data => {
        console.log(data) ; 
        updateStatus(data) ; 
        const equipment = "motor" ; 
        const formData1 = new URLSearchParams() ; 
        formData1.append("equipment" , equipment) ; 
        formData1.append("temp" , temp) ; 
        formData1.append("pres" , press) ; 
        formData1.append("vib" , vib) ; 
        formData1.append("humid" , humid) ; 
        if(data === "\"Faulty\""){
            const res = fetch("http://localhost:8080/dashboard/causes" , {
                method : "POST" , 
                credentials : "include" , 
                headers : {
                    "Content-Type" : "application/x-www-form-urlencoded"
                } , 
                body : formData1.toString()
            })
            .then(res => {return res.text()})
            .then(d =>{
                console.log(d) ; 
                console.log(typeof(d)) ; 
                splitter(d) ; 
            })
            .catch(err => {
                console.error(err) ; 
            }) ; 
        }
        else{
            const cause = document.getElementById("cause-value") ; 
            const soln = document.getElementById("solution-value") ; 
            cause.textContent = '-' ; 
            soln.textContent = '-' ; 
        }
        const responseTable = fetch("http://localhost:8080/dashboard/retrieve-data-after" , {
            method : "GET" , 
            credentials : "include"
        })
        .then(responseTable => responseTable.json())
        .then(data => {
            console.log(data) ; 
            const firstEntry = data[0] ; 
            addToTable(firstEntry.timestamp , firstEntry.temperature , firstEntry.pressure , firstEntry.vibration , firstEntry.humidity , firstEntry.statusMonitor) ; 
        })
        .catch(err => {
            console.error('An error occured at the responseTable fetch : ' + err) ; 
        }) ; 
    })
    .catch(err => {
        console.error('Some backend problem occured ...' + err) ; 
        Swal.fire({
            title : 'Session Expired' , 
            text : 'Please login again' , 
            icon : 'error'
        }) ; 
        window.location.href = "login.html" ; 
    }) ; 
}) ; 

function temperatureConverter(temperature , tempUnit){
    temperature = Number(temperature) ; 
    if(tempUnit === 'celcius'){
        return temperature ; 
    }
    if(tempUnit === 'kelvin'){
        return temperature - 273.16 ; 
    }
    return (5*(temperature - 32)) / 9 ; 
}

function pressureConverter(pressure , pressureUnit){
    pressure = Number(pressure) ; 
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
    vibration = Number(vibration) ; 
    if(vibrationUnit == 'mm/s'){
        return vibration ; 
    }
    return vibration * 0.1 ; 
}

function humidityConverter(humidity , humidUnit){
    humidity = Number(humidity) ; 
    return humidity ; 
}

function updateStatus(data) {
    const elements = document.getElementsByClassName("output-section") ; 

    const element = document.getElementById("status-output") ; 

    const span = document.getElementById("status-value") ; 

    if(data === "\"Healthy\""){
        elements[0].style.backgroundColor = "green" ; 
        span.textContent = data ; 
        span.style.fontSize = "17px" ; 
        span.style.color = "darkblue" ; 
        span.style.fontFamily = "sans-serif" ; 
        span.style.textTransform = "uppercase" ; 
    }
    else{
        elements[0].style.backgroundColor = "red" ; 
        span.textContent = data ; 
        span.style.fontSize = "17px" ; 
        span.style.color = "#721c24" ; 
        span.style.fontFamily = "sans-serif" ; 
        span.style.textTransform = "uppercase" ; 
    }

    console.log("This is the updated version") ; 
}

function splitter(data){
    const arr = data.split(".") ; 
    
    const cause = document.getElementById("cause-value") ; 
    const soln = document.getElementById("solution-value") ; 

    if(arr.length < 2){
        return ;
    }
    cause.textContent = arr[0] ; 
    cause.style.fontSize = "17px" ; 
    cause.style.fontFamily = "sans-serif" ; 
    cause.style.color = "yellow" ; 

    soln.textContent = arr[1] ; 
    soln.style.fontSize = "17px" ; 
    soln.style.fontFamily = "sans-serif" ; 
    soln.style.color = "yellow" ; 

    console.log("Am parsing the splitter function here...") ; 

    return ; 
}

function addToTable(timeStamp , temperature , pressure , vibration , humidity , statusMonitor){
    const table = document.getElementById("history-logs") ; 
    const newrow = table.insertRow(1) ; 

    const cell1 = newrow.insertCell(0) ; 
    const cell2 = newrow.insertCell(1) ; 
    const cell3 = newrow.insertCell(2) ; 
    const cell4 = newrow.insertCell(3) ; 
    const cell5 = newrow.insertCell(4) ; 
    const cell6 = newrow.insertCell(5) ; 

    cell1.textContent = timeStamp ; 
    cell2.textContent = temperature ; 
    cell3.textContent = pressure ; 
    cell4.textContent = vibration ; 
    cell5.textContent = humidity ; 
    cell6.textContent = statusMonitor ; 

    console.log("Inserted values successfully...") ; 
}