package br.edu.Infnet.MusicStreaming.favorite.service;

import br.edu.Infnet.MusicStreaming.favorite.dto.FavoriteMusicResponseDTO;
import org.springframework.stereotype.Service;

import br.edu.Infnet.MusicStreaming.favorite.dto.FavoriteMusicRequestDTO;
import br.edu.Infnet.MusicStreaming.favorite.entity.FavoriteMusic;
import br.edu.Infnet.MusicStreaming.favorite.repository.FavoriteMusicRepository;
import br.edu.Infnet.MusicStreaming.music.entity.Music;
import br.edu.Infnet.MusicStreaming.music.repository.MusicRepository;
import br.edu.Infnet.MusicStreaming.user.entity.User;
import br.edu.Infnet.MusicStreaming.user.repository.UserRepository;

import java.util.List;

@Service
public class FavoriteMusicService {
  private final UserRepository userRepository;
  private final MusicRepository musicRepository;
  private final FavoriteMusicRepository repository;

  public FavoriteMusicService(UserRepository userRepository, MusicRepository musicRepository,
      FavoriteMusicRepository repository) {
    this.userRepository = userRepository;
    this.musicRepository = musicRepository;
    this.repository = repository;
  }

  public void favorite(FavoriteMusicRequestDTO dto) {
    if (repository.existsByUserIdAndMusicId(dto.userId(), dto.musicId())) {
      return;
    }

    User user = userRepository.findById(dto.userId()).orElseThrow();
    Music music = musicRepository.findById(dto.musicId()).orElseThrow();
    FavoriteMusic favorite = FavoriteMusic.builder().user(user).music(music).build();

    repository.save(favorite);
  }

  public List<FavoriteMusicResponseDTO> getFavoriteMusic(Long userId) {
      return repository.findByUserId(userId).stream().map(favorite -> new FavoriteMusicResponseDTO(favorite.getMusic().getId(), favorite.getMusic().getTitle(),  favorite.getMusic().getArtist())).toList();
    }

  public void unfavorite(FavoriteMusicRequestDTO dto) {
    repository.findByUserIdAndMusicId(dto.userId(), dto.musicId()).ifPresent(repository::delete);
  }

}
