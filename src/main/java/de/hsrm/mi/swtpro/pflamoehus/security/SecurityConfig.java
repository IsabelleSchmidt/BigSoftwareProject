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

import de.hsrm.mi.swtpro.pflamoehus.user.User;
import de.hsrm.mi.swtpro.pflamoehus.user.UserRepository;

@Configuration @EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter{

    @Autowired BenutzerUserDetailService buds;

    
    /** 
     * @return PasswordEncoder
     */
    @Bean PasswordEncoder getPasswordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }
    
    
    /** 
     * @param http
     * @throws Exception
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception{
        http.authorizeRequests()
            .antMatchers("/").permitAll()
            .antMatchers("/console/**").permitAll()
            .antMatchers("/h2-console/**").permitAll()
            .antMatchers("/products").permitAll()
            .antMatchers("/favorites").permitAll()
            .antMatchers("/cart").permitAll()
            .antMatchers("/rooms").permitAll()
            .antMatchers("/console/*").permitAll()
            .antMatchers("/profile").hasRole("USER")
        .and()
            .formLogin()
            .loginPage("/login")
            .permitAll()
        .and()
            .logout()
            .logoutUrl("/logout")
            .permitAll()
        .and()
            .csrf()
            .disable();

        http.csrf().disable();
        http.headers().frameOptions().disable();
    }

    
    /** 
     * @param authmanagerbuilder
     * @throws Exception
     */
    @Override
    public void configure(AuthenticationManagerBuilder authmanagerbuilder) throws Exception{

        authmanagerbuilder
            .userDetailsService(buds)
            .passwordEncoder(getPasswordEncoder());
    
    }

    @Service
    public class BenutzerUserDetailService implements UserDetailsService {
        @Autowired private UserRepository userRepository;
        @Override
        public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
            Optional<User> user = userRepository.findByEmail(email);
            if (user.isEmpty()) { 
                throw new UsernameNotFoundException(email); }
            return org.springframework.security.core.userdetails.User 
                .withUsername(email)
                .password(getPasswordEncoder().encode(user.get().getPassword())) // falls in DB encoded gespeichert 
                .roles("USER")  //Muss später noch geschaut werden, wie man dann eventuelle Lagerarbeiter etc. entsprechend setzt. Nach aktuellem Stand aber noch nicht gebraucht
                .build();
    }
}

    
}
