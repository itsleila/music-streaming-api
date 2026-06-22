package br.edu.Infnet.MusicStreaming.payment.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import br.edu.Infnet.MusicStreaming.payment.entity.PaymentMethod;
import br.edu.Infnet.MusicStreaming.user.entity.User;

public interface PaymentMethodRepository extends JpaRepository<PaymentMethod, Long> {
  List<PaymentMethod> findAllByUser(User user);

  Optional<PaymentMethod> findByIdAndUser(Long id, User user);
    Optional<PaymentMethod> findByIdAndUserId(Long id, Long userId);

  boolean existsByUserAndCardNumber(User user, String cardNumber);
}
