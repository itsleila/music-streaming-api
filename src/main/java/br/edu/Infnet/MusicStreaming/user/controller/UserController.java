package br.edu.Infnet.MusicStreaming.user.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.edu.Infnet.MusicStreaming.user.dto.UserRequestDTO;
import br.edu.Infnet.MusicStreaming.user.dto.UserResponseDTO;
import br.edu.Infnet.MusicStreaming.user.service.UserService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/users")
public class UserController {
  private final UserService userService;

  public UserController(UserService userService) {
    this.userService = userService;
  }

  @PostMapping
  public ResponseEntity<UserResponseDTO> createUser(@Valid @RequestBody UserRequestDTO requestDTO) {
    UserResponseDTO responseDTO = userService.createUser(requestDTO);
    return ResponseEntity.status(HttpStatus.CREATED).body(responseDTO);
  }

  @GetMapping("/{id}")
  public ResponseEntity<UserResponseDTO> getUserById(@PathVariable Long id) {
    UserResponseDTO responseDTO = userService.getUserById(id);
    return ResponseEntity.ok(responseDTO);
  }

  @PutMapping("/{id}")
  public ResponseEntity<UserResponseDTO> updateUser(@PathVariable Long id,
      @Valid @RequestBody UserRequestDTO requestDTO) {
    UserResponseDTO responseDTO = userService.updateUser(id, requestDTO);
    return ResponseEntity.ok(responseDTO);
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
    userService.deleteUser(id);
    return ResponseEntity.noContent().build();
  }

  @GetMapping
  public ResponseEntity<List<UserResponseDTO>> getAllUsers() {
    return ResponseEntity.ok(userService.getAllUsers());
  }
}
