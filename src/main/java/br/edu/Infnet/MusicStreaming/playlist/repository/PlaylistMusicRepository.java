package br.edu.Infnet.MusicStreaming.playlist.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import br.edu.Infnet.MusicStreaming.playlist.entity.PlaylistMusic;

public interface PlaylistMusicRepository extends JpaRepository<PlaylistMusic, Long> {

  List<PlaylistMusic> findByPlaylistId(Long playlistId);

  Optional<PlaylistMusic> findByPlaylistIdAndMusicId(Long playlistId, Long musicId);
}
