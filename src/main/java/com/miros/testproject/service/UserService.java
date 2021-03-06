package com.miros.testproject.service;
import com.miros.testproject.data.DAO.UserDAO;
import com.miros.testproject.data.entity.User;
import com.miros.testproject.exception.RuntimeEx;

import java.util.stream.Collectors;
import java.util.List;
import java.util.stream.Stream;

public class UserService {
    private List<User> userDAO = UserDAO.getInstance().getInstanceList();

    public boolean save(User user) throws RuntimeEx {
            return userDAO.add(user);
    }

    public User find(int id) throws RuntimeEx {
        return userDAO.get(id);
    }

    public User delete(int id) {
        return userDAO.remove(id);
    }

    public boolean delete(User user) {
        return userDAO.remove(user);
    }

    public Stream<User> findAll(){
        return userDAO.stream();
    }

    public boolean exist(int id){
        int size = userDAO.stream()
                .filter(s-> s.getId() == id)
                .collect(Collectors.toList())
                .size();
        if(size>0){
            return true;
        }
        return false;
    }
}
