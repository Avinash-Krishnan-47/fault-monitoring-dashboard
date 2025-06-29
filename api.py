import pickle
from fastapi import FastAPI
import uvicorn
from ParameterTokens import ParameterToken
import numpy as np
import pandas as pd

app = FastAPI() #Create a FastAPI

pickle_in = open("equipment_health_monitoring_new.pkl" , "rb") #Open the pkl file in read mode 
equipment_health_monitoring = pickle.load(pickle_in) 

@app.post('/predict-ml')
def condition_monitoring(parameter : ParameterToken):
    
    temperature = parameter.temperature 
    pressure = parameter.pressure
    vibration = parameter.vibration
    humidity = parameter.humidity

    input_data = pd.DataFrame([{
        "temperature" : temperature,
        "pressure" : pressure,
        "vibration" : vibration,
        "humidity" : humidity
    }])
    
    prediction = equipment_health_monitoring.predict(input_data)

    if(prediction[0] == 1):
        return "Healthy"
    return "Faulty"


