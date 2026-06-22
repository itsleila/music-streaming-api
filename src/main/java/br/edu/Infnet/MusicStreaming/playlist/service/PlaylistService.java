package br.edu.Infnet.MusicStreaming.playlist.service;

import br.edu.Infnet.MusicStreaming.playlist.repository.PlaylistMusicRepository;

import java.util.List;

import org.springframework.stereotype.Service;

import br.edu.Infnet.MusicStreaming.music.entity.Music;
import br.edu.Infnet.MusicStreaming.music.repository.MusicRepository;
import br.edu.Infnet.MusicStreaming.playlist.dto.PlaylistRequestDTO;
import br.edu.Infnet.MusicStreaming.playlist.dto.PlaylistResponseDTO;
import br.edu.Infnet.MusicStreaming.playlist.entity.Playlist;
import br.edu.Infnet.MusicStreaming.playlist.entity.PlaylistMusic;
import br.edu.Infnet.MusicStreaming.playlist.repository.PlaylistRepository;
import br.edu.Infnet.MusicStreaming.user.entity.User;
import br.edu.Infnet.MusicStreaming.user.repository.UserRepository;

@Service
public class PlaylistService {

  private final PlaylistRepository playlistRepository;
  private final PlaylistMusicRepository playlistMusicRepository;
  private final MusicRepository musicRepository;
  private final UserRepository userRepository;

  public PlaylistService(PlaylistRepository playlistRepository, PlaylistMusicRepository playlistMusicRepository,
      MusicRepository musicRepository, UserRepository userRepository) {
    this.playlistRepository = playlistRepository;
    this.playlistMusicRepository = playlistMusicRepository;
    this.musicRepository = musicRepository;
    this.userRepository = userRepository;
  }

  public Playlist toEntity(PlaylistRequestDTO dto) {
    User user = userRepository.findById(dto.userId()).orElseThrow(() -> new RuntimeException("User not found"));
    return Playlist.builder().title(dto.title()).user(user).build();
  }

  public PlaylistResponseDTO toDTO(Playlist playlist) {
    return new PlaylistResponseDTO(playlist.getId(), playlist.getTitle());
  }

  public PlaylistResponseDTO createPlaylist(PlaylistRequestDTO dto) {
    Playlist playlist = toEntity(dto);
    return toDTO(playlistRepository.save(playlist));
  }

  public PlaylistResponseDTO getPlaylistById(Long id) {

    Playlist playlist = playlistRepository.findById(id).orElseThrow(() -> new RuntimeException("Playlist not found"));
    return toDTO(playlist);
  }

  public List<PlaylistResponseDTO> getAllPlaylists() {
    return playlistRepository.findAll().stream().map(this::toDTO).toList();
  }

  public PlaylistResponseDTO updatePlaylist(Long id, PlaylistRequestDTO dto) {
    Playlist playlist = playlistRepository.findById(id).orElseThrow(() -> new RuntimeException("Playlist not found"));
    playlist.setTitle(dto.title());

    return toDTO(playlistRepository.save(playlist));
  }

  public void deletePlaylist(Long id) {
    if (!playlistRepository.existsById(id)) {
      throw new RuntimeException("Playlist not found");
    }
    playlistRepository.deleteById(id);
  }

  public void addMusic(Long playlistId, Long musicId) {
    Playlist playlist = playlistRepository.findById(playlistId)
        .orElseThrow(() -> new RuntimeException("Playlist not found"));
    Music music = musicRepository.findById(musicId).orElseThrow(() -> new RuntimeException("Music not found"));

    boolean alreadyExists = playlistMusicRepository.findByPlaylistIdAndMusicId(playlistId, musicId).isPresent();
    if (alreadyExists) {
      return;
    }

    PlaylistMusic relation = PlaylistMusic.builder().playlist(playlist).music(music).build();
    playlistMusicRepository.save(relation);
  }

  public void removeMusic(Long playlistId, Long musicId) {
    playlistMusicRepository.findByPlaylistIdAndMusicId(playlistId, musicId).ifPresent(playlistMusicRepository::delete);
  }

  public List<Music> getMusicsInPlaylist(Long playlistId) {
    return playlistMusicRepository.findByPlaylistId(playlistId).stream().map(PlaylistMusic::getMusic).toList();
  }
}