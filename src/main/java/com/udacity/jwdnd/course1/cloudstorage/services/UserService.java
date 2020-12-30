package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mapper.UserMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.User;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.Base64;

@Service
public class UserService {
    // fields:
    private final UserMapper userMapper;
    private final HashService hashService;

    // constructor:
    public UserService(UserMapper userMapper, HashService hashService) {
        this.userMapper = userMapper;
        this.hashService = hashService;
    }

    // check to see if the username already exists in database (if null):
    public boolean isUsernameAvailable(String username) {
        return this.userMapper.getUser(username) == null;
    }

    // create new user into User database with PK = userId:
    public int createUser(User user) {
        SecureRandom random = new SecureRandom();
        // generate random salt:
        byte[] salt = new byte[16];
        random.nextBytes(salt);
        // generate base64 encoded salt:
        String encodedSalt = Base64.getEncoder().encodeToString(salt);
        // use Hash Service to hash user's input password with encoded salt:
        String hashedPassword = this.hashService.getHashedValue(user.getPassword(), encodedSalt);

        // insert new user into User db:
        return this.userMapper.insert(new User(null, user.getUsername(), encodedSalt, hashedPassword, user.getFirstName(), user.getLastName()));
    }

    // get specific user by username from User database:
    public int getUserById(String username) {
        return this.userMapper.getUser(username).getUserId();
    }
}