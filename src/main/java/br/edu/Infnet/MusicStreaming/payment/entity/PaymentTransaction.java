package br.edu.Infnet.MusicStreaming.payment.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import br.edu.Infnet.MusicStreaming.plan.entity.Plan;
import br.edu.Infnet.MusicStreaming.user.entity.User;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "payment_transactions")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PaymentTransaction {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  @ManyToOne
  private User user;
  @ManyToOne
  private PaymentMethod paymentMethod;
  @ManyToOne
  private Plan plan;
  private String merchant;
  private BigDecimal amount;
  private LocalDateTime transactionDate;

  @Enumerated(EnumType.STRING)
  private TransactionStatus status;
}