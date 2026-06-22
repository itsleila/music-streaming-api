package br.edu.Infnet.MusicStreaming.playlist.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.edu.Infnet.MusicStreaming.playlist.entity.Playlist;

public interface PlaylistRepository extends JpaRepository<Playlist, Long> {
}
