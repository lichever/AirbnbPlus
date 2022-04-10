package com.example.staybooking.filter;

import java.io.IOException;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
public class CorsFilter extends OncePerRequestFilter {

  @Override
  protected void doFilterInternal(HttpServletRequest httpServletRequest,
      HttpServletResponse httpServletResponse, FilterChain filterChain)
      throws ServletException, IOException {
    httpServletResponse.setHeader("Access-Control-Allow-Origin", "*");
    httpServletResponse.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, DELETE");

    //Authorization,Content-Type 这2个header是optional的, 所以要加上去
    httpServletResponse.setHeader("Access-Control-Allow-Headers", "Authorization, Content-Type");

    if ("OPTIONS".equalsIgnoreCase(httpServletRequest.getMethod())) {
      httpServletResponse.setStatus(HttpServletResponse.SC_OK);//preflight就是直接返回
    } else {
      filterChain.doFilter(httpServletRequest, httpServletResponse);
    }
  }
}
