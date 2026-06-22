package br.edu.Infnet.MusicStreaming.music.service;

import java.util.List;

import org.springframework.stereotype.Service;

import br.edu.Infnet.MusicStreaming.music.dto.MusicRequestDTO;
import br.edu.Infnet.MusicStreaming.music.dto.MusicResponseDTO;
import br.edu.Infnet.MusicStreaming.music.entity.Music;
import br.edu.Infnet.MusicStreaming.music.repository.MusicRepository;

@Service
public class MusicService {
  private final MusicRepository musicRepository;

  public MusicService(MusicRepository musicRepository) {
    this.musicRepository = musicRepository;
  }

  public Music toEntity(MusicRequestDTO requestDTO) {
    return Music.builder()
        .title(requestDTO.title())
        .artist(requestDTO.artist())
        .album(requestDTO.album())
        .build();
  }

  public MusicResponseDTO toDTO(Music music) {
    return new MusicResponseDTO(music.getId(), music.getTitle(), music.getArtist(), music.getAlbum());
  }

  public MusicResponseDTO createMusic(MusicRequestDTO requestDTO) {
    Music music = toEntity(requestDTO);
    Music savedMusic = musicRepository.save(music);
    return toDTO(savedMusic);
  }

  public MusicResponseDTO getMusicById(Long id) {
    Music music = musicRepository.findById(id)
        .orElseThrow(() -> new RuntimeException("Music not found"));
    return toDTO(music);
  }

  public void deleteMusic(Long id) {
    if (!musicRepository.existsById(id)) {
      throw new RuntimeException("Music not found");
    }
    musicRepository.deleteById(id);
  }

  public MusicResponseDTO updateMusic(Long id, MusicRequestDTO requestDTO) {
    Music music = musicRepository.findById(id)
        .orElseThrow(() -> new RuntimeException("Music not found"));
    music.setTitle(requestDTO.title());
    music.setArtist(requestDTO.artist());
    music.setAlbum(requestDTO.album());
    Music updatedMusic = musicRepository.save(music);
    return toDTO(updatedMusic);
  }

  public List<MusicResponseDTO> getAllMusics() {
    return musicRepository.findAll().stream()
        .map(this::toDTO)
        .toList();
  }
}
