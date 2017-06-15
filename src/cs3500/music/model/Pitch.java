package cs3500.music.model;

import java.util.HashMap;
import java.util.Map;

/**
 * Represents the twelve distinct pitches in the western style of music.
 * Changes 6/13/17: Added method makePitch
 */
public enum Pitch {
  C(12), CSharp(13), D(14), DSharp(15), E(16), F(17), FSharp(18), G(19), GSharp(20), A(21), ASharp(22),
  B(23);

  private final int pitchNumber;

  private static Map<Integer, Pitch> pitchMap = new HashMap<>();

  static {
    for (Pitch p: Pitch.values()) {
      pitchMap.put(p.pitchNumber, p);
    }
  }

  Pitch(int pitchNumber) {
    this.pitchNumber = pitchNumber;
  }

  public static Pitch pitchFromNumber(int pitchNumber) {
    if (pitchMap.get(pitchNumber) == null) {
      throw new IllegalArgumentException("Not a valid pitch");
    }
    return pitchMap.get(pitchNumber);
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

//  /**
//   * Makes a pitch according to the integer given
//   * @param pitch the integer that associates with the pitch
//   * @return the pitch that associates with the Integer.
//   */
//  public static Pitch makePitch(int pitch) {
//    switch (pitch) {
//      case 1: return C;
//      case 2 :return CSharp;
//      case 3: return D;
//      case 4: return DSharp;
//      case 5: return E;
//      case 6: return F;
//      case 7: return FSharp;
//      case 8: return G;
//      case 9: return GSharp;
//      case 10: return A;
//      case 11: return ASharp;
//      case 12: return B;
//      default: throw new IllegalArgumentException("No such pitch!");
//    }
//  }
}
