package pl.coderslab.dao;

import java.util.Collection;

import pl.coderslab.model.User;

public interface UserDao {
	
	
	int save(User user);
	boolean update(User user);
	User loadUserById(int id);
	User loadUserByEmail(String email);
	Collection<User> loadAllUsers();
	boolean delete(long id);
	

}
