package cs3500.music.tests;

import org.junit.Test;

import cs3500.music.model.Pitch;

import static org.junit.Assert.assertEquals;

/**
 * Tests methods in the cs3500.music.model.Pitch enum.
 */
public class PitchTest {

  @Test
  public void testToString() {
    String allPitches = "";
    for (Pitch p : Pitch.values()) {
      allPitches += p.toString() + " ";
    }
    assertEquals("C C# D D# E F F# G G# A A# B", allPitches.trim());
  }

  @Test
  public void testPitchNumber() {
    String allPitchNumbers = "";
    for (Pitch p : Pitch.values()) {
      allPitchNumbers += p.getPitchNumber() + " ";
    }
    assertEquals("12 13 14 15 16 17 18 19 20 21 22 23", allPitchNumbers.trim());
  }
}
