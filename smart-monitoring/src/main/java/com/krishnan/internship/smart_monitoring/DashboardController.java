package com.krishnan.internship.smart_monitoring;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/dashboard")
public class DashboardController {

    @Autowired
    private WebClientService webClientService ;

    @Autowired
    private JavaToPython javaToPython ;

    @PostMapping("/input-parameters")
    public String inputParameters(@RequestParam("temp") float temperature , @RequestParam("pres") float pressure
    , @RequestParam("vib") float vibration , @RequestParam("humid") float humidity){
        try{
            webClientService.insertValues(temperature , pressure , vibration , humidity) ;
            return webClientService.getResponseFromModel() ;
        }
        catch(Exception e){
            System.out.println("Exception occured in the inputParameters method") ;
            e.printStackTrace() ;
            return "Database error occured" ; 
        }
    }

    @PostMapping("/causes")
    public String causesFinder(@RequestParam("equipment") String equipment , @RequestParam("temp") float temperature , @RequestParam("pres") float pressure
            , @RequestParam("vib") float vibration , @RequestParam("humid") float humidity){
        try{
            String res = javaToPython.getCauses(equipment , temperature , pressure , vibration , humidity) ;
            System.out.println("This is our res : " + res) ; 
            return res ;
        }
        catch(Exception e){
            System.out.println("Exception occured at causesFInder method in DashboardController") ;
            e.printStackTrace() ;
            return "Exception occured" ;
        }
    }
}
