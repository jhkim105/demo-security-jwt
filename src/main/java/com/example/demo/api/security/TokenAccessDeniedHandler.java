package com.example.demo.api.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class TokenAccessDeniedHandler implements AccessDeniedHandler {

  @Autowired
  private TokenAuthenticationHandler tokenAuthenticationHandler;

  @Override
  public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) {
    tokenAuthenticationHandler.handleAccessDenied(request, response);
  }
}
