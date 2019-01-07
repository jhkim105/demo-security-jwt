package com.example.demo.api.security;

public class AuthTokenInvalidException extends RuntimeException {

  private static final long serialVersionUID = -8392066122824853909L;

  public AuthTokenInvalidException(Throwable cause) {
    super(cause);
  }
}
