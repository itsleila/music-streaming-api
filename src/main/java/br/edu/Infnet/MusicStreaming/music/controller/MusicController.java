package br.edu.Infnet.MusicStreaming.music.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import br.edu.Infnet.MusicStreaming.music.dto.MusicRequestDTO;
import br.edu.Infnet.MusicStreaming.music.dto.MusicResponseDTO;
import br.edu.Infnet.MusicStreaming.music.service.MusicService;

@RestController
@RequestMapping("/musics")
public class MusicController {
  private final MusicService service;

  public MusicController(MusicService service) {
    this.service = service;
  }

  @PostMapping
  public ResponseEntity<MusicResponseDTO> create(@RequestBody MusicRequestDTO dto) {
    return ResponseEntity.ok(service.createMusic(dto));
  }

  @GetMapping
  public ResponseEntity<List<MusicResponseDTO>> findAll() {
    return ResponseEntity.ok(service.getAllMusics());
  }

  @GetMapping("/{id}")
  public ResponseEntity<MusicResponseDTO> findById(@PathVariable Long id) {
      MusicResponseDTO musicResponseDTO = service.getMusicById(id);
      return ResponseEntity.ok(musicResponseDTO);

  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deleteById(@PathVariable Long id) {
      service.deleteMusic(id);
      return ResponseEntity.ok().build();
  }

  @PutMapping("/{id}")
  public ResponseEntity<MusicResponseDTO> update(@PathVariable Long id, @RequestBody MusicRequestDTO dto) {
      MusicResponseDTO musicResponseDTO = service.updateMusic(id, dto);
      return ResponseEntity.ok(musicResponseDTO);
  }
}
