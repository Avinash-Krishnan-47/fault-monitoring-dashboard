package com.krishnan.internship.smart_monitoring;

import org.hibernate.boot.jaxb.mapping.marshall.ResultCheckStyleMarshalling;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.sql.*;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/forgot-password")
public class ForgotPasswordController {

    String dbUrl = "jdbc:mysql://localhost:3306/loginDB" ;
    String username = "loginUsers" ;
    String password = "avinashkrishnan4832" ;

    private final EmailService service ;

    @Autowired
    public ForgotPasswordController(EmailService service){
        this.service = service ;
    }

    @PostMapping("/codegenerator")
    public String setCode(@RequestParam("email") String email){
        if(!checker(email)){
            System.out.println("Email not exist for setCode function") ;
            return "Email does not exist" ;
        }
        service.setJavaMailSender(email) ;
        return "Mail Sent successfully" ;
    }

    public boolean checker(String email){
        String sqlStatement = "SELECT 1 FROM availableUsers WHERE email = ?" ;
        try{
            Connection myConn = DriverManager.getConnection(dbUrl , username , password) ;
            System.out.println("Database connection successful for checker function") ;
            PreparedStatement stmt = myConn.prepareStatement(sqlStatement) ;
            stmt.setString(1 , email) ;
            ResultSet rset = stmt.executeQuery() ;
            boolean exists = rset.next() ;
            if(exists){
                System.out.println("Email exists") ;
            }
            else{
                System.out.println("Email Doesn't exist") ;
            }
            return exists ;
        }
        catch(SQLException e){
            System.out.println("Database error occured") ;
            return false ;
        }
    }
}
