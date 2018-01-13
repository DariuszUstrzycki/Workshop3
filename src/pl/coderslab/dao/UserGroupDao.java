package pl.coderslab.dao;

import java.util.Collection;
import java.util.List;

import pl.coderslab.model.Exercise;
import pl.coderslab.model.UserGroup;

public interface UserGroupDao {
	
	int save(UserGroup group);
	boolean update(UserGroup group);
	UserGroup loadUserGroupById(int id);
	Collection<UserGroup> loadAllUserGroups();
	boolean delete(long id);

}
