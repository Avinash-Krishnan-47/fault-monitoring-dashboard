package com.krishnan.internship.smart_monitoring;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/dashboard")
public class DashboardController {

    @Autowired
    private WebClientService webClientService ;

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
}
