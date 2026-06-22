package br.edu.Infnet.MusicStreaming.payment.entity;

import java.time.LocalDate;

import br.edu.Infnet.MusicStreaming.user.entity.User;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "payment_methods")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PaymentMethod {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @NotBlank
  @Size(min = 13, max = 19)
  @Column(nullable = false, length = 19)
  private String cardNumber;

  @NotBlank
  @Size(max = 100)
  @Column(nullable = false, length = 100)
  private String holderName;

  @Min(1)
  @Max(12)
  @Column(nullable = false)
  private Integer expirationMonth;

  @Min(2025)
  @Column(nullable = false)
  private Integer expirationYear;

  @Column(nullable = false)
  @Builder.Default
  private boolean active = true;

  @ManyToOne(optional = false)
  @JoinColumn(name = "user_id", nullable = false)
  private User user;

  public boolean isExpired() {
    LocalDate expirationDate = LocalDate.of(expirationYear, expirationMonth, 1)
        .withDayOfMonth(LocalDate.of(expirationYear, expirationMonth, 1).lengthOfMonth());

    return LocalDate.now().isAfter(expirationDate);
  }

  public boolean isValid() {
    return active && !isExpired();
  }

  public String getLastFourDigits() {
    if (cardNumber == null || cardNumber.length() < 4) {
      return "";
    }
    return cardNumber.substring(cardNumber.length() - 4);
  }
}
