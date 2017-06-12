package cs3500.music.model;

import java.util.Objects;

/**
 * Represents a single musical note, including octave.
 */
public class Note implements Comparable<Note> {

  private Pitch pitch;
  private int octave;

  /**
   * Assigns the length of the element in beats.
   *
   * @param pitch  the pitch of the note, must be one of the twelve commonly used in western music.
   * @param octave the octave the note is in, must be hearable by humans.
   */
  public Note(Pitch pitch, int octave) {
    if (octave < 0 || octave > 10) {
      throw new IllegalArgumentException("octave must be between 0 and 10 (inclusive)");
    }
    this.pitch = pitch;
    this.octave = octave;
  }

  @Override
  public int compareTo(Note note) {
    if (this.octave < note.octave) {
      return -1;
    } else if (this.octave > note.octave) {
      return 1;
    }
    return this.pitch.getPitchNumber() - note.pitch.getPitchNumber();
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }

    if (o instanceof Note) {
      Note that = (Note) o;
      return this.pitch == that.pitch && this.octave == that.octave;
    }

    return false;
  }

  @Override
  public int hashCode() {
    return Objects.hash(this.pitch, this.octave);
  }

  @Override
  public String toString() {
    return this.pitch.toString() + this.octave;
  }

  /**
   * Gets the pitch.
   *
   * @return the pitch of the note.
   */
  public Pitch getPitch() {
    return this.pitch;
  }

  /**
   * Gets the octave of the note.
   *
   * @return the octave the pitch is in.
   */
  public int getOctave() {
    return this.octave;
  }
}
