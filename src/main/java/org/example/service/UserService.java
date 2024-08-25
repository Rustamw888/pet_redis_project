package org.example.service;

import org.example.exception.UserNotFoundException;
import org.example.model.User;
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

  public User saveUser(User user) {
    User savedUser = userRepository.save(user);
    redisTemplate.opsForHash().put(USER_CACHE, savedUser.getId(), savedUser);
    return savedUser;
  }

  public Optional<User> getUserById(Long id) {
    User cachedUser = (User) redisTemplate.opsForHash().get(USER_CACHE, id);
    if (cachedUser != null) {
      return Optional.of(cachedUser);
    } else {
      Optional<User> user = userRepository.findById(id);
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