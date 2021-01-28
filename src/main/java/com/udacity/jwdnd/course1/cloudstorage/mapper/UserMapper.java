package com.udacity.jwdnd.course1.cloudstorage.mapper;

import com.udacity.jwdnd.course1.cloudstorage.model.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface UserMapper {
    // use in AuthenticationService to get user by username from SQL database:
    // search user from USERS table based on username value:
    // return new User object:
    @Select("SELECT * FROM USERS WHERE username = #{username}")
    User getUser(String username);

    // insert new user into USERS table
    // @Options tells MyBatis to auto generate new key for userId:
    // return userId of the newly created user:
    @Insert("INSERT INTO USERS (username, salt, password, firstname, lastname) VALUES(#{username}, #{salt}, #{password}, #{firstName}, #{lastName})")
    @Options(useGeneratedKeys = true, keyProperty = "userId")
    int insert(User user);
}