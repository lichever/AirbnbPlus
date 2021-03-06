package com.example.staybooking.config;

import com.example.staybooking.filter.JwtFilter;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {


  @Autowired
  private DataSource dataSource;

  @Autowired
  private JwtFilter jwtFilter;
  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

  //authorize put more strict url first
  //all endpoint必须写在antMatchers里， 不然默认是internal function
  @Override
  protected void configure(HttpSecurity http) throws Exception {
    http.authorizeHttpRequests()
        .antMatchers(HttpMethod.POST, "/register/*").permitAll()
        .antMatchers(HttpMethod.POST, "/authenticate/*").permitAll()
        .antMatchers("/stays").hasAuthority("ROLE_HOST")
        .antMatchers("/stays/*").hasAuthority("ROLE_HOST")
        .antMatchers("/search").hasAuthority("ROLE_GUEST")
        .antMatchers("/reservations").hasAuthority("ROLE_GUEST")
        .antMatchers("/reservations/*").hasAuthority("ROLE_GUEST")
        .anyRequest()
        .authenticated().and().csrf().disable();

    http
        .sessionManagement()
        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        .and()
        .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);//jwtFilter 不一定排第二，但一定排UsernamePasswordAuthenticationFilter之前

  }

  // authenticate
  @Override
  protected void configure(AuthenticationManagerBuilder auth) throws Exception {
    auth.jdbcAuthentication().dataSource(dataSource)
        .passwordEncoder(passwordEncoder())
        .usersByUsernameQuery("SELECT username, password, enabled FROM user WHERE username = ?")
        .authoritiesByUsernameQuery("SELECT username, authority FROM authority WHERE username = ?");
  }

  @Override
  @Bean
  public AuthenticationManager authenticationManagerBean() throws Exception {
    return super.authenticationManagerBean();
  }



}


