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
  private Note c4_0;
  private Note c4_1;
  private Note cSharp4_0;
  private Note b3_0;

  /**
   * Initializes the notes to be tested.
   */
  @Before
  public void initialize() {
    this.c4_0 = new Note(Pitch.C, 4, 0);
    this.c4_1 =  new Note(Pitch.C, 4, 1);
    this.cSharp4_0 = new Note(Pitch.CSharp, 4, 0);
    this.b3_0 = new Note(Pitch.B, 3, 0);
  }

  @Rule
  public ExpectedException expectedException = ExpectedException.none();

  @Test
  public void testNoteToString() {
    assertEquals("C4(0)", this.c4_0.toString());
    assertEquals("C#4(0)", this.cSharp4_0.toString());
    assertEquals("B3(0)", this.b3_0.toString());
  }

  @Test (expected = IllegalArgumentException.class)
  public void testCreateNoteOctaveTooLow() {
    Note badNote = new Note(Pitch.C, 11, 0);
  }

  @Test (expected = IllegalArgumentException.class)
  public void testCreateNoteOctaveTooHigh() {
    Note badNote = new Note(Pitch.C, -1, 0);
  }

  @Test
  public void testCompareTo() {
    assertEquals(true, this.c4_0.compareTo(this.cSharp4_0) < 0);
    assertEquals(true, this.c4_0.compareTo(this.c4_1) < 0);
    assertEquals(true, this.c4_0.compareTo(this.c4_0) == 0);
    assertEquals(true, this.c4_0.compareTo(this.b3_0) > 0);
  }

  @Test
  public void testEquals() {
    assertEquals(true, this.c4_0.equals(new Note(Pitch.C, 4, 0)));
    assertEquals(false, this.c4_0.equals(this.cSharp4_0));
  }

  @Test
  public void testHashCode() {
    assertEquals(true, this.c4_0.hashCode() == new Note(Pitch.C, 4, 0).hashCode());
    assertEquals(false, this.c4_0.hashCode() == this.cSharp4_0.hashCode());
  }

  @Test
  public void testGetOctave() {
    assertEquals(4, this.c4_0.getOctave());
    assertEquals(4, this.cSharp4_0.getOctave());
    assertEquals(3, this.b3_0.getOctave());
  }

  @Test
  public void testGetPitch() {
    assertEquals(Pitch.C, this.c4_0.getPitch());
    assertEquals(Pitch.CSharp, this.cSharp4_0.getPitch());
    assertEquals(Pitch.B, this.b3_0.getPitch());
  }
}
