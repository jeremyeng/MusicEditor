package cs3500.music.tests;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import cs3500.music.model.MusicEditorModel;
import cs3500.music.model.Note;
import cs3500.music.model.Pitch;

import static junit.framework.TestCase.assertEquals;

/**
 * Tests the methods implemented in the cs3500.music.model.MusicEditorModel class.
 */
public class MusicEditorModelTest {

  private MusicEditorModel model;

  /**
   * Initializes the model to be tested.
   */
  @Before
  public void initialize() {
    this.model = new MusicEditorModel(6);
  }

  @Rule
  public ExpectedException expectedException = ExpectedException.none();

  @Test
  public void testInitialState() {
    assertEquals(" \n" +
            "0\n" +
            "1\n" +
            "2\n" +
            "3\n" +
            "4\n" +
            "5\n", this.model.getState());
  }

  @Test
  public void testAddNoteLongerThanDuration() {
    this.model.addNote(new Note(Pitch.C, 0, 0), 0, 10, 64);
    assertEquals("    C0\n" +
            "0    X\n" +
            "1    |\n" +
            "2    |\n" +
            "3    |\n" +
            "4    |\n" +
            "5    |\n" +
            "6    |\n" +
            "7    |\n" +
            "8    |\n" +
            "9    |\n", this.model.getState());
  }

  @Test(expected = IllegalArgumentException.class)
  public void testAddNoteInvalidLength() {
    this.model.addNote(new Note(Pitch.C, 0, 0), 0, -1, 64);
  }

  @Test
  public void testAddOneNote() {

    this.model.addNote(new Note(Pitch.C, 0, 0), 0, 4, 60);
    assertEquals("    C0\n" +
            "0    X\n" +
            "1    |\n" +
            "2    |\n" +
            "3    |\n" +
            "4     \n" +
            "5     \n", this.model.getState());
  }

  @Test
  public void testAddTwoNotes() {
    this.model.addNote(new Note(Pitch.C, 0, 0), 0, 4, 60);
    this.model.addNote(new Note(Pitch.CSharp, 0, 0), 1, 4, 60);
    assertEquals("    C0  C#0\n" +
            "0    X     \n" +
            "1    |    X\n" +
            "2    |    |\n" +
            "3    |    |\n" +
            "4         |\n" +
            "5          \n", this.model.getState());
  }

  @Test
  public void testAddChord() {
    this.model.addNote(new Note(Pitch.C, 0, 0), 0, 4, 10);
    this.model.addNote(new Note(Pitch.E, 0, 0), 0, 4, 10);
    this.model.addNote(new Note(Pitch.G, 0, 1), 0, 4, 10);
    assertEquals("    C0  C#0   D0  D#0   E0   F0  F#0   G0\n" +
            "0    X                   X              X\n" +
            "1    |                   |              |\n" +
            "2    |                   |              |\n" +
            "3    |                   |              |\n" +
            "4                                        \n" +
            "5                                        \n", this.model.getState());
  }

  @Test
  public void testAddOverlappingNotes() {
    this.model.addNote(new Note(Pitch.C, 0, 1), 0, 4, 10);
    this.model.addNote(new Note(Pitch.C, 0, 1), 1, 4, 10);
    assertEquals("    C0\n" +
            "0    X\n" +
            "1    X\n" +
            "2    |\n" +
            "3    |\n" +
            "4    |\n" +
            "5     \n", this.model.getState());
  }

  @Test
  public void testAddSameNoteDifferentInstrument() {
    this.model.addNote(new Note(Pitch.C, 0, 1), 0, 4, 10);
    this.model.addNote(new Note(Pitch.C, 0, 2), 0, 4, 10);
    assertEquals("    C0\n" +
            "0    X\n" +
            "1    |\n" +
            "2    |\n" +
            "3    |\n" +
            "4     \n" +
            "5     \n", this.model.getState());
  }

  @Test
  public void testRemoveBottomOverlappingNote() {
    this.model.addNote(new Note(Pitch.C, 0, 0), 0, 4, 10);
    this.model.addNote(new Note(Pitch.C, 0, 0), 1, 4, 10);
    this.model.removeNote(new Note(Pitch.C, 0, 0), 1);
    assertEquals("    C0\n" +
            "0    X\n" +
            "1     \n" +
            "2     \n" +
            "3     \n" +
            "4     \n" +
            "5     \n", this.model.getState());
  }

  @Test
  public void testRemoveTopOverlappingNote() {
    this.model.addNote(new Note(Pitch.C, 0, 0), 0, 4, 10);
    this.model.addNote(new Note(Pitch.C, 0, 0), 1, 4, 10);
    this.model.removeNote(new Note(Pitch.C, 0, 0), 0);
    assertEquals("    C0\n" +
            "0     \n" +
            "1    X\n" +
            "2    |\n" +
            "3    |\n" +
            "4    |\n" +
            "5     \n", this.model.getState());
  }

  @Test(expected = IllegalArgumentException.class)
  public void testRemoveNoteOutOfRange() {
    this.model.addNote(new Note(Pitch.C, 0, 0), 0, 4, 10);
    this.model.removeNote(new Note(Pitch.C, 0, 0), 7);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testRemoveNoteDoesNotExist() {
    this.model.addNote(new Note(Pitch.C, 0, 0), 0, 4, 10);
    this.model.removeNote(new Note(Pitch.C, 0, 0), 2);
  }

  @Test
  public void testRemoveOneNote() {
    this.model.addNote(new Note(Pitch.C, 0,0), 0, 4, 10);
    this.model.addNote(new Note(Pitch.CSharp, 1, 0), 1, 4, 10);
    this.model.removeNote(new Note(Pitch.CSharp, 1, 0), 1);
    assertEquals("    C0\n" +
            "0    X\n" +
            "1    |\n" +
            "2    |\n" +
            "3    |\n" +
            "4     \n" +
            "5     \n", this.model.getState());
  }

  @Test
  public void testRemoveAllNotes() {
    this.model.addNote(new Note(Pitch.CSharp, 3, 0), 0, 4, 1);
    this.model.addNote(new Note(Pitch.B, 10, 0), 0, 4, 10);
    this.model.removeNote(new Note(Pitch.B, 10, 0), 0);
    this.model.removeNote(new Note(Pitch.CSharp, 3, 0), 0);
    assertEquals(" \n" +
            "0\n" +
            "1\n" +
            "2\n" +
            "3\n" +
            "4\n" +
            "5\n", this.model.getState());
  }

  @Test
  public void testAddConsecutivePiece() {
    this.model.addNote(new Note(Pitch.C, 0,0), 0, 4, 10);
    MusicEditorModel model2 = new MusicEditorModel(6);
    model2.addNote(new Note(Pitch.C, 0, 0), 0, 4, 10);
    this.model.addPieceConsecutively(model2);
    assertEquals("     C0\n" +
            " 0    X\n" +
            " 1    |\n" +
            " 2    |\n" +
            " 3    |\n" +
            " 4     \n" +
            " 5     \n" +
            " 6    X\n" +
            " 7    |\n" +
            " 8    |\n" +
            " 9    |\n" +
            "10     \n" +
            "11     \n", this.model.getState());
  }

  @Test(expected = IllegalArgumentException.class)
  public void testAddConsecutivePieceInvalid() {
    this.model.addNote(new Note(Pitch.C, 0, 0), 0, 4, 10);
    MusicEditorModel model2 = new MusicEditorModel(6);
    model2.addNote(new Note(Pitch.C, 0, 0), 0, 4, 10);
    this.model.addPieceConsecutively(null);
  }

  @Test
  public void testAddSameSimultaneousPiece() {
    this.model.addNote(new Note(Pitch.C, 0,0), 0, 4, 10);
    MusicEditorModel model2 = new MusicEditorModel(6);
    model2.addNote(new Note(Pitch.C, 0, 0), 0, 4, 10);
    this.model.addPieceSimultaneously(model2);
    assertEquals("    C0\n" +
            "0    X\n" +
            "1    |\n" +
            "2    |\n" +
            "3    |\n" +
            "4     \n" +
            "5     \n", this.model.getState());
  }

  @Test(expected = IllegalArgumentException.class)
  public void testAddSimultaneousPieceInvalid() {
    this.model.addNote(new Note(Pitch.C, 0, 0), 0, 4, 10);
    MusicEditorModel model2 = new MusicEditorModel(6);
    model2.addNote(new Note(Pitch.C, 0, 0), 1, 4, 10);
    this.model.addPieceSimultaneously(null);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testAddSimultaneousPieceTooLong() {
    this.model.addNote(new Note(Pitch.C, 0, 0), 0, 4, 10);
    MusicEditorModel model2 = new MusicEditorModel(10);
    model2.addNote(new Note(Pitch.C, 0, 0), 1, 4, 10);
    this.model.addPieceSimultaneously(model2);
  }

  @Test
  public void testAddOverlappingSimultaneousPiece() {
    this.model.addNote(new Note(Pitch.C, 0, 0), 0, 4, 10);
    MusicEditorModel model2 = new MusicEditorModel(6);
    model2.addNote(new Note(Pitch.C, 0, 0), 1, 4, 10);
    this.model.addPieceSimultaneously(model2);
    assertEquals("    C0\n" +
            "0    X\n" +
            "1    X\n" +
            "2    |\n" +
            "3    |\n" +
            "4    |\n" +
            "5     \n", this.model.getState());
  }

  @Test
  public void testAddDifferentSimultaneousPiece() {
    this.model.addNote(new Note(Pitch.C, 0, 0), 0, 4, 10);
    MusicEditorModel model2 = new MusicEditorModel(6);
    model2.addNote(new Note(Pitch.CSharp, 0, 0), 1, 4, 10);
    this.model.addPieceSimultaneously(model2);
    assertEquals("    C0  C#0\n" +
            "0    X     \n" +
            "1    |    X\n" +
            "2    |    |\n" +
            "3    |    |\n" +
            "4         |\n" +
            "5          \n", this.model.getState());
  }

  @Test
  public void testGetNoteState() {
    this.model.addNote(new Note(Pitch.C, 0, 0), 0, 4, 10);
    assertEquals("start", this.model.getNoteState(new Note(Pitch.C, 0, 0), 0));
    assertEquals("continue", this.model.getNoteState(new Note(Pitch.C, 0, 0), 1));
    assertEquals("rest", this.model.getNoteState(new Note(Pitch.C, 0, 0), 5));

  }

  @Test
  public void testGetCombinedNoteMap() {
    this.model.addNote(new Note(Pitch.C, 0, 0), 0, 4, 10);
    this.model.addNote(new Note(Pitch.C, 0, 1), 1, 4, 10);
    assertEquals("[start, start, continue, continue, continue, rest]",
            this.model.getCombinedNoteMap().get(12).toString());
  }

}
