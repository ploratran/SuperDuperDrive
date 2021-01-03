package com.udacity.jwdnd.course1.cloudstorage.mapper;

import com.udacity.jwdnd.course1.cloudstorage.model.Credential;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface CredentialMapper {
    // GET all credentials from Credential DB by userId:
    @Select("SELECT * FROM CREDENTIALS WHERE userId = #{userId}")
    List<Credential> findCredByUser(int userId);

    // INSERT/ADD new url, username, password into Credential DB by credId:
    @Insert("INSERT INTO CREDENTIALS (url, username, key, password, userId) " +
    "VALUES (#{url}, #{username}, #{key}, #{password}, #{userId})")
    @Options(useGeneratedKeys = true, keyProperty = "credentialId")
    int addCredentialById(Credential credentialId);

    // UPDATE/EDIT a new credential by credentialId:
    @Update("UPDATE CREDENTIALS SET url=#{url}, username=#{username}, key=#{key}, password=#{password} " +
    "WHERE (credentialid=#{credentialId})")
    int updateCredentialById(Credential credential);

    // DELETE a credential by credentialId:
    @Delete("DELETE FROM CREDENTIALS WHERE credentialid=#{credentialId}")
    int deleteCredentialById(int credentialId);

    // GET a credential by its credentialId
    // use in CredentialController to retrieve a credential
    // in order to decrypt a password:
    @Select("SELECT * FROM CREDENTIALS WHERE credentialid = #{credentialId}")
    Credential getCredentialById(int credentialId);
}