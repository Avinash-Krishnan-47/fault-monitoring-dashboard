import os
from dotenv import load_dotenv
import google.generativeai as genai
import json

load_dotenv()
api_key = os.getenv("GOOGLE_API_KEY")
genai.configure(api_key=api_key) 

model = genai.GenerativeModel("gemini-1.5-flash") 

def question_asker(equipment , temperature , pressure , vibration , humidity):
    question = f"What is the problem for the {equipment} equipment which has got a {temperature} temperature (C) , {pressure} pressure (bar) , {vibration} vibration (mm/s) and {humidity} humidity (%RH)"
    
    prompt_causes = f"I will give you a question and you have tell only one cause ONLY regarding the given problem for an industrial health monitoring thing . The cause should be in one line with sentences and dont tell anything out of it and it should be crisp and crystal clear . Also club the solution for the given problem after giving the cause IN A SENTENCE followed by a full stop after the cause . DONT LEAVE A SPACE AFTER THE FULL STOP AND MORE IMPORTANLTY DONT KEEP A FULL STOP AT THE END OF THE SENTENCE THAT IS AT THE END OF THE SOLUTION ONLY . The question is {question}"

    response_causes = model.generate_content(prompt_causes)

    return response_causes.text

