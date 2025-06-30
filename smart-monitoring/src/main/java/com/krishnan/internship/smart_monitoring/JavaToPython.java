package com.krishnan.internship.smart_monitoring;

import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.Scanner;

@Component
public class JavaToPython {

    public String getCauses(String equipment , float temperature , float pressure , float vibration , float humidity){
        try{
            System.out.println("Executed the first line...") ;
            ProcessBuilder builder = new ProcessBuilder("python" , "D://Internship Project/causes.py") ;
            Process process = builder.start() ;

            System.out.println("Executed the second line ....") ;
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(process.getOutputStream())) ;
            writer.write(equipment + "," + temperature + "," + pressure + "," + vibration + "," + humidity) ;

            System.out.println("Executed the third line....") ;
            writer.flush() ;
            writer.close() ;

            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            StringBuilder res = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println("Updating") ;
                res.append(line).append("\n");
            }
            System.out.println("Executed the fourth line...") ;
            reader.close();

            System.out.println(res.toString()) ;
            return res.toString() ;
        }
        catch(Exception e){
            System.out.println("Exception occured in Java To Python file!!") ;
            e.printStackTrace() ;
            return "Exception occured" ;
        }
    }
}
