package com.example.staybooking.filter;

import com.example.staybooking.repository.AuthorityRepository;
import com.example.staybooking.util.JwtUtil;
import java.io.IOException;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.filter.OncePerRequestFilter;

public class JwtFilter extends OncePerRequestFilter {

  private final String HEADER = "Authorization";
  private final String PREFIX = "Bearer ";
  private AuthorityRepository authorityRepository;
  private JwtUtil jwtUtil;

  @Autowired
  public JwtFilter(AuthorityRepository authorityRepository, JwtUtil jwtUtil) {
    this.authorityRepository = authorityRepository;
    this.jwtUtil = jwtUtil;
  }

  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
      FilterChain filterChain) throws ServletException, IOException {
    // filter by token, for invalid token return error, otherwise next filter


     filterChain.doFilter(request, response);
  }
}
