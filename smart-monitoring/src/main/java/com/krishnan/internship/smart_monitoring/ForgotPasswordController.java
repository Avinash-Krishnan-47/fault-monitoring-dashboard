package com.krishnan.internship.smart_monitoring;

import org.hibernate.boot.jaxb.mapping.marshall.ResultCheckStyleMarshalling;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.sql.*;

@CrossOrigin(origins = "http://localhost:3000")
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

    @Autowired
    private PasswordEncoder passwordEncoder ;

    @Autowired
    private RedisService redisService ;

    private String eMail = null ;

    @PostMapping("/codegenerator")
    public String setCode(@RequestParam("email") String email){
        if(!checker(email)){
            System.out.println("Email not exist for setCode function") ;
            return "Email does not exist" ;
        }
        eMail = email ;
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

    @PostMapping("/code-input")
    public String codeInputTaker(@RequestParam("code") String code){
        if(eMail == null){
            return "Invalid email" ;
        }
        if(redisService.getCode(eMail).equals(code)){
            System.out.println("Code verified successfully") ;
            redisService.deleteCode(eMail) ;
            return "Code verified successfully" ;
        }
        System.out.println("Invalid code provided by the user") ;
        return "Invalid code" ;
    }

    @PutMapping("/confirm-password")
    public String confirmPassword(@RequestParam("password") String pass){
        try{
            String pswd = passwordEncoder.encode(pass) ;
            Connection myConn = DriverManager.getConnection(dbUrl , username , password) ;
            String sqlStatement = "UPDATE availableUsers SET pswd = ? WHERE username = ? OR email = ?" ;
            PreparedStatement stmt = myConn.prepareStatement(sqlStatement) ;
            stmt.setString(1 , pswd) ;
            stmt.setString(2 , eMail) ;
            stmt.setString(3 , eMail) ;
            stmt.executeUpdate() ;
            System.out.println("Password updated successfully") ;
            return "Updated Password successfully" ;
        }
        catch(Exception e){
            e.printStackTrace() ;
            System.out.println("An error occured in the confirm-password method - ForgotPasswordController class") ;
            return "Database error occured" ;
        }
    }
}
