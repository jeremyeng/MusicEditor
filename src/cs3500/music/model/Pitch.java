package cs3500.music.model;

/**
 * Represents the twelve distinct pitches in the western style of music.
 */
public enum Pitch {
  C(1), CSharp(2), D(3), DSharp(4), E(5), F(6), FSharp(7), G(8), GSharp(9), A(10), ASharp(11),
  B(12);

  private final int pitchNumber;

  Pitch(int pitchNumber) {
    this.pitchNumber = pitchNumber;
  }

  /**
   * Gets the number of the pitch so it can be compared to other pitches in the same octave.
   * @return The number of this pitch.
   */
  public int getPitchNumber() {
    return this.pitchNumber;
  }

  @Override
  public String toString() {
    if (this.name().contains("Sharp")) {
      return this.name().charAt(0) + "#";
    }
    return this.name();
  }
}
