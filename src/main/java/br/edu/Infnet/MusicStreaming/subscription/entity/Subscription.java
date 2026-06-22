package br.edu.Infnet.MusicStreaming.subscription.entity;

import java.time.LocalDate;

import br.edu.Infnet.MusicStreaming.plan.entity.Plan;
import br.edu.Infnet.MusicStreaming.user.entity.User;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "subscriptions")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Subscription {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  @Column(nullable = false)
  private LocalDate startDate;
  @Column(nullable = true)
  private LocalDate endDate;
  @ManyToOne
  @JoinColumn(name = "user_id", nullable = false)
  private User user;
  @ManyToOne
  @JoinColumn(name = "plan_id", nullable = false)
  private Plan plan;

  public boolean isActive() {
    return endDate == null || !endDate.isBefore(LocalDate.now());
  }

}
