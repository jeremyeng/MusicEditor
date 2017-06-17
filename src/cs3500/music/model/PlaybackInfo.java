package cs3500.music.model;

/**
 * Represents the information required to play a note at certain beat.
 * Stores information like MusicState(START,CONTINUE,REST) and volume.
 */
public class PlaybackInfo {
  private MusicStates state;
  private int volume;

  /**
   * Construct a playback info
   * @param state the state of the music
   * @param volume the volume of the note
   */
  public PlaybackInfo(MusicStates state, int volume) {
    if (state == MusicStates.REST && volume > 0) {
      throw new IllegalArgumentException("Rests cannot have a volume");
    }
    this.state = state;
    this.volume = volume;
  }

  /**
   * Gets the current music state
   * @return the current music state
   */
  public MusicStates getState() {
    return this.state;
  }

  /**
   * Gets the current volume music state
   * @return the current volume of the note
   */
  public int getVolume() {
    return this.volume;
  }

  @Override
  public String toString() {
    return this.state.toString();
  }
}
