package de.hsrm.mi.swtpro.pflamoehus.security;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.CrossOrigin;

import de.hsrm.mi.swtpro.pflamoehus.user.User;
import de.hsrm.mi.swtpro.pflamoehus.user.UserRepository;

/*
 * SecurityConfiq: certain pages can only be accessed by certain types of user. So in this class we define 
 * who can access which part of the website.
 * 
 * @author Svenja Schenk, Ann-Cathrin Fabian
 * @version 3
 */
@Configuration
@EnableWebSecurity
@CrossOrigin
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    UserDetailService buds;

    /**
     * Implementing the service for encoding some of the given attributes of an
     * user.
     * 
     * @return passwordencoder
     */
    @Bean
    PasswordEncoder getPasswordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }


    /**
     * Defining who can access which part of the website. The different roles are:
     * user, default visitor and worker.
     * 
     * @param http to configure the security for specific http requests
     * @throws Exception if a request doesn't work
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests().antMatchers("/").permitAll()
                .antMatchers("/products").permitAll().antMatchers("/favorites").permitAll().antMatchers("/cart")
                .permitAll().antMatchers("/rooms").permitAll().antMatchers("/console/*").permitAll()
                .antMatchers("/profile").hasRole("USER").and()
                .logout().logoutUrl("/logout").permitAll().and().csrf().disable();

        http.csrf().disable();
        http.headers().frameOptions().disable();
    }

    /**
     * Implementing an AuthenticationManagerBuilder for easy memory authentication.
     * 
     * @param authmanagerbuilder for easy memory authentication
     * @throws Exception if the passwordEncoder isn't working properly
     */
    @Override
    public void configure(AuthenticationManagerBuilder authmanagerbuilder) throws Exception {

        authmanagerbuilder.userDetailsService(buds).passwordEncoder(getPasswordEncoder());

    }

    /**
    * Service class: Here it is possible to control the login of an user. If the email is not found, the user has to register himself first.
    * Otherwise the password and username(email) get controlled and verified.
    */
    @Service
    public class UserDetailService implements UserDetailsService {
        @Autowired
        private UserRepository userRepository;

        @Override
        public UserDetails loadUserByUsername(String email) {
            Optional<User> user = userRepository.findByEmail(email);
            if (user.isEmpty()) {
                throw new UsernameNotFoundException(email);
            }
            return org.springframework.security.core.userdetails.User.withUsername(email)
                    .password(getPasswordEncoder().encode(user.get().getPassword()))
                    .roles("USER") 
                    .build();
        }
    }

}
