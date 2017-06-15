package cs3500.music.model;

/**
 * Represents the information required to play a note at certain beat.
 * Stores information like MusicState(START,CONTINUE,REST) and volume.
 */
public class PlaybackInfo {
  private MusicStates state;
  private int volume;

  public PlaybackInfo(MusicStates state, int volume) {
    if (state == MusicStates.REST && volume > 0) {
      throw new IllegalArgumentException("Rests cannot have a volume");
    }
    this.state = state;
    this.volume = volume;
  }

  public MusicStates getState() {
    return this.state;
  }

  public int getVolume() {
    return this.volume;
  }

  @Override
  public String toString() {
    return this.state.toString();
  }
}
