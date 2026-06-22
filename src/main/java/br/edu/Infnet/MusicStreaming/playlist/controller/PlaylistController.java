package br.edu.Infnet.MusicStreaming.playlist.controller;

import java.util.List;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.edu.Infnet.MusicStreaming.music.entity.Music;
import br.edu.Infnet.MusicStreaming.playlist.dto.PlaylistRequestDTO;
import br.edu.Infnet.MusicStreaming.playlist.dto.PlaylistResponseDTO;
import br.edu.Infnet.MusicStreaming.playlist.service.PlaylistService;

@RestController
@RequestMapping("/playlists")
public class PlaylistController {

  private final PlaylistService service;

  public PlaylistController(PlaylistService service) {
    this.service = service;
  }

  @PostMapping
  public ResponseEntity<PlaylistResponseDTO> create(@RequestBody PlaylistRequestDTO dto) {
    return ResponseEntity.ok(service.createPlaylist(dto));
  }

  @GetMapping("/{id}")
  public ResponseEntity<PlaylistResponseDTO> getById(@PathVariable Long id) {
    return ResponseEntity.ok(service.getPlaylistById(id));
  }

  @GetMapping
  public ResponseEntity<List<PlaylistResponseDTO>> getAll() {
    return ResponseEntity.ok(service.getAllPlaylists());
  }

  @PutMapping("/{id}")
  public ResponseEntity<PlaylistResponseDTO> update(@PathVariable Long id, @RequestBody PlaylistRequestDTO dto) {
    return ResponseEntity.ok(service.updatePlaylist(id, dto));
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> delete(@PathVariable Long id) {
    service.deletePlaylist(id);
    return ResponseEntity.noContent().build();
  }

  @PostMapping("/{playlistId}/musics/{musicId}")
  public ResponseEntity<Void> addMusic(@PathVariable Long playlistId, @PathVariable Long musicId) {
    service.addMusic(playlistId, musicId);

    return ResponseEntity.ok().build();
  }

  @DeleteMapping("/{playlistId}/musics/{musicId}")
  public ResponseEntity<Void> removeMusic(@PathVariable Long playlistId, @PathVariable Long musicId) {
    service.removeMusic(playlistId, musicId);
    return ResponseEntity.noContent().build();
  }

  @GetMapping("/{playlistId}/musics")
  public ResponseEntity<List<Music>> getMusics(@PathVariable Long playlistId) {

    return ResponseEntity.ok(service.getMusicsInPlaylist(playlistId));
  }
}
