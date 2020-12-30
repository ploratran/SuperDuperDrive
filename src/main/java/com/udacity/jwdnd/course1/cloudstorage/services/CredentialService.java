package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mapper.CredentialMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.Credential;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CredentialService {
    // field, use CredMapper:
    private CredentialMapper credMapper;

    // constructor:
    public CredentialService(CredentialMapper cMapper) {
        this.credMapper = cMapper;
    }

    // GET all creds from Cred DB by userId to use in CredController:
    public List<Credential> getAllCredentials(int userId) {
        return this.credMapper.findCredByUser(userId);
    }

    // ADD/UPDATE a cred in Cred DB:
    public int addCredential(Credential credential) {
        // check if a credential is null, then add new credential:
        if (credential.getCredentialId() == null) {
            return this.credMapper.addCredentialById(credential);
        }
        // else if a credential already exists, update it:
        else {
            return this.credMapper.updateCredentialById(credential);
        }
    }

    // DELETE a cred by credId:
    public int deleteCredential(int credentialId) {
        return this.credMapper.deleteCredentialById(credentialId);
    }

    public Credential getCredentialById(int credentialId) {
        return this.credMapper.getCredentialById(credentialId);
    }
}
