package com.shaul.saidikaV3.services;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import com.shaul.saidikaV3.entities.offered_services;
import com.shaul.saidikaV3.requestModels.updateProfile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
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
import com.shaul.saidikaV3.repositories.service_provider_repo;
import com.shaul.saidikaV3.requestModels.loginRequestmodel;
import com.shaul.saidikaV3.requestModels.registerRequestModel;
import com.shaul.saidikaV3.responsemodels.login_response;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.multipart.MultipartFile;



@Service
public class service_provider_service {
    @Autowired
    private AuthService authService;


    @Autowired
    service_provider_repo spr;
@Autowired
offered_services_service ghj;
    

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
       
          return ResponseEntity.ok(login_response.builder().message("Login Successful").Authorization(authorization).first_time_login(true).profile_id(serviceProvider.getId()).profile_name(serviceProvider.getFirst_name()).build());}
        else 
           
         return ResponseEntity.ok(login_response.builder().message("Login Successful").Authorization(authorization).first_time_login(false).profile_id(serviceProvider.getId()).profile_name(serviceProvider.getFirst_name()).build());
        
    }



    public ResponseEntity<service_provider> activeUser() {
        Users person=authService.getActiveProfile();
        if(person!=null&&person instanceof service_provider)
            return ResponseEntity.ok((service_provider) person);
        else
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
    }

    public ResponseEntity<String> registerProvider(registerRequestModel requestModel, MultipartFile gh) throws IOException {


        Optional<service_provider> email_Exists = spr.findByEmail(requestModel.getEmail());
        if(email_Exists.isPresent())
            throw new EmailAlreadyRegisteredException("Account with Email Already exists");
       

        service_provider new_service_provider=new service_provider();
        new_service_provider.setFirst_name(requestModel.getFirst_name());
        new_service_provider.setLast_name(requestModel.getLast_name());
        new_service_provider.setPhone(requestModel.getPhone());
        new_service_provider.setAvailableLocation(requestModel.getLocation());
        new_service_provider.setEmail(requestModel.getEmail().toLowerCase().trim());
        new_service_provider.setPassword(passwordEncoder.encode(requestModel.getPassword()));
        new_service_provider.setRole(requestModel.getRole());
        new_service_provider.setNotif_token(requestModel.getNotifTok());
        if(gh != null){
            new_service_provider.setProfile_Photo_Path(setProfilePhoto(gh, requestModel.getEmail()));
            spr.save(new_service_provider);
            return ResponseEntity.status(HttpStatus.CREATED).body("Registration Successful");
        }else{
            spr.save(new_service_provider);
            return ResponseEntity.status(HttpStatus.CREATED).body("Registration Successful");
        }




    }

    public ResponseEntity<String> logOut(HttpServletRequest httpServletRequest) {
        if(authService.getActiveProfile()==null)
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Not Logged In");
        return ResponseEntity.ok(authService.logOut(httpServletRequest));
    }

 public ResponseEntity<String> update_profile(updateProfile update){
    service_provider  provider_update=spr.findById(authService.getActiveProfile().getId()).orElse(null);


    if(update.getPassword()==null && update.getPhone_number()!=null && update.getLocation()==null){
       provider_update.setPhone(update.getPhone_number());
       spr.saveAndFlush(provider_update);
   } else if (update.getPassword()!=null && update.getPhone_number()==null && update.getLocation()==null) {
       provider_update.setPassword(passwordEncoder.encode(update.getPassword()));
       spr.saveAndFlush(provider_update);
   }else if(update.getPassword()!=null && update.getPhone_number()!=null && update.getLocation()!=null){
          provider_update.setPhone(update.getPhone_number());
          provider_update.setPassword(passwordEncoder.encode(update.getPassword()));
          provider_update.setAvailableLocation(update.getLocation());
        List<offered_services> los= provider_update.getOfferedServices();
        los.forEach((os -> os.setAvailableLocation(update.getLocation())));
        spr.saveAndFlush(provider_update);
         ghj.update_all(los);
    } else if (update.getPassword()==null && update.getPhone_number()==null && update.getLocation()!=null) {
       provider_update.setAvailableLocation(update.getLocation());
        List<offered_services> los= provider_update.getOfferedServices();
        los.forEach((os -> os.setAvailableLocation(update.getLocation())));
        spr.saveAndFlush(provider_update);
        ghj.update_all(los);
   } else if (update.getPassword()!=null && update.getPhone_number()!=null && update.getLocation()==null) {
        provider_update.setPhone(update.getPhone_number());
        provider_update.setPassword(passwordEncoder.encode(update.getPassword()));
        spr.saveAndFlush(provider_update);
    } else if (update.getPassword()!=null && update.getPhone_number()==null && update.getLocation()!=null) {
        provider_update.setAvailableLocation(update.getLocation());
        List<offered_services> los= provider_update.getOfferedServices();
        los.forEach((os -> os.setAvailableLocation(update.getLocation())));
        provider_update.setPassword(passwordEncoder.encode(update.getPassword()));
        spr.saveAndFlush(provider_update);
        ghj.update_all(los);
    }else if(update.getPassword()==null && update.getPhone_number()!=null && update.getLocation()!=null){
        provider_update.setPhone(update.getPhone_number());
        provider_update.setAvailableLocation(update.getLocation());
        List<offered_services> los= provider_update.getOfferedServices();
        los.forEach((os -> os.setAvailableLocation(update.getLocation())));
        spr.saveAndFlush(provider_update);
        ghj.update_all(los);
    }
     return ResponseEntity.status(HttpStatus.OK).body("update successful");
 }

 public service_provider find_by_email(String em){
        return spr.findByEmail(em).orElse(null);
 }
    public String setProfilePhoto(MultipartFile dp,String email) throws IOException {
        //String filename=dp.getOriginalFilename();
        String filename=email+"_photo";
         
       //String folder_path="C:\\Users\\Administrator\\Desktop\\saidika backend\\saidika_backend\\src\\main\\java\\com\\shaul\\saidikaV3\\profileImages\\";
        String folder_path="src/main/java/com/shaul/saidikaV3/profileImages/";
        String photo_path=folder_path+filename;
        dp.transferTo(new File(photo_path));
        return photo_path;
    }
    public ResponseEntity<?> get_profilePhoto() throws IOException {
        String file_path=authService.getActiveProfile().getProfile_Photo_Path();
        byte[] pp = Files.readAllBytes(new File(file_path).toPath());
        return ResponseEntity.status(200)
                .contentType(MediaType.IMAGE_JPEG)
                .contentType(MediaType.IMAGE_PNG)
                .body(pp);
    }
    public String update_profilePhoto(MultipartFile dppp) throws IOException {
        service_provider fd= find_by_id(authService.getActiveProfile().getId()).orElse(null);
        fd.setProfile_Photo_Path(setProfilePhoto(dppp,fd.getEmail()));

        spr.saveAndFlush(fd);
        return "updated";
    }
    public String update_token(String newToken) throws IOException {
        service_provider kkfd = find_by_id(authService.getActiveProfile().getId()).orElse(null);
        kkfd.setNotif_token(newToken);

        spr.saveAndFlush(kkfd);
        return "updated";
    }
}
