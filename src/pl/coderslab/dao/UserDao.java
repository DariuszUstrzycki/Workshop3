package pl.coderslab.dao;

import java.util.Collection;

import pl.coderslab.model.User;

public interface UserDao {
	
	
	int save(User user);
	boolean update(User user);
	User loadUserById(int id);
	User loadUserByUsername(String username);
	User loadUserByEmail(String email);
	Collection<User> loadAllUsers();
	Collection<User> loadUsersByGroupId(long groupId);
	boolean delete(long id);
	

}
