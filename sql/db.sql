
CREATE DATABASE IF NOT EXISTS school 
	CHARACTER SET utf8mb4;

CREATE TABLE user_group( 
	id INT AUTO_INCREMENT, 
	name VARCHAR(255), 
	PRIMARY KEY(id))
    CHARACTER SET utf8mb4;
	
CREATE TABLE exercise( 
  	id INT AUTO_INCREMENT, 
  	title VARCHAR(255),  
  	description TEXT, 
  	PRIMARY KEY(id) )
    CHARACTER SET utf8mb4;	

CREATE TABLE user (  
  	id BIGINT UNSIGNED AUTO_INCREMENT,
  	username VARCHAR(255) NOT NULL UNIQUE, 
  	email VARCHAR(255) NOT NULL UNIQUE,
  	password VARCHAR(255) NOT NULL,
  	user_group_id INT DEFAULT 1, -- by default everyone has the status of a student
  	PRIMARY KEY (id),
  	FOREIGN KEY (user_group_id) REFERENCES user_group (id) ON DELETE SET NULL)
    CHARACTER SET utf8mb4;
  
CREATE TABLE solution( 
	id INT AUTO_INCREMENT, 
	created TIMESTAMP DEFAULT CURRENT_TIMESTAMP, 
	updated TIMESTAMP DEFAULT CURRENT_TIMESTAMP, 
	description TEXT,   
	exercise_id INT, 
	user_id BIGINT UNSIGNED, 
	PRIMARY KEY(id), 
	FOREIGN KEY(exercise_id) REFERENCES exercise(id) ON DELETE CASCADE, 
	FOREIGN KEY(user_id) REFERENCES user(id) ON DELETE NO ACTION)
    CHARACTER SET utf8mb4;
