package br.edu.Infnet.MusicStreaming.favorite.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import br.edu.Infnet.MusicStreaming.favorite.entity.FavoriteMusic;

public interface FavoriteMusicRepository extends JpaRepository<FavoriteMusic, Long> {

  List<FavoriteMusic> findByUserId(Long userId);

  boolean existsByUserIdAndMusicId(Long userId, Long musicId);

  Optional<FavoriteMusic> findByUserIdAndMusicId(Long userId, Long musicId);
}
