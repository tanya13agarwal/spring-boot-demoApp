package com.telusko.demoApp.config;


//import com.telusko.demoApp.service.MyUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private JwtFilter jwtFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        //To disable CSRF token
        http.csrf(customizer -> customizer.disable());

        //To Enable authentication (Make it compulsory)
        //Agar username and pass bhej bhi denge still kuch nhi hoga
        //we need a form login to get authorized
        http.authorizeHttpRequests(requests -> requests
                .requestMatchers( "/login" , "hello" )//In Routes prr authorization nhi lagega
                .permitAll()
                //Baaki sb prr authorization mein username and password bhejna padega
                .anyRequest().authenticated());

        //Form login enable krne ke liye (Web Browser)
//        http.formLogin(Customizer.withDefaults());

        //Form Login anble (Post Man)
        http.httpBasic(Customizer.withDefaults());

        //To make out http stateless
        http.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
        //When we use this we have to disable form login becoz hrr new session ke liye dobaara
        //login krna padta hai and hmm lo hrr baar ek new session generate kee rhe hai
        //toh login page se aage kabhi badh hi nhi paayenge


        //BUILDER PATTERN -->
//       return http.csrf(customizer -> customizer.disable())
        //            .authorizeHttpRequests(requests -> requests.anyRequest().authenticated())
        //            .formLogin(Customizer.withDefaults())
        //            .httpBasic(Customizer.withDefaults())
        //            .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
        //            .build();


        //JWT token aayega requests ke saath to usko decode krna padega
        http.addFilterBefore(jwtFilter , UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    //Hardcore values deta hai so, we cannnot use it
//    @Bean
//    public UserDetailsService userDetailsService() {
//        UserDetails user1 = User
//                .withUsername("Divy")
//                .password("Divyan")
//                .roles("USER")
//                .build();
//
//        return new InMemoryUserDetailsManager(user1);
//    }

    //To acces user from database we create a new Bean
    @Bean
    public AuthenticationProvider authenticationProvider() {
        // In order to communicate with Database we use it
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        //Abhi hmmlog password encode nhi krr rhe hai
//        provider.setPasswordEncoder(NoOpPasswordEncoder.getInstance());

        //When we use Bcrypt to hash password
        provider.setPasswordEncoder(new BCryptPasswordEncoder(12));
        provider.setUserDetailsService(userDetailsService);
        return provider;
    }


    //For Jwt we create AuthenticationManager
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }
}
