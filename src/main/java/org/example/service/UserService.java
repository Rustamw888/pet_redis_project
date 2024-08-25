package org.example.service;

import org.example.exception.UserNotFoundException;
import org.example.model.UserData;
import org.example.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

  @Autowired
  private UserRepository userRepository;

  @Autowired
  private RedisTemplate<String, Object> redisTemplate;

  private static final String USER_CACHE = "USER";

  public UserData saveUser(UserData userData) {
    UserData savedUserData = userRepository.save(userData);
    redisTemplate.opsForHash().put(USER_CACHE, savedUserData.getId(), savedUserData);
    return savedUserData;
  }

  public Optional<UserData> getUserById(Long id) {
    UserData cachedUserData = (UserData) redisTemplate.opsForHash().get(USER_CACHE, id);
    if (cachedUserData != null) {
      return Optional.of(cachedUserData);
    } else {
      Optional<UserData> user = userRepository.findById(id);
      user.ifPresent(value -> redisTemplate.opsForHash().put(USER_CACHE, id, value));
      return Optional.ofNullable(
          user.orElseThrow(() -> new UserNotFoundException("User not found with id: " + id)));
    }
  }

  public void deleteUserById(Long id) {
    userRepository.deleteById(id);
    redisTemplate.opsForHash().delete(USER_CACHE, id);
  }
}