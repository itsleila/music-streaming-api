package br.edu.Infnet.MusicStreaming.user.service;

import br.edu.Infnet.MusicStreaming.subscription.service.SubscriptionService;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import br.edu.Infnet.MusicStreaming.user.dto.UserRequestDTO;
import br.edu.Infnet.MusicStreaming.user.dto.UserResponseDTO;
import br.edu.Infnet.MusicStreaming.user.entity.User;
import br.edu.Infnet.MusicStreaming.user.repository.UserRepository;

@Service
public class UserService {
  private final SubscriptionService subscriptionService;
  private final UserRepository userRepository;

  public UserService(UserRepository userRepository, SubscriptionService subscriptionService) {
    this.userRepository = userRepository;
    this.subscriptionService = subscriptionService;
  }

  public User toEntity(UserRequestDTO requestDTO) {
    return User.builder()
        .username(requestDTO.username())
        .email(requestDTO.email())
        .password(requestDTO.password())
        .build();
  }

  public UserResponseDTO toDTO(User user) {
    return new UserResponseDTO(user.getId(), user.getUsername(), user.getEmail(),
        subscriptionService.getCurrentPlan(user));
  }

  private User findUserById(Long id) {
    return userRepository.findById(id)
        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));
  }

  private void verifyEmailAvailable(String email) {
    if (userRepository.existsByEmail(email)) {
      throw new ResponseStatusException(HttpStatus.CONFLICT, "Email already in use");
    }
  }

  private void verifyUsernameAvailable(String username) {
    if (userRepository.existsByUsername(username)) {
      throw new ResponseStatusException(HttpStatus.CONFLICT, "Username already in use");
    }
  }

  public UserResponseDTO createUser(UserRequestDTO requestDTO) {

    verifyEmailAvailable(requestDTO.email());
    verifyUsernameAvailable(requestDTO.username());
    User user = toEntity(requestDTO);
    User savedUser = userRepository.save(user);
    return toDTO(savedUser);
  }

  public UserResponseDTO getUserById(Long id) {
    User user = findUserById(id);
    return toDTO(user);
  }

  public UserResponseDTO updateUser(Long id, UserRequestDTO requestDTO) {
    User user = findUserById(id);
    if (!user.getEmail().equals(requestDTO.email())) {
      verifyEmailAvailable(requestDTO.email());
    }
    if (!user.getUsername().equals(requestDTO.username())) {
      verifyUsernameAvailable(requestDTO.username());
    }
    user.setUsername(requestDTO.username());
    user.setEmail(requestDTO.email());
    user.setPassword(requestDTO.password());

    return toDTO(userRepository.save(user));
  }

  public void deleteUser(Long id) {
    User user = findUserById(id);
    userRepository.delete(user);
  }

  public List<UserResponseDTO> getAllUsers() {
    return userRepository.findAll()
        .stream()
        .map(this::toDTO)
        .toList();
  }

}
