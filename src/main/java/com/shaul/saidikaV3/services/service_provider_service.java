package com.shaul.saidikaV3.services;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.shaul.saidikaV3.auth.AuthService;
import com.shaul.saidikaV3.auth.Authorities.AccountRoles;
import com.shaul.saidikaV3.configs.AuthenticationManagers.saidika_provider_AuthenticationManager;
import com.shaul.saidikaV3.entities.Users;
import com.shaul.saidikaV3.entities.service_provider;
import com.shaul.saidikaV3.exceptions.EmailAlreadyRegisteredException;
import com.shaul.saidikaV3.exceptions.EmailNotFoundException;
import com.shaul.saidikaV3.exceptions.PasswordsDontMatchException;
import com.shaul.saidikaV3.repositories.service_provider_repo;
import com.shaul.saidikaV3.requestModels.loginRequestmodel;
import com.shaul.saidikaV3.requestModels.registerRequestModel;
import com.shaul.saidikaV3.responsemodels.login_response;

import jakarta.servlet.http.HttpServletRequest;

@Service
public class service_provider_service {
    @Autowired
    private AuthService authService;


    @Autowired
    service_provider_repo spr;

    

    @Autowired
    private PasswordEncoder passwordEncoder;

   
   
    @Autowired
    private saidika_provider_AuthenticationManager authenticationManager;


    // public service_provider add_provider(service_provider sp){
    //     return spr.save(sp);
    // }

    public Optional<service_provider> find_by_id(UUID id){
        return spr.findById(id);
    }

    public List<service_provider> get_all_providers() {
        return spr.findAll();
    }
public ResponseEntity<login_response> login(loginRequestmodel loginRequest) {

        Optional<service_provider> provOptional = spr.findByEmail(loginRequest.getEmail());
        if(provOptional.isEmpty())
            throw new EmailNotFoundException();

        service_provider serviceProvider = provOptional.get();
        String authorization = authService.loginUser(serviceProvider.getId(), loginRequest.getEmail(), loginRequest.getPassword(), authenticationManager,AccountRoles.PROVIDER);
           if(serviceProvider.getOfferedServices().isEmpty()){
       
          return ResponseEntity.ok(login_response.builder().message("Login Successful").Authorization(authorization).twoFactorEnabled(authService.get2FAEnabled(serviceProvider.getId(),AccountRoles.PROVIDER)).first_time_login(true).profile(serviceProvider.getId()).build());}
        else 
           
         return ResponseEntity.ok(login_response.builder().message("Login Successful").Authorization(authorization).twoFactorEnabled(authService.get2FAEnabled(serviceProvider.getId(),AccountRoles.PROVIDER)).first_time_login(false).profile(serviceProvider.getId()).build());
        
    }



    public ResponseEntity<service_provider> activeUser() {
        Users person=authService.getActiveProfile();
        if(person!=null&&person instanceof service_provider)
            return ResponseEntity.ok((service_provider) person);
        else
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
    }

    public ResponseEntity<String> registerProvider(registerRequestModel requestModel) {
        if(!requestModel.getPassword().equals(requestModel.getConfirmPassword()))
            throw  new PasswordsDontMatchException();
        Optional<service_provider> email_Exists = spr.findByEmail(requestModel.getEmail());
        if(email_Exists.isPresent())
            throw new EmailAlreadyRegisteredException();
       

        service_provider new_service_provider=new service_provider();
        new_service_provider.setFirst_name(requestModel.getFirst_name());
        new_service_provider.setLast_name(requestModel.getLast_name());
        new_service_provider.setPhone(requestModel.getPhone());
        new_service_provider.setEmail(requestModel.getEmail().toLowerCase().trim());
        new_service_provider.setPassword(passwordEncoder.encode(requestModel.getPassword()));
        new_service_provider.setRole(requestModel.getRole());

       spr.save(new_service_provider);

        return ResponseEntity.status(HttpStatus.CREATED).body("Registration Successful");
    }

    public ResponseEntity<String> logOut(HttpServletRequest httpServletRequest) {
        if(authService.getActiveProfile()==null)
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Not Logged In");
        return ResponseEntity.ok(authService.logOut(httpServletRequest));
    }


}
