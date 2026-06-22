package br.edu.Infnet.MusicStreaming.user.entity;

import java.util.ArrayList;
import java.util.List;

import br.edu.Infnet.MusicStreaming.favorite.entity.FavoriteMusic;
import br.edu.Infnet.MusicStreaming.payment.entity.PaymentMethod;
import br.edu.Infnet.MusicStreaming.playlist.entity.Playlist;
import br.edu.Infnet.MusicStreaming.subscription.entity.Subscription;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false, unique = true, length = 20)
  private String username;

  @Column(nullable = false, unique = true)
  private String email;

  @Column(nullable = false)
  private String password;

  @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
  @Builder.Default
  private List<Subscription> subscriptions = new ArrayList<>();

  @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
  @Builder.Default
  private List<PaymentMethod> paymentMethods = new ArrayList<>();

  @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
  @Builder.Default
  private List<Playlist> playlists =new ArrayList<>();

  @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
  @Builder.Default
  private List<FavoriteMusic> favoriteSongs = new ArrayList<>();

}