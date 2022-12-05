package com.enigma.repository;

import com.enigma.exception.NotFoundException;
import com.enigma.exception.RestTemplateException;
import com.enigma.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;


@Repository
public class UserRepositoryImpl implements UserRepository {

    @Value("${service.userUri}")
    private String serviceUserUri;

    private RestTemplate restTemplate;

    public UserRepositoryImpl(@Autowired RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Override
    public User[] getAll() {
        try {
            ResponseEntity<User[]> response = restTemplate.getForEntity(serviceUserUri, User[].class);
            User[] users = response.getBody();
            return users;
        } catch (RestClientException e) {
            throw new RestTemplateException(serviceUserUri, HttpStatus.UNPROCESSABLE_ENTITY, "Service is down");
        }
    }

    @Override
    public User getById(String id) {
        try {
            ResponseEntity<User> response = restTemplate.getForEntity(serviceUserUri + "/" + id, User.class);
            User user = response.getBody();
            return user;
        } catch (RestClientException e) {
           throw new NotFoundException("Data is not found");
        }
    }

    @Override
    public void updateById(User user) {
        try {
            String url = serviceUserUri + "/" + user.getId();
            restTemplate.put(url, user);
        } catch (RestClientException e) {
            throw new NotFoundException("Data is not found");
        }
    }

    @Override
    public void delete(String id) {
        try {
            String url = serviceUserUri + "/" + id;
            restTemplate.delete(url);
        } catch (RestClientException e) {
            throw new RestTemplateException(serviceUserUri, HttpStatus.UNPROCESSABLE_ENTITY, "Service is down");
        }
    }
}
