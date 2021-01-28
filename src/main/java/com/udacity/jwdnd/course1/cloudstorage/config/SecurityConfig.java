package com.udacity.jwdnd.course1.cloudstorage.config;

import com.udacity.jwdnd.course1.cloudstorage.services.AuthenticationService;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

/**
 * restrict unauthorized users from accessing pages other than
 * login and signup pages
 * https://docs.spring.io/spring-security/site/docs/current/api/org/springframework/security/config/annotation/web/builders/HttpSecurity.html
 */

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    // field:
    // use AuthenticationService for security:
    private AuthenticationService authenticationService;

    // constructor:
    public SecurityConfig(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    // configure Spring Authentication Manager
    // take AuthenticationManagerBuilder class supplied by Spring:
    @Override
    protected void configure(AuthenticationManagerBuilder auth) {
        auth.authenticationProvider(this.authenticationService);
    }

    // use the security configuration to override the default login page:
    // HttpSecurity: configure web based security for specific http requests
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests() // allow restricting access based on .antMatcher
                .antMatchers("/signup","/login", "/css/**", "/js/**").permitAll()
                .anyRequest().authenticated()
                .and()
                .logout() // provides logout support
                    .deleteCookies("remove") // delete cookies
                    .invalidateHttpSession(true) // session invalidated after logout
                    .logoutSuccessUrl("/login?logout"); // after successfully logout, redirect user to /login?logout

        // handling calls to the /login and /logout endpoints
        // use the security configuration to override the default login page with one of your own
        http.formLogin()
                .loginPage("/login")
                .permitAll()
                .defaultSuccessUrl("/home", true);

    }
}