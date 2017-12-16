package pl.coderslab.dao;

import java.util.Collection;

import pl.coderslab.model.User;

public interface UserDao {
	
	
	int save(User user);
	boolean update(User user);
	User loadUserById(int id);
	User loadUserByEmailAndPassword(String email, String password);
	Collection<User> loadAllUsers();
	boolean delete(long id);
	

}
