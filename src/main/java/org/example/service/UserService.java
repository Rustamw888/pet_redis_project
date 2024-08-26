package org.example.service;

import org.example.model.UserData;
import org.example.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import java.util.List;
import java.util.Optional;

//@Service
//public class UserService {
//
//  @Autowired
//  private UserRepository userRepository;
//
//  @Value("${spring.redis.host}")
//  private String redisHost;
//
//  @Value("${spring.redis.port}")
//  private int redisPort;
//
//  // Используем JedisPool для управления соединением с Redis
//  private JedisPool jedisPool;
//
//  public UserService() {
//    // Конфигурация пула соединений для Redis
//    JedisPoolConfig poolConfig = new JedisPoolConfig();
//    poolConfig.setMaxTotal(10);
//    poolConfig.setMaxIdle(5);
//    poolConfig.setMinIdle(1);
//    poolConfig.setTestOnBorrow(true);
//    poolConfig.setTestOnReturn(true);
//
//    // Создание пула соединений с Redis
//    jedisPool = new JedisPool(poolConfig, redisHost, redisPort);
//  }
//
//  public UserData saveUser(UserData user) {
//    UserData savedUser = userRepository.save(user);
//    // Кэширование имени пользователя в Redis
//    try (Jedis jedis = jedisPool.getResource()) {
//      jedis.set("user:" + savedUser.getId(), savedUser.getName());
//    } catch (Exception e) {
//      // Redis недоступен, не кэшируем
//      System.err.println("Redis error: 1" + e.getMessage());
//    }
//    return savedUser;
//  }
//
//  public Optional<UserData> getUser(Long id) {
//    try (Jedis jedis = jedisPool.getResource()) {
//      // Пробуем получить имя пользователя из Redis
//      String cachedUserName = jedis.get("user:" + id);
//      if (cachedUserName != null) {
//        UserData cachedUser = new UserData();
//        cachedUser.setId(id);
//        cachedUser.setName(cachedUserName);
//        return Optional.of(cachedUser);
//      }
//    } catch (Exception e) {
//      // Redis недоступен, используем базу данных
//      System.err.println("Redis error: 2" + e.getMessage());
//    }
//
//    // Получение пользователя из базы данных
//    return userRepository.findById(id);
//  }
//
//  public List<UserData> getAllUsers() {
//    return userRepository.findAll();
//  }
//
//  public void deleteUser(Long id) {
//    try (Jedis jedis = jedisPool.getResource()) {
//      // Удаление данных из кэша
//      jedis.del("user:" + id);
//    } catch (Exception e) {
//      System.err.println("Redis error: 3" + e.getMessage());
//    }
//
//    userRepository.deleteById(id);
//  }
//}

@Service
public class UserService {

  @Autowired
  private UserRepository userRepository;

  public UserData saveUser(UserData user) {
    return userRepository.save(user);
  }

  public Optional<UserData> getUser(Long id) {
    return userRepository.findById(id);
  }

  public List<UserData> getAllUsers() {
    return userRepository.findAll();
  }

  public void deleteUser(Long id) {
    userRepository.deleteById(id);
  }
}
