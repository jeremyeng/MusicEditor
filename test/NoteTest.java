import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import cs3500.music.model.Note;
import cs3500.music.model.Pitch;

import static junit.framework.TestCase.assertEquals;

/**
 * Tests the methods implemented in the note class.
 */
public class NoteTest {
  private Note c4;
  private Note cSharp4;
  private Note b3;

  /**
   * Initializes the notes to be tested.
   */
  @Before
  public void initialize() {
    this.c4 = new Note(Pitch.C, 4);
    this.cSharp4 = new Note(Pitch.CSharp, 4);
    this.b3 = new Note(Pitch.B, 3);
  }

  @Rule
  public ExpectedException expectedException = ExpectedException.none();

  @Test
  public void testNoteToString() {
    assertEquals("C4", this.c4.toString());
    assertEquals("C#4", this.cSharp4.toString());
    assertEquals("B3", this.b3.toString());
  }

  @Test (expected = IllegalArgumentException.class)
  public void testCreateNoteOctaveTooLow() {
    Note badNote = new Note(Pitch.C, 11);
  }

  @Test (expected = IllegalArgumentException.class)
  public void testCreateNoteOctaveTooHigh() {
    Note badNote = new Note(Pitch.C, -1);
  }

  @Test
  public void testCompareTo() {
    assertEquals(true, this.c4.compareTo(this.cSharp4) < 0);
    assertEquals(true, this.c4.compareTo(this.c4) == 0);
    assertEquals(true, this.c4.compareTo(this.b3) > 0);
  }

  @Test
  public void testEquals() {
    assertEquals(true, this.c4.equals(new Note(Pitch.C, 4)));
    assertEquals(false, this.c4.equals(this.cSharp4));
  }

  @Test
  public void testHashCode() {
    assertEquals(true, this.c4.hashCode() == new Note(Pitch.C, 4).hashCode());
    assertEquals(false, this.c4.hashCode() == this.cSharp4.hashCode());
  }

  @Test
  public void testGetOctave() {
    assertEquals(4, this.c4.getOctave());
    assertEquals(4, this.cSharp4.getOctave());
    assertEquals(3, this.b3.getOctave());
  }

  @Test
  public void testGetPitch() {
    assertEquals(Pitch.C, this.c4.getPitch());
    assertEquals(Pitch.CSharp, this.cSharp4.getPitch());
    assertEquals(Pitch.B, this.b3.getPitch());
  }
}
