package cs3500.music.model;

import java.util.Objects;

/**
 * Represents a single musical note, including octave.
 */
public class Note implements Comparable<Note> {

  private Pitch pitch;
  private int octave;
  private int instrument;


  /**
   * Assigns the length of the element in beats.
   *
   * @param pitch  the pitch of the note, must be one of the twelve commonly used in western music.
   * @param octave the octave the note is in, must be hearable by humans.
   * @param instrument an integer representing the instrument that plays the note.
   */
  public Note(Pitch pitch, int octave, int instrument) {
    if (octave < 0 || octave > 10) {
      throw new IllegalArgumentException("octave must be between 0 and 10 (inclusive)");
    }

    this.pitch = pitch;
    this.octave = octave;
    this.instrument = instrument;
  }

  @Override
  public int compareTo(Note note) {
    if (this.octave < note.octave) {
      return -1;
    } else if (this.octave > note.octave) {
      return 1;
    }
    return (this.pitch.getPitchNumber() + this.instrument * 1000)
            - (note.pitch.getPitchNumber() + note.instrument * 1000);
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }

    if (o instanceof Note) {
      Note that = (Note) o;
      return this.pitch.getPitchNumber() == that.pitch.getPitchNumber()
              && this.octave == that.octave
              && this.instrument == that.instrument;
    }

    return false;
  }

  @Override
  public int hashCode() {
    return Objects.hash(this.pitch, this.octave, this.instrument);
  }

  @Override
  public String toString() {
    return this.pitch.toString() + this.octave +"(" + this.instrument + ")" ;
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

  public int getInstrument() {
    return instrument;
  }

//  /**
//   * Construct a note base on the given pitch and octave.
//   * @param pitch the pitch of the note that is to be created
//   * @param octave the octave of the note that is to be created
//   * @return the note with the given pitch and octave.
//   */
//  public static Note makeNote(int pitch, int octave) {
//    if (octave < 0) {
//      throw new IllegalArgumentException("Invalid octave parameter!");
//    }
//    return new Note(Pitch.makePitch(pitch),octave);
//  }

}
