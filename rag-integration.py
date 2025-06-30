import os
from dotenv import load_dotenv
import google.generativeai as genai
import json

load_dotenv()
api_key = os.getenv("GOOGLE_API_KEY")
genai.configure(api_key=api_key) 

model = genai.GenerativeModel("gemini-1.5-flash-002") 

def question_asker(equipment , temperature , pressure , vibration , humidity):
    question = f"What is the problem for the {equipment} equipment which has got a {temperature} temperature (C) , {pressure} pressure (bar) , {vibration} vibration (mm/s) and {humidity} humidity (%RH)"
    
    prompt_causes = f"I will give you a question and you have tell only 3 causes regarding the given problem for an industrial health monitoring thing . All the three causes should be in 2 line with sentences and dont tell anything out of it and they all should be crisp and crystal clear. The question is {question}"

    response_causes = model.generate_content(prompt_causes)

    return response_causes 

def solution_asker(equipment , temperature , pressure , vibration , humidity , causes):
    question = f"What is the problem for the {equipment} equipment which has got a {temperature} temperature (C) , {pressure} pressure (bar) , {vibration} vibration (mm/s) and {humidity} humidity (%RH)"

    prompt_solution = f"Give me ONLY two lines regarding the solution for the given problem and causes regarding industrial equipment health monitoring . The problem is {question} and causes are {causes}"

    response_solution = model.generate_content(prompt_solution)

    return response_solution

