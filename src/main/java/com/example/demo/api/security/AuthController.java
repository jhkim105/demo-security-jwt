package com.example.demo.api.security;

import com.example.demo.core.model.User;
import com.example.demo.core.repository.UserRepository;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.ZonedDateTime;
import java.util.Date;
import java.util.NoSuchElementException;

@Api(tags = "auth")
@RestController
@RequestMapping("/auth")
public class AuthController  {


  @Autowired
  private JwtUtils jwtUtils;

  @Autowired
  private UserRepository userRepository;

  @Autowired
  private PasswordEncoder passwordEncoder;

  @GetMapping("/token")
  public AuthTokenResponseVO token(String username, String password) {
    User user = userRepository.findByUsername(username);
    if (user == null) {
      throw new NoSuchElementException(String.format("[%s] not found", username));
    }

    if (!passwordEncoder.matches(password, user.getPassword())) {
      throw new IllegalArgumentException("password not matched.");
    }

    AuthUser authUser = new AuthUser(user);
    ZonedDateTime today = ZonedDateTime.now();
    String accessToken = jwtUtils.generateToken(authUser, Date.from(today.plusDays(JwtConfig.AUTH_TOKEN_EXPIRE_DAY).toInstant()));
    String refreshToken = jwtUtils.generateToken(authUser, Date.from(today.plusDays(JwtConfig.REFRESH_TOKEN_EXPIRE_DAY).toInstant()));
    AuthTokenResponseVO responseVO = new AuthTokenResponseVO(accessToken, refreshToken);
    return responseVO;
  }

  @GetMapping("/check_token")
  public void checkToken(@RequestParam String token) {
    jwtUtils.checkToken(token);
  }

  @GetMapping("/refresh_token")
  public AuthRefreshTokenResponseVO refreshToken(@RequestParam String refreshToken) {
    AuthUser authUser = jwtUtils.parseToken(refreshToken);
    String accessToken = jwtUtils.generateToken(authUser, Date.from(ZonedDateTime.now().plusDays(JwtConfig.REFRESH_TOKEN_EXPIRE_DAY).toInstant()));
    AuthRefreshTokenResponseVO responseVO = new AuthRefreshTokenResponseVO(accessToken);
    return responseVO;
  }

}
