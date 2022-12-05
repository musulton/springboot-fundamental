package com.enigma.service;

import com.enigma.model.User;

public interface UserService {
    User[] findAll();
    User findById(String id);
    void deleteById(String id);
    void updateById(User user);
}
