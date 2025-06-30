import sys
import rag_integration as obj

for line in sys.stdin:
    try:
        equipment , temperature , pressure , vibration , humidity = line.strip().split(",")
        causes = obj.question_asker(equipment , temperature , pressure , vibration , humidity)
        print(causes)
    except ValueError:
        print("An error occured") 