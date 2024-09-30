package com.shaul.saidikaV3.configs;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import com.shaul.saidikaV3.auth.JWTTOkenFilter;
import com.shaul.saidikaV3.auth.saidika_finders_UserDetails.finder_DetailsService;
import com.shaul.saidikaV3.auth.saidika_providers_UserDetails.provider_DetailsService;
import com.shaul.saidikaV3.configs.AuthenticationManagers.saidika_findersAuthenticationManager;
import com.shaul.saidikaV3.configs.AuthenticationManagers.saidika_provider_AuthenticationManager;

@EnableWebSecurity
@EnableMethodSecurity
@Configuration
public class SecurityConfig {

    public static String jwtSecretKey="saidikasaidikasaidikasaidikasaidikasaidikasaidikasaidikasaidikasaidikasaidika";

    @Autowired
    private JWTTOkenFilter jwtTokenFilter;

   

    @Autowired
    private finder_DetailsService finder_detailsService;
    @Autowired
    private provider_DetailsService provider_detailsService;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
       http.csrf(AbstractHttpConfigurer::disable)
               .cors(httpSecurityCorsConfigurer -> {
                   CorsConfiguration configuration = new CorsConfiguration();
                   configuration.applyPermitDefaultValues();
                   configuration.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS", "HEAD"));
                   UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
                   source.registerCorsConfiguration("/**",configuration);
                   httpSecurityCorsConfigurer.configurationSource(source);
               }).
               authorizeHttpRequests(authorizationManagerRequestMatcherRegistry -> {
                   authorizationManagerRequestMatcherRegistry.requestMatchers(
                    
                                   "/api/v1/finder/login**"
                                   ,"/api/v1/provider/login**"
                                   ,"/api/v1/provider/register**"
                                    ,"/api/v1/finder/register**"
                                     ,"/api/v1/provider/activeUser"
                                     ,"/api/v1/services/*"
                                     ,"/api/v1/finder/activeUser"
                                     ,"/api/v1/comments/*"
                                     ,"/active_User"
                           ).permitAll()
                           //.requestMatchers("/api/v1/provider/activeUser","/api/v1/services/get_services_offered","/api/v1/services/add_new_service").hasAuthority("PROVIDER")
                           .requestMatchers("/api/v1/*").authenticated()
                           .anyRequest().permitAll();
               }).sessionManagement(httpSecuritySessionManagementConfigurer -> {
                   httpSecuritySessionManagementConfigurer.sessionCreationPolicy(SessionCreationPolicy.STATELESS);
               });

       http.addFilterBefore(jwtTokenFilter, UsernamePasswordAuthenticationFilter.class);
    

       return http.build();
    }




  @Bean
  public PasswordEncoder passwordEncoder()
    {
        return new BCryptPasswordEncoder();
    }



   @Bean
   public AuthenticationProvider finderAuthenticationProvider(){
       DaoAuthenticationProvider authenticationProvider=new DaoAuthenticationProvider();
       authenticationProvider.setUserDetailsService(finder_detailsService);
       authenticationProvider.setPasswordEncoder(passwordEncoder());
       return authenticationProvider;
   }


    
    @Primary
    @Bean()
    public AuthenticationProvider providerAuthenticationProvider(){
        DaoAuthenticationProvider pauthenticationProvider=new DaoAuthenticationProvider();
        pauthenticationProvider.setUserDetailsService(provider_detailsService);
        pauthenticationProvider.setPasswordEncoder(passwordEncoder());
        return pauthenticationProvider;
    }





  
    @Bean(name ="enf")
    public saidika_findersAuthenticationManager finders_AuthenticationManager(AuthenticationConfiguration authenticationConfiguration)
            throws Exception {
        return new saidika_findersAuthenticationManager(finderAuthenticationProvider());
    }

    @Primary
    @Bean(name="prov")
    public saidika_provider_AuthenticationManager providers_AuthenticationManager(AuthenticationConfiguration authenticationConfiguration)
            throws Exception {
        return new saidika_provider_AuthenticationManager(providerAuthenticationProvider());
    }

   
}
