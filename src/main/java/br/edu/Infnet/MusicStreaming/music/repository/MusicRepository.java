package br.edu.Infnet.MusicStreaming.music.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.edu.Infnet.MusicStreaming.music.entity.Music;

public interface MusicRepository extends JpaRepository<Music, Long> {
}
