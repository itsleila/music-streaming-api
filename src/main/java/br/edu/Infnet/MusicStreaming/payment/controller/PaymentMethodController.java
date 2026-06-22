package br.edu.Infnet.MusicStreaming.payment.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import br.edu.Infnet.MusicStreaming.payment.dto.PaymentMethodRequestDTO;
import br.edu.Infnet.MusicStreaming.payment.dto.PaymentMethodResponseDTO;
import br.edu.Infnet.MusicStreaming.payment.entity.PaymentMethod;
import br.edu.Infnet.MusicStreaming.payment.service.PaymentMethodService;
import br.edu.Infnet.MusicStreaming.user.entity.User;
import br.edu.Infnet.MusicStreaming.user.repository.UserRepository;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/users/{userId}/payment-methods")
public class PaymentMethodController {
  private final PaymentMethodService paymentMethodService;
  private final UserRepository userRepository;

  public PaymentMethodController(PaymentMethodService paymentMethodService, UserRepository userRepository) {
    this.paymentMethodService = paymentMethodService;
    this.userRepository = userRepository;
  }

  @PostMapping
  public ResponseEntity<PaymentMethodResponseDTO> createCard(@PathVariable Long userId,
      @Valid @RequestBody PaymentMethodRequestDTO dto) {
    User user = findUser(userId);
    PaymentMethod paymentMethod = paymentMethodService.create(user, dto);

    return ResponseEntity.status(HttpStatus.CREATED).body(paymentMethodService.toDTO(paymentMethod));
  }

  @GetMapping
  public ResponseEntity<List<PaymentMethodResponseDTO>> getCards(@PathVariable Long userId) {
    User user = findUser(userId);
    return ResponseEntity.ok(paymentMethodService.getAllByUser(user));
  }

  @GetMapping("/{paymentMethodId}")
  public ResponseEntity<PaymentMethodResponseDTO> getCard(@PathVariable Long userId,
      @PathVariable Long paymentMethodId) {
    User user = findUser(userId);
    return ResponseEntity.ok(paymentMethodService.getDTOById(paymentMethodId, user));
  }

  @PatchMapping("/{paymentMethodId}/activate")
  public ResponseEntity<PaymentMethodResponseDTO> activate(@PathVariable Long userId,
      @PathVariable Long paymentMethodId) {
    User user = findUser(userId);
    return ResponseEntity.ok(paymentMethodService.activate(paymentMethodId, user));
  }

  @PatchMapping("/{paymentMethodId}/deactivate")
  public ResponseEntity<PaymentMethodResponseDTO> deactivate(@PathVariable Long userId,
      @PathVariable Long paymentMethodId) {
    User user = findUser(userId);
    return ResponseEntity.ok(paymentMethodService.deactivate(paymentMethodId, user));
  }

  private User findUser(Long userId) {
    return userRepository.findById(userId)
        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));
  }
}
