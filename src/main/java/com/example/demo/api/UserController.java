package com.example.demo.api;

import com.example.demo.core.model.User;
import com.example.demo.core.repository.UserRepository;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Api(tags = "users")
@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

  private final UserRepository userRepository;

  @ApiOperation("list")
  @GetMapping
  public Page<User> list(@RequestParam("page") int page, @RequestParam("size") int size) {
    Page<User> resultPage = userRepository.findAll(PageRequest.of(page, size));
    return resultPage;
  }
}
