package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mapper.UserMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.User;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.Base64;

/**
 * use UserMapper to access to database
 * use HashService to
 * */
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

    // upon Signup, check to see if a username is NOT already exists in database (if null):
    public boolean isUsernameAvailable(String username) {
        return this.userMapper.getUser(username) == null;
    }

    // create new user in USERS database with PK = userId:
    public int createUser(User user) {

        // use SecureRandom class to generate cryptographic, random number:
        SecureRandom random = new SecureRandom();
        // define salt:
        byte[] salt = new byte[16];
        // generate random with salt:
        random.nextBytes(salt);
        // encode salt with Base64:
        String encodedSalt = Base64.getEncoder().encodeToString(salt);
        // use Hash Service to hash user's input password with encoded salt:
        String hashedPassword = this.hashService.getHashedValue(user.getPassword(), encodedSalt);

        // insert new user into User db:
        return this.userMapper.insert(new User(null, user.getUsername(), encodedSalt, hashedPassword, user.getFirstName(), user.getLastName()));
    }

    // find specific userId by username from User database to use in the Controllers:
    public int getUserById(String username) {
        return this.userMapper.getUser(username).getUserId();
    }
}