-- CREATE DATABASE loginDB ; 
USE loginDB ; 

-- CREATE TABLE availableUsers(
--      id INT AUTO_INCREMENT PRIMARY KEY , 
--      username VARCHAR(50) UNIQUE NOT NULL , 
--      email VARCHAR(100) NOT NULL UNIQUE , 
--      pswd VARCHAR(500) NOT NULL 
-- ) ; 

CREATE TABLE dashboardUsers(
	id INT auto_increment PRIMARY KEY , 
    user_id INT NOT NULL , 
    timestmp TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL , 
    temperature DECIMAL(10,5) NOT NULL , 
    pressure DECIMAL(10,5) NOT NULL , 
    vibration DECIMAL(10,5) NOT NULL , 
    humidity DECIMAL(10,5) NOT NULL , 
    statusMonitor VARCHAR(20) NOT NULL , 
    FOREIGN KEY (user_id) REFERENCES availableUsers(id) ON DELETE CASCADE
) ; 

-- DROP TABLE dashboardUsers ;