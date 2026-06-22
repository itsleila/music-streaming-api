package br.edu.Infnet.MusicStreaming.subscription.controller;

import br.edu.Infnet.MusicStreaming.subscription.dto.CreateSubscriptionDTO;
import br.edu.Infnet.MusicStreaming.subscription.dto.SubscriptionResponseDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import br.edu.Infnet.MusicStreaming.subscription.dto.SubscriptionSummaryDTO;
import br.edu.Infnet.MusicStreaming.subscription.entity.Subscription;
import br.edu.Infnet.MusicStreaming.subscription.service.SubscriptionService;
import br.edu.Infnet.MusicStreaming.user.entity.User;
import br.edu.Infnet.MusicStreaming.user.repository.UserRepository;

@RestController
@RequestMapping("/users/{userId}/subscription")
public class SubscriptionController {
  private final SubscriptionService subscriptionService;
  private final UserRepository userRepository;

  public SubscriptionController(SubscriptionService subscriptionService, UserRepository userRepository) {
    this.subscriptionService = subscriptionService;
    this.userRepository = userRepository;
  }

  @GetMapping
  public ResponseEntity<SubscriptionSummaryDTO> getCurrentPlan(@PathVariable Long userId) {
    User user = findUser(userId);
    return ResponseEntity.ok(subscriptionService.getCurrentPlan(user));
  }

  @PostMapping
  public ResponseEntity<SubscriptionResponseDTO> subscribe(@PathVariable Long userId, @RequestBody CreateSubscriptionDTO dto) {
    User user = findUser(userId);

    Subscription subscription = subscriptionService.subscribe(user, dto.planId(), dto.paymentMethodId());
    return ResponseEntity.status(HttpStatus.CREATED).body(subscriptionService.toDTO(subscription));
  }

  @DeleteMapping
  public ResponseEntity<Void> cancelSubscription(@PathVariable Long userId) {
    User user = findUser(userId);
    subscriptionService.cancelSubscription(user);
    return ResponseEntity.noContent().build();
  }

  @PutMapping
  public ResponseEntity<SubscriptionResponseDTO> changePlan(@PathVariable Long userId, @RequestBody CreateSubscriptionDTO dto) {
    User user = findUser(userId);

    Subscription subscription = subscriptionService.changePlan(user, dto.planId(), dto.paymentMethodId());
    return ResponseEntity.ok(subscriptionService.toDTO(subscription));
}

  private User findUser(Long userId) {
    return userRepository.findById(userId)
        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));
  }

}
