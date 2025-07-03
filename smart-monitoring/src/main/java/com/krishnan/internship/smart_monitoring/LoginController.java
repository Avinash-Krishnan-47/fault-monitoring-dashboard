package com.krishnan.internship.smart_monitoring;

import jakarta.servlet.http.HttpServletResponse;
import org.apache.tomcat.util.http.SameSiteCookies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.MissingRequestHeaderException;
import org.springframework.web.bind.annotation.*;

import java.sql.*;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/login")
public class LoginController {

    @Autowired
    private RedisService redisService ;

    String dbUrl = "jdbc:mysql://127.0.0.1:3306/loginDB" ;
    String username = "loginUsers" ;
    String password = "avinashkrishnan4832" ;

    @Autowired
    private PasswordEncoder passwordEncoder ;

    @Autowired
    JWTUtil jwt ;
    @PostMapping("/currentuser")
    public String loginIntoAccount(@RequestParam("uname") String uname , @RequestParam("pswd") String pswd , HttpServletResponse response){
        if(!checker(uname)){
            System.out.println("Username not found") ;
            return "No user found with the given username . Please signup instead !" ;
        }
        String sqlStatement = "SELECT pswd FROM availableUsers WHERE (username = ? OR email = ?)" ;
        try{
            Connection myConn = DriverManager.getConnection(dbUrl , username , password) ;
            System.out.println("Database connection given successfully for the login function !!") ;
            PreparedStatement stmt = myConn.prepareStatement(sqlStatement) ;
            stmt.setString(1 , uname) ;
            stmt.setString(2 , uname) ;
            ResultSet rset = stmt.executeQuery() ;
            boolean exists = rset.next() ;
            if(exists){
                String hashedPassword = rset.getString("pswd") ;
                if(passwordEncoder.matches(pswd , hashedPassword)){
                    String token = jwt.generateToken(uname) ;
                    redisService.storeToken(token , 10) ;

                    ResponseCookie cookie = ResponseCookie.from("auth_token" , token)
                                    .httpOnly(true)
                                    .secure(true)
                                    .path("/")
                                    .maxAge(600)
                                    .sameSite("Strict")
                                    .build() ;

                    response.addHeader(HttpHeaders.SET_COOKIE , cookie.toString()) ;
                    System.out.println("Login successful") ;
                    return "Login successful" ;
                }
                else{
                    System.out.println("Incorrect password") ;
                    return "Incorrect password" ;
                }
            }
            else{
                System.out.println("No user found") ;
                return "No user found" ;
            }
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

    @GetMapping("/content-load")
    public boolean isValidToken(@CookieValue(value="auth_token") String token){
        try{
            if(token == null){
                return false ;
            }
            System.out.println("Valid token") ;
            return redisService.isValidToken(token) ;
        }
        catch (Exception e){
            System.out.println("There is an exception underlying with") ;
            e.printStackTrace() ;
            return false ;
        }
    }
}
