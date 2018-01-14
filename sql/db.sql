
CREATE DATABASE IF NOT EXISTS school 
	CHARACTER SET utf8mb4;

CREATE TABLE user_group( 
	id INT AUTO_INCREMENT, 
	name VARCHAR(30), 
	PRIMARY KEY(id))
    ENGINE=InnoDB CHARACTER SET utf8mb4;
    
    CREATE TABLE user (  
  	id BIGINT UNSIGNED AUTO_INCREMENT,
  	username VARCHAR(100) NOT NULL UNIQUE, 
  	email VARCHAR(255) NOT NULL UNIQUE,
  	password VARCHAR(100) NOT NULL,
  	user_group_id INT DEFAULT 1, -- by default everyone has the status of a student
  	PRIMARY KEY (id),
  	FOREIGN KEY (user_group_id) REFERENCES user_group (id) ON DELETE RESTRICT) -- cant remove a user group
    ENGINE=InnoDB CHARACTER SET utf8mb4;
	
CREATE TABLE exercise( 
  	id BIGINT UNSIGNED AUTO_INCREMENT, 
  	title VARCHAR(250),  
  	description TEXT, 
  	user_id BIGINT UNSIGNED,
  	PRIMARY KEY(id), 
  	FOREIGN KEY(user_id) REFERENCES user(id) ON DELETE SET NULL)
    ENGINE=InnoDB CHARACTER SET utf8mb4;	
  
CREATE TABLE solution( 
	id BIGINT UNSIGNED AUTO_INCREMENT, 
	created TIMESTAMP DEFAULT CURRENT_TIMESTAMP, 
	updated TIMESTAMP DEFAULT CURRENT_TIMESTAMP, 
	description TEXT,   
	exercise_id BIGINT UNSIGNED, 
	user_id BIGINT UNSIGNED, 
	PRIMARY KEY(id), 
	FOREIGN KEY(exercise_id) REFERENCES exercise(id) ON DELETE CASCADE, -- solution will be removed
	FOREIGN KEY(user_id) REFERENCES user(id) ON DELETE SET NULL)
    ENGINE=InnoDB CHARACTER SET utf8mb4;
    
CREATE TABLE attachment (
	id BIGINT UNSIGNED NOT NULL AUTO_INCREMENT,
	solution_id BIGINT UNSIGNED NOT NULL,
	attachment_name VARCHAR(255) NULL,
	mime_content_type VARCHAR(255) NOT NULL,
	contents BLOB NOT NULL,
	PRIMARY KEY(id),
	FOREIGN KEY(solution_id) REFERENCES solution(id) ON DELETE CASCADE
	) ENGINE = InnoDB CHARACTER SET utf8mb4;
