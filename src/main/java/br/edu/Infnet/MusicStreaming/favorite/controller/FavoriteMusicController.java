package br.edu.Infnet.MusicStreaming.favorite.controller;

import br.edu.Infnet.MusicStreaming.favorite.dto.FavoriteMusicResponseDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import br.edu.Infnet.MusicStreaming.favorite.dto.FavoriteMusicRequestDTO;
import br.edu.Infnet.MusicStreaming.favorite.service.FavoriteMusicService;

import java.util.List;

@RestController
@RequestMapping("/users/{userId}/favorites")
public class FavoriteMusicController {
  private final FavoriteMusicService favoriteService;

  public FavoriteMusicController(FavoriteMusicService service) {
    this.favoriteService = service;
  }

  @PostMapping("/{musicId}")
  public ResponseEntity<Void> favorite(@PathVariable Long userId, @PathVariable Long musicId) {
    favoriteService.favorite(new FavoriteMusicRequestDTO(userId, musicId));
    return ResponseEntity.ok().build();
  }

  @DeleteMapping("/{musicId}")
  public ResponseEntity<Void> unfavorite(@PathVariable Long userId, @PathVariable Long musicId) {
    favoriteService.unfavorite(new FavoriteMusicRequestDTO(userId, musicId));
    return ResponseEntity.noContent().build();
  }

  @GetMapping()
  public ResponseEntity<List<FavoriteMusicResponseDTO>> getFavorites(@PathVariable Long userId) {
      return ResponseEntity.ok(favoriteService.getFavoriteMusic(userId));
    }
}
