package br.edu.Infnet.MusicStreaming.playlist.entity;

import br.edu.Infnet.MusicStreaming.music.entity.Music;
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
@Table(name = "playlist_musics")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PlaylistMusic {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  @ManyToOne
  private Playlist playlist;
  @ManyToOne
  private Music music;
}
