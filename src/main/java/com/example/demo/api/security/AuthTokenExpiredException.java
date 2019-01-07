package com.example.demo.api.security;

public class AuthTokenExpiredException extends RuntimeException {

  private static final long serialVersionUID = -3088334246568145943L;

  public AuthTokenExpiredException(Throwable cause) {
    super(cause);
  }
}
