package com.krishnan.internship.smart_monitoring;

import org.springframework.web.bind.annotation.*;

import java.sql.*;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/login")
public class LoginController {

    String dbUrl = "jdbc:mysql://127.0.0.1:3306/loginDB" ;
    String username = "loginUsers" ;
    String password = "avinashkrishnan4832" ;

    JWTUtil jwt = new JWTUtil() ;
    @PostMapping("/currentuser")
    public String loginIntoAccount(@RequestParam("uname") String uname , @RequestParam("pswd") String pswd){
        if(!checker(uname)){
            System.out.println("Username not found") ;
            return "No user found with the given username . Please signup instead !" ;
        }
        String sqlStatement = "SELECT 1 FROM availableUsers WHERE (username = ? OR email = ?) AND pswd = ?" ;
        try{
            Connection myConn = DriverManager.getConnection(dbUrl , username , password) ;
            System.out.println("Database connection given successfully for the login function !!") ;
            PreparedStatement stmt = myConn.prepareStatement(sqlStatement) ;
            stmt.setString(1 , uname) ;
            stmt.setString(2 , uname) ;
            stmt.setString(3 , pswd) ;
            ResultSet rset = stmt.executeQuery() ;
            boolean exists = rset.next() ;
            if(exists){
                String token = jwt.generateToken(uname) ;
                System.out.println("Login successful !!") ;
                return "Bearer " + token ;
            }
            return "Incorrect Password" ; 
        }
        catch(SQLException e){
            System.out.println("Database error occured in the login Function !!") ;
            e.printStackTrace() ;
            return "Database Error occured !" ;
        }
    }

    public boolean checker(String uname){
        String sqlStatement = "SELECT 1 FROM availableUsers WHERE username = ? OR email = ?" ;
        try{
            Connection myConn = DriverManager.getConnection(dbUrl , username , password) ;
            System.out.println("Database connected successfully for checker function !!") ;
            PreparedStatement stmt = myConn.prepareStatement(sqlStatement) ;
            stmt.setString(1 , uname) ;
            stmt.setString(2 , uname) ;
            ResultSet rset = stmt.executeQuery() ;
            return rset.next() ;
        }
        catch(SQLException e){
            System.out.println("Database error occured in the checker function") ;
            e.printStackTrace() ;
            return false ;
        }
    }
}
