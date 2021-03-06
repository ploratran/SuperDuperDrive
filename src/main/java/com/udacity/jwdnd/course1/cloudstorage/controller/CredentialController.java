package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.dto.CredentialDTO;
import com.udacity.jwdnd.course1.cloudstorage.model.Credential;
import com.udacity.jwdnd.course1.cloudstorage.services.CredentialService;
import com.udacity.jwdnd.course1.cloudstorage.services.EncryptionService;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.security.SecureRandom;
import java.util.Base64;

@Controller
public class CredentialController {
    // fields, use UserService, EncryptionService, CredentialService:
    private UserService userService;
    private EncryptionService encryptionService;
    private CredentialService credentialService;

    // constructor:
    public CredentialController(UserService uService, EncryptionService eService, CredentialService cService) {
        this.userService = uService;
        this.encryptionService = eService;
        this.credentialService = cService;
    }

    @ModelAttribute("credentialDTO")
    public CredentialDTO getCredentialDTO() {
        return new CredentialDTO();
    }

    @PostConstruct()
    public void postConstruct() {
        System.out.println("Creating Credential Controller bean");
    }

    // ADD new credential to CredentialDB:
    @PostMapping("/home/credential/newCredential")
    public String postNewCredential(@ModelAttribute("credentialDTO") CredentialDTO credDTO, Model model, Authentication auth) {

        String errMsg = null;

        int currentUserId = this.userService.getUserById(auth.getName());

        // create the credential variable that will be
        // responsible for receiving the credDTO variable data:
        Credential credential = new Credential();
        // set new data of URL, username, password to Credential with
        // new data from Credential DTO:
        credential.setCredentialId(credDTO.getCredentialID());
        credential.setUrl(credDTO.getCredentialUrl());
        credential.setUsername(credDTO.getCredentialUsername());
        credential.setPassword(credDTO.getCredentialPassword());

        // if there are no err, add new credential based on currentUserId:
        if (errMsg == null) {
            credential.setUserId(currentUserId);

            // encrypt password when user adds new credential before store to DB:
            SecureRandom random = new SecureRandom();
            byte[] key = new byte[16];
            random.nextBytes(key);
            String encodedKey = Base64.getEncoder().encodeToString(key);

            // set new encoded key and encrypted password to Credential Class Model:
            credential.setKey(encodedKey);

            // set encrypted password to Credential Class Model:
            String encryptedPassword = this.encryptionService.encryptValue(credential.getPassword(), credential.getKey());
            credential.setPassword(encryptedPassword);

            // add new credential to Credential DB:
            int currentCredId = this.credentialService.addCredential(credential);

            // check if there are errors adding new credential, display error:
            if (currentCredId < 0) {
                errMsg = "Error adding new credential.";
            }
        }

        // show result.html page with success/fail message:
        if (errMsg == null) {
            model.addAttribute("updateSuccess", true);
        } else {
            model.addAttribute("updateFail", errMsg);
        }

        return "result";
    }

    // DELETE a credential by its id:
    @GetMapping("/home/credential/delete/{credentialId}")
    public String deleteCredential(@PathVariable("credentialId") int credId, Model model) {

        String errMsg = null;

        if (errMsg == null) {
            // delete a credential by its credentialId:
            // return a successfully deleted credentialId:
            int deletedCredId = this.credentialService.deleteCredential(credId);

            // check if there is error deleting a credential, display error msg:
            if (deletedCredId < 1) {
                errMsg = "There was error deleting this credential.";
            }
        }

        if (errMsg == null) {
            model.addAttribute("updateSuccess", true);
        } else {
            model.addAttribute("updateFail", errMsg);
        }

        return "result";
    }

    // DECRYPT password by to show decrypted password when open up modal on frontend:
    // does NOT return thymeleaf template because we return decrypted password as HTTP response value:
    @GetMapping("/home/credential/decrypt-password/{credentialId}")
    public ResponseEntity<String> decryptPassword(@PathVariable("credentialId") int credentialId) throws IOException {
        Credential credential = this.credentialService.getCredentialById(credentialId);
        // use .decryptValue in EncryptionService to decrypt password:
        String decryptedPassword = this.encryptionService.decryptValue(credential.getPassword(), credential.getKey());
        System.out.println("Decrypted password " + decryptedPassword);

        // Reference: https://www.baeldung.com/spring-response-entity
        // return the decrypted password back as HTTP response:
        return ResponseEntity.ok(decryptedPassword);
    }
}