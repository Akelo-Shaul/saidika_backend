package com.shaul.saidikaV3.services;
import com.shaul.saidikaV3.auth.AuthService;
import com.shaul.saidikaV3.auth.Authorities.AccountRoles;
import com.shaul.saidikaV3.configs.AuthenticationManagers.saidika_findersAuthenticationManager;
import com.shaul.saidikaV3.entities.Users;
import com.shaul.saidikaV3.entities.service_finder;
import com.shaul.saidikaV3.exceptions.EmailAlreadyRegisteredException;
import com.shaul.saidikaV3.exceptions.EmailNotFoundException;
import com.shaul.saidikaV3.exceptions.PasswordsDontMatchException;
import com.shaul.saidikaV3.repositories.service_finder_repo;
import com.shaul.saidikaV3.requestModels.loginRequestmodel;
import com.shaul.saidikaV3.requestModels.registerRequestModel;
import com.shaul.saidikaV3.responsemodels.login_response;

import jakarta.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class service_finder_service {
    @Autowired
    AuthService authService;

   @Autowired
   private saidika_findersAuthenticationManager authenticationManager;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    service_finder_repo sfr;

    service_finder sf=new service_finder();

    // public service_finder add_finder(service_finder sf){
    //    return sfr.save(sf);
    // }

    public Optional<service_finder> find_by_id(UUID id){
            return sfr.findById(id);
    }

    public List<service_finder> get_all() {
        return sfr.findAll();
    }
public ResponseEntity<login_response> login(loginRequestmodel loginRequest) {

        Optional<service_finder> finderOptional = sfr.findByEmail(loginRequest.getEmail());
        if(finderOptional.isEmpty())
            throw new EmailNotFoundException();

        service_finder serviceFinder = finderOptional.get();

        String authorization = authService.loginUser(serviceFinder.getId(), loginRequest.getEmail(), loginRequest.getPassword(), authenticationManager,AccountRoles.SERVICE_FINDER);
        return ResponseEntity.ok(login_response.builder().message("Login Successful").Authorization(authorization).twoFactorEnabled(authService.get2FAEnabled(serviceFinder.getId(),AccountRoles.SERVICE_FINDER)).build());
    }



    public ResponseEntity<service_finder> activeUser() {
        Users person=authService.getActiveProfile();
        if(person!=null&&person instanceof service_finder)
            return ResponseEntity.ok((service_finder) person);
        else
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
    }

    public ResponseEntity<String> registerFinder(registerRequestModel requestModel) {
        if(!requestModel.getPassword().equals(requestModel.getConfirmPassword()))
            throw  new PasswordsDontMatchException();
        Optional<service_finder> email_Exists = sfr.findByEmail(requestModel.getEmail());
        if(email_Exists.isPresent())
            throw new EmailAlreadyRegisteredException();
       

        service_finder new_Service_finder=new service_finder();
        new_Service_finder.setFirst_name(requestModel.getFirst_name());
        new_Service_finder.setLast_name(requestModel.getLast_name());
        new_Service_finder.setPhone(requestModel.getPhone());
        new_Service_finder.setEmail(requestModel.getEmail().toLowerCase().trim());
        new_Service_finder.setPassword(passwordEncoder.encode(requestModel.getPassword()));
        new_Service_finder.setRole(requestModel.getRole());

       sfr.save(new_Service_finder);

        return ResponseEntity.status(HttpStatus.CREATED).body("Registration Successful");
    }

    public ResponseEntity<String> logOut(HttpServletRequest httpServletRequest) {
        if(authService.getActiveProfile()==null)
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Not Logged In");
        return ResponseEntity.ok(authService.logOut(httpServletRequest));
    }




    }
