package cs3500.music.model;

/**
 * Represents the states of music.
 * In a piece of music the state is always either:
 * (1) The start of a new note -> START
 * (2) The continuation of the last started note -> CONTINUE
 * (3) No note is being played -> REST
 */
public enum MusicStates {
  START, CONTINUE, REST;


  @Override
  public String toString() {
    switch (this) {
      case START:
        return "  X  ";
      case CONTINUE:
        return "  |  ";
      case REST:
        return "     ";
      default:
        throw new IllegalArgumentException();
    }
  }
}
