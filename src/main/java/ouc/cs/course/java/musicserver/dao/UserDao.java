package ouc.cs.course.java.musicserver.dao;

import java.util.List;

import ouc.cs.course.java.musicserver.model.User;

public interface UserDao {

	int insert(User user);

	User findById(int id);
	
	User findByName(String name);
	
	User findOne(String name, String passMd5Value);

	List<User> findAll();
}
