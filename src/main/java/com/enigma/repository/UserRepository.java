package com.enigma.repository;

import com.enigma.model.User;

public interface UserRepository {
    User[] getAll();

    User getById(String id);

    void delete(String id);

    void updateById(User user);
}
