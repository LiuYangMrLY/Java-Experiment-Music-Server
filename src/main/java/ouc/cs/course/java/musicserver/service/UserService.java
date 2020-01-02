package ouc.cs.course.java.musicserver.service;

import ouc.cs.course.java.musicserver.dao.impl.UserDaoImpl;
import ouc.cs.course.java.musicserver.model.User;

import java.util.List;

public class UserService {
    UserDaoImpl userDao = new UserDaoImpl();

    public int create(User user) {
        return userDao.insert(user);
    }

    public List<User> getAll() {
        return userDao.findAll();
    }

    public User getOne(int id) {
        return userDao.findById(id);
    }

    public User getOne(String name) {
        return userDao.findByName(name);
    }

    public User getOne(String name, String passMd5Value) {
        return userDao.findOne(name, passMd5Value);
    }
}
