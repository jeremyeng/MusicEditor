package cs3500.music.view;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import cs3500.music.model.IReadOnlyMusicEditor;
import cs3500.music.model.Note;


/**
 * Represents the music editor as text printed to the console.
 */
public class ConsoleView implements IMusicEditorView {

  private IReadOnlyMusicEditor model;
  private Appendable text;

  /**
   * Construct a console view with the given appendable as its text.
   * @param app the appendable to be set as the console view's text.
   */
  public ConsoleView(Appendable app) {
    this.text = app;
  }

  @Override
  public void makeVisible() throws IOException {
    text.append(this.getState());
  }

  @Override
  public void update(IReadOnlyMusicEditor model) {
    this.model = model;
  }

  /**
   * Displays the notes in the model that are played. The rows represent the beat numbers of the
   * piece and the columns represent each note in the piece as well as during which beats they
   * start and continue.
   * @return a String
   */
  public String getState() {
    String state = "";
    Map<Integer, List<String>> combined = this.model.getCombinedNoteMap();

    int lowNote = this.getLowNote();
    int highNote = this.getHighNote();

    state += getColumnHeaders(lowNote, highNote, combined) + "\n";
    state += getRowStates(lowNote, highNote, combined);

    return state;
  }

  /**
   * Gets the columns of the text view showing the range of pitch-octaves in the piece.
   *
   * @param lowNote the range of notes that comprise the piece
   * @param highNote the lowest pitch-octave in the piece
   * @param combined the map of notes where all instrument's notes are combined.
   * @return a String representing the MusicState of the notes for each beat of the piece.
   */
  private String getColumnHeaders(int lowNote, int highNote, Map<Integer, List<String>> combined) {
    int beatColumnWidth = Integer.toString(this.model.getDuration() - 1).length();
    String columnHeaders = String.format("%1$" + beatColumnWidth + "s", "");
    for (int j = lowNote; j <= highNote; j++) {
      columnHeaders += String.format("%1$" + Integer.toString(5) + "s", new Note(j, 0).toString());
    }

    return columnHeaders;
  }

  /**
   * Gets the status of all the beats in the noteMap ("X", "|", or " ").
   *
   * @param lowNote the range of notes that comprise the piece
   * @param highNote the lowest pitch-octave in the piece
   * @param combined the map of notes where all instrument's notes are combined.
   * @return a String representing the MusicState of the notes for each beat of the piece.
   */
  private String getRowStates(int lowNote, int highNote, Map<Integer, List<String>> combined) {
    String rowStates = "";
    int beatColumnWidth = Integer.toString(this.model.getDuration() - 1).length();
    for (int i = 0; i < this.model.getDuration(); i++) {
      rowStates += String.format("%1$" + beatColumnWidth + "s", i);
      for (int j = lowNote; j <= highNote; j++) {

        rowStates += String.format("%1$" + Integer.toString(5) + "s", this.convertState(combined.get(j).get(i)));
      }
      rowStates += "\n";
    }
    return rowStates;
  }

  /**
   * Converts the state of a note in string form to a symbol for the console view.
   *
   * @param s the state of the note.
   * @return the corresponding state symbol
   */
  private String convertState(String s) {
    switch (s) {
      case "start":
        return "X";
      case "continue":
        return "|";
      case "rest":
        return " ";
      default:
        throw new IllegalArgumentException("Invalid string state");
    }
  }

  /**
   * Determines the highest pitch-octave in the piece.
   *
   * @return an int representing the note.
   */
  private int getHighNote() {
    Map<Integer, List<String>> combined = this.model.getCombinedNoteMap();
    int highNote = combined.size() + 11;
    while (this.allRest(combined.get(highNote))) {
      if (highNote <= 12) {
        break;
      }
      highNote -= 1;
    }
    return highNote;
  }

  /**
   * Determines the lowest pitch-octave in the piece.
   *
   * @return an int representing the note.
   */
  private int getLowNote() {
    Map<Integer, List<String>> combined = this.model.getCombinedNoteMap();
    int lowNote = 12;
    while (this.allRest(combined.get(lowNote))) {
      if (lowNote > combined.keySet().size()) {
        break;
      }
      lowNote += 1;
    }
    return lowNote;
  }

  /**
   * Determines if all strings in the list are at rest.
   *
   * @param strings the arraylist of strings to check.
   * @return true if they are all at rest, false otherwise.
   */
  private boolean allRest(List<String> strings) {
    for (String string : strings) {
      if (!string.equals("rest")) {
        return false;
      }
    }
    return true;
  }

}
