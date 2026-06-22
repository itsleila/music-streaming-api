package br.edu.Infnet.MusicStreaming.favorite.entity;

import br.edu.Infnet.MusicStreaming.music.entity.Music;
import br.edu.Infnet.MusicStreaming.user.entity.User;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "favorite_musics")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FavoriteMusic {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  @ManyToOne
  private User user;
  @ManyToOne
  private Music music;
}
