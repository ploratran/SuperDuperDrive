package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mapper.UserMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.User;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
/**
 * implement a custom AuthenticationProvider interface which authorizes user logins
 * by matching their credentials against those stored in the database.
 */
@Service
public class AuthenticationService implements AuthenticationProvider {
    // fields:
    private UserMapper userMapper;
    private HashService hashService;

    // constructor:
    public AuthenticationService(UserMapper usermapper, HashService hashService) {
        this.userMapper = usermapper;
        this.hashService = hashService;
    }

    // override authenticate() used by Spring to
    // check user credential using Spring's special login form:
    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String username = authentication.getName();
        String password = authentication.getCredentials().toString();

        User user = this.userMapper.getUser(username);
        if (user != null) {
            String encodedSalt = user.getSalt();
            String hashedPassword = this.hashService.getHashedValue(password, encodedSalt);

            if (user.getPassword().equals(hashedPassword)) {
                return new UsernamePasswordAuthenticationToken(username, password, new ArrayList<>());
            }
        }

        return null;
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }
}
