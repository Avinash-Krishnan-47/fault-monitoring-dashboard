from pydantic import BaseModel

class ParameterToken(BaseModel):
    temperature : float 
    pressure : float
    vibration : float
    humidity : float