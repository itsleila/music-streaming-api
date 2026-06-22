package br.edu.Infnet.MusicStreaming.payment.repository;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import br.edu.Infnet.MusicStreaming.payment.entity.PaymentTransaction;
import br.edu.Infnet.MusicStreaming.user.entity.User;

public interface PaymentTransactionRepository extends JpaRepository<PaymentTransaction, Long> {

  List<PaymentTransaction> findByUserAndTransactionDateAfter(User user, LocalDateTime date);

  List<PaymentTransaction> findByUserAndMerchantAndAmountAndTransactionDateAfter(User user, String merchant,
      BigDecimal amount, LocalDateTime date);
}
