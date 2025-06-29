package com.krishnan.internship.smart_monitoring;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
public class WebClientService {

    WebClient client = WebClient.create() ;

    @Autowired
    public DashboardParameters dashboardParameters ;

    public void insertValues(float temperature , float pressure , float vibration , float humidity){
        dashboardParameters.setTemperature(temperature) ;
        dashboardParameters.setPressure(pressure) ;
        dashboardParameters.setVibration(vibration) ;
        dashboardParameters.setHumidity(humidity) ;
    }

    public String getResponseFromModel(){
        try{
            String response = client.post()
                    .uri("http://localhost:8000/predict-ml")
                    .bodyValue(dashboardParameters)
                    .retrieve()
                    .bodyToMono(String.class)
                    .block() ;

            System.out.println("Successfully got response") ;
            return response ;
        }
        catch(Exception e) {
            System.out.println("Exception occured") ;
            e.printStackTrace() ;
            return "Database error occured" ;
        }
    }
}
