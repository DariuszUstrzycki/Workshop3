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
