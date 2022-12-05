package com.enigma.service;

import com.enigma.model.User;
import com.enigma.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;

    public UserServiceImpl(@Autowired UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User[] findAll() {
        return userRepository.getAll();
    }

    @Override
    public User findById(String id) {
        return userRepository.getById(id);
    }

    @Override
    public void deleteById(String id) { userRepository.delete(id); }

    @Override
    public void updateById(User user) { userRepository.updateById(user); }
}
