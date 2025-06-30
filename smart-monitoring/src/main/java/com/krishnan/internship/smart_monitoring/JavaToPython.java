package com.krishnan.internship.smart_monitoring;

import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.OutputStreamWriter;

@Component
public class JavaToPython {

    public String getCauses(String equipment , float temperature , float pressure , float vibration , float humidity){
        try{
            ProcessBuilder builder = new ProcessBuilder("python" , "causes.py") ;
            Process process = builder.start() ;

            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(process.getOutputStream())) ;
            writer.write(equipment + "," + temperature + "," + pressure + "," + vibration + "," + humidity) ;

            writer.flush() ;
            writer.close() ;

            return null ;
        }
        catch(Exception e){
            System.out.println("Exception occured in Java To Python file!!") ;
            e.printStackTrace() ;
            return "Exception occured" ;
        }
    }
}
