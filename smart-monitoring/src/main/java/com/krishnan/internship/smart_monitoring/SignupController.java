package com.krishnan.internship.smart_monitoring;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.sql.*;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/signup")
public class SignupController {

    SignupReg signup;

    @Autowired
    public SignupController(SignupReg signup) {
        this.signup = signup;
    }

    @Autowired
    private PasswordEncoder passwordEncoder ;
    // Database details
    String dbUrl = "jdbc:mysql://127.0.0.1:3306/loginDB";
    String dbUsername = "loginUsers";
    String dbPassword = "avinashkrishnan4832";

    @PostMapping("/register")
    public String registerUser(@RequestParam("uname") String uname,
                               @RequestParam("pswd") String password,
                               @RequestParam("email") String email) {
        System.out.println("Received signup request: " + uname + ", " + email);
        try {
            // Check if username or email exists
            if (checker(uname, email)) {
                System.out.println("Username or Email already exists. Please use a different one.");
                return "Invalid Credentials";
            }

            System.out.println("Proceeding to register user...");

            // Insert user into database
            try (Connection myConn = DriverManager.getConnection(dbUrl, dbUsername, dbPassword)) {
                String hashedPassword = passwordEncoder.encode(password) ;
                System.out.println("Database Connection succeeded") ;

                String sqlStatement = "INSERT INTO availableUsers (username, email, pswd) VALUES (?, ?, ?)";
                PreparedStatement stmt = myConn.prepareStatement(sqlStatement);

                stmt.setString(1, uname);
                stmt.setString(2, email);
                stmt.setString(3, hashedPassword);

                int rowUpdates = stmt.executeUpdate();

                if (rowUpdates > 0) {
                    System.out.println("✅ User registered successfully");
                    return "Registration successful";
                } else {
                    System.out.println("⚠️ Registration failed");
                    return "Registration failed";
                }

            } catch (SQLException e) {
                System.out.println("❌ Database Connection failed");
                e.printStackTrace();
                return "Database connection error";
            }

        } catch (SQLException e) {
            System.out.println("❌ Error during user checking or insertion");
            e.printStackTrace();
            return "Error during signup";
        }
    }

    public boolean checker(String uname, String email) throws SQLException {
        System.out.println("Checking for existing username or email...");

        String sql = "SELECT * FROM availableUsers WHERE username = ? OR email = ?";

        try (Connection myConn = DriverManager.getConnection(dbUrl, dbUsername, dbPassword);
             PreparedStatement stmt = myConn.prepareStatement(sql)) {

            System.out.println("✅ Database Connection succeeded for checker");

            stmt.setString(1, uname);
            stmt.setString(2, email);

            ResultSet rset = stmt.executeQuery();

            boolean exists = rset.next();

            if (exists) {
                System.out.println("⚠️ Username or Email already present in database");
            } else {
                System.out.println("✅ Username and Email are unique");
            }

            rset.close();
            return exists;

        } catch (SQLException e) {
            System.out.println("❌ Error while checking existing user");
            e.printStackTrace();
            return true; // Block registration if check fails
        }
    }

    @GetMapping("/test")
    public String test() {
        System.out.println("Test endpoint hit");
        return "Test Successful";
    }

}
