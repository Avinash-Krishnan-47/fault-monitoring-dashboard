package com.krishnan.internship.smart_monitoring;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

@CrossOrigin(origins = "http://localhost:3000" , allowCredentials = "true")
@RestController
@RequestMapping("/dashboard")
public class DashboardController {

    @Autowired
    private JWTUtil jwtUtil ;

    String dbUrl = "jdbc:mysql://localhost:3306/loginDB" ;
    String uname = "loginUsers" ;
    String password = "avinashkrishnan4832" ;

    @Autowired
    private WebClientService webClientService ;

    @Autowired
    private JavaToPython javaToPython ;

    @PostMapping("/input-parameters")
    public String inputParameters(@RequestParam("temp") float temperature , @RequestParam("pres") float pressure
    , @RequestParam("vib") float vibration , @RequestParam("humid") float humidity , @CookieValue(value = "auth_token" , required = false) String auth){
        try{
            if(auth == null){
                return "Cookie not present" ;
            }
            webClientService.insertValues(temperature , pressure , vibration , humidity) ;
            String res =  webClientService.getResponseFromModel() ;
            Connection myConn = DriverManager.getConnection(dbUrl , uname , password) ;
            String sqlStatement = "SELECT id FROM availableUsers WHERE username = ? OR email = ?" ;
            PreparedStatement stmt = myConn.prepareStatement(sqlStatement) ;
            String user = jwtUtil.extractUser(auth) ;
            stmt.setString(1 , user) ;
            stmt.setString(2 , user) ;
            ResultSet rset = stmt.executeQuery() ;
            if(rset.next()){
                int id = rset.getInt("id") ;
                String sql = "INSERT INTO dashboardUsers(user_id , temperature , pressure , vibration , humidity , StatusMonitor) VALUES(?,?,?,?,?,?)" ;
                PreparedStatement stmt1 = myConn.prepareStatement(sql) ;
                stmt1.setInt(1 , id) ;
                stmt1.setFloat(2 , temperature) ;
                stmt1.setFloat(3 , pressure) ;
                stmt1.setFloat(4 , vibration) ;
                stmt1.setFloat(5 , humidity) ;
                stmt1.setString(6 , res) ;
                stmt1.executeUpdate() ;
            }
            else{
                return "No user found!!" ;
            }
            return res ;
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

    @GetMapping("/retrieve-data")
    public ResponseEntity<List<DashboardParams>> retriever(@CookieValue(value = "auth_token" , required = false) String auth) {
        System.out.println("Hello there from the DashboardController") ;
        List<DashboardParams> list = new ArrayList<>() ;
        if(auth == null || auth.isEmpty()){
            System.out.println("The cookie is empty") ;
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(list) ;
        }
        System.out.println("Cookie is not empty") ;
        try{
            String sqlStatement = "SELECT timestmp , temperature , pressure , vibration , humidity , statusMonitor FROM dashboardUsers WHERE user_id = ? ORDER BY id DESC" ;
            Connection myConn = DriverManager.getConnection(dbUrl , uname , password) ;
            String sql = "SELECT id FROM availableUsers WHERE username = ? OR email = ? " ;
            String user = jwtUtil.extractUser(auth) ;
            PreparedStatement stmt1 = myConn.prepareStatement(sql) ;
            stmt1.setString(1 , user) ;
            stmt1.setString(2 , user) ;
            ResultSet rset1 = stmt1.executeQuery() ;
            rset1.next() ;
            int id = rset1.getInt("id") ;
            PreparedStatement stmt = myConn.prepareStatement(sqlStatement) ;
            stmt.setInt(1 , id) ;
            ResultSet rset = stmt.executeQuery() ;
            while(rset.next()){
                DashboardParams dashboardParams = new DashboardParams() ;
                dashboardParams.setTimestamp(rset.getTimestamp("timestmp")) ;
                dashboardParams.setTemperature(rset.getFloat("temperature")) ;
                dashboardParams.setPressure(rset.getFloat("pressure")) ;
                dashboardParams.setVibration(rset.getFloat("vibration")) ;
                dashboardParams.setHumidity(rset.getFloat("humidity")) ;
                dashboardParams.setStatusMonitor(rset.getString("statusMonitor")) ;
                list.add(dashboardParams) ;
            }
            System.out.println(list) ;
            return ResponseEntity.ok(list) ;
        }
        catch (Exception e){
            e.printStackTrace() ;
            System.out.println("Exception occured - retriever function") ;
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(list) ;
        }
    }

    @GetMapping("/retrieve-data-after")
    public List<DashboardParams> retrieverAfter(@CookieValue(value = "auth_token") String auth){
        List<DashboardParams> list = new ArrayList<>() ;
        DashboardParams obj = new DashboardParams() ;
        try{
            String sqlStatement = "SELECT timestmp , temperature , pressure , vibration , humidity , statusMonitor FROM dashboardUsers WHERE user_id = ? ORDER BY id DESC LIMIT 1" ;
            Connection myConn = DriverManager.getConnection(dbUrl , uname , password) ;
            String sql = "SELECT id FROM availableUsers WHERE username = ? OR email = ? " ;
            String user = jwtUtil.extractUser(auth) ;
            PreparedStatement stmt1 = myConn.prepareStatement(sql) ;
            stmt1.setString(1 , user) ;
            stmt1.setString(2 , user) ;
            ResultSet rset1 = stmt1.executeQuery() ;
            rset1.next() ;
            int id = rset1.getInt("id") ;
            PreparedStatement stmt = myConn.prepareStatement(sqlStatement) ;
            stmt.setInt(1 , id) ;
            ResultSet rset = stmt.executeQuery() ;
            while(rset.next()){
                DashboardParams dashboardParams = new DashboardParams() ;
                dashboardParams.setTimestamp(rset.getTimestamp("timestmp")) ;
                dashboardParams.setTemperature(rset.getFloat("temperature")) ;
                dashboardParams.setPressure(rset.getFloat("pressure")) ;
                dashboardParams.setVibration(rset.getFloat("vibration")) ;
                dashboardParams.setHumidity(rset.getFloat("humidity")) ;
                dashboardParams.setStatusMonitor(rset.getString("statusMonitor")) ;
                list.add(dashboardParams) ;
            }
            System.out.println(list) ;
            return list ;
        }
        catch (Exception e){
            e.printStackTrace() ;
            System.out.println("Exception occured - retriever function") ;
            return list ;
        }
    }
}
