package br.edu.Infnet.MusicStreaming.subscription.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import br.edu.Infnet.MusicStreaming.subscription.entity.Subscription;
import br.edu.Infnet.MusicStreaming.user.entity.User;

public interface SubscriptionRepository extends JpaRepository<Subscription, Long> {
  List<Subscription> findAllByUser(User user);

  List<Subscription> findAllByUserOrderByStartDateDesc(User user);

}
