
CREATE DATABASE school;

CREATE TABLE user_group( 
	id INT AUTO_INCREMENT, 
	name VARCHAR(255), 
	PRIMARY KEY(id));

CREATE TABLE user (
  	id BIGINT AUTO_INCREMENT,
  	username varchar(255) NOT NULL,
  	email varchar(255) NOT NULL UNIQUE,
  	password varchar(255) NOT NULL,
  	user_group_id INT DEFAULT NULL,
  	PRIMARY KEY (id),
  	FOREIGN KEY (user_group_id) REFERENCES user_group (id) );
  
CREATE TABLE exercise( 
  	id INT AUTO_INCREMENT, 
  	title VARCHAR(255), 
  	description TEXT, 
  	PRIMARY KEY(id) );
  
CREATE TABLE solution( 
	id INT AUTO_INCREMENT, 
	created DATETIME, 
	updated DATETIME, 
	description TEXT, 
	exercise_id INT, 
	user_id BIGINT, 
	PRIMARY KEY(id), 
	FOREIGN KEY(exercise_id) REFERENCES exercise(id), 
	FOREIGN KEY(user_id) REFERENCES user(id));
