package cs3500.music.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.TreeSet;

/**
 * Represents the notes contained in a piece of music.
 */
public class MusicEditorModel implements IMusicEditor<Note> {
  private int duration;
  private int tempo;
  public Map<Note, ArrayList<PlaybackInfo>> noteMap;


  /**
   * Creates a new music editor model with the specified duration.
   *
   * @param duration the initial length of the model in beats.
   */
  public MusicEditorModel(int duration) {
    this.duration = duration;
    this.noteMap = new TreeMap<>();
  }

  /**
   * Initializes all possible notes in all octaves to be at rest for the initial duration.
   */
  private void initializeStates(int instrument) {
    PlaybackInfo[] initial = new PlaybackInfo[this.duration];
    List<PlaybackInfo> initalList = new ArrayList<>(Arrays.asList(initial));
    Collections.fill(initalList, new PlaybackInfo(MusicStates.REST, 0));
    for (int i = 0; i <= 10; i++) {
      for (Pitch p : Pitch.values()) {
        this.noteMap.put(new Note(p, i, instrument), new ArrayList<>(initalList));
      }
    }
//    this.noteMap.put(note, new ArrayList<>(initalList));
  }


  @Override
  public void addNote(Note note, int beatNumber, int length, int volume) throws IllegalArgumentException {
    this.checkCanAdd(note, beatNumber, length, volume);

    this.noteMap.get(note).set(beatNumber, new PlaybackInfo(MusicStates.START, volume));
    for (int i = beatNumber + 1; i < beatNumber + length; i++) {
      this.noteMap.get(note).set(i, new PlaybackInfo(MusicStates.CONTINUE, volume));
    }
  }

  @Override
  public void removeNote(Note note, int beatNumber) throws IllegalArgumentException {
    this.checkCanRemove(note, beatNumber);

    this.noteMap.get(note).set(beatNumber, new PlaybackInfo(MusicStates.REST, 0));
    int index = beatNumber;
    while (index < this.noteMap.get(note).size() &&
            this.noteMap.get(note).get(index).getState() != MusicStates.START) {
      this.noteMap.get(note).set(index, new PlaybackInfo(MusicStates.REST, 0));
      index += 1;
    }
  }

  @Override
  public void addPieceConsecutively(IMusicEditor<Note> piece) {
    this.checkCanCombine(piece);

    MusicEditorModel other = (MusicEditorModel) piece;
    for (Note note : this.noteMap.keySet()) {
      this.noteMap.get(note).addAll(other.noteMap.get(note));
    }
    this.duration += other.duration;
  }

  @Override
  public void addPieceSimultaneously(IMusicEditor<Note> piece) {
    this.checkCanCombine(piece);

    MusicEditorModel other = (MusicEditorModel) piece;
    if (other.duration > this.duration) {
      throw new IllegalArgumentException(
              "Cannot play a longer piece simultaneously with this piece.");
    }

    this.overlayNotes(other);
  }

  @Override
  public void setTempo(int tempo) {
    this.tempo = tempo;
  }

  @Override
  public int getDuration() {
    return this.duration;
  }

  /**
   * Takes the notes from the other cs3500.music.model.MusicEditorModel and overlays them over the
   * notes of this cs3500.music.model.MusicEditorModel so that they are simultaneous.
   *
   * @param other the cs3500.music.model.MusicEditorModel to get notes from.
   */
  private void overlayNotes(MusicEditorModel other) {
    for (Note note : other.noteMap.keySet()) {
      this.checkCanAdd(note,0, 1, 1);
      for (int i = 0; i < this.duration; i++) {
        if (other.noteMap.get(note).get(i).getState() != MusicStates.REST
                && !this.noteMap.get(note).get(i).equals(other.noteMap.get(note).get(i))) {
          this.noteMap.get(note).set(i, other.noteMap.get(note).get(i));
        }
      }
    }
  }

  /**
   * Checks to see if the piece can be combined with this.
   * They can only be combined if the piece is also an instance of this class.
   *
   * @param piece the cs3500.music.model.IMusicEditor implementation to be combined
   */
  private void checkCanCombine(IMusicEditor<Note> piece) {
    if (!(piece instanceof MusicEditorModel)) {
      throw new IllegalArgumentException(
              "Only instances of the same implementation can be combined");
    }
  }

  /**
   * Checks to see if the note is eligible to be removed.
   *
   * @param note       to be removed
   * @param beatNumber at which the note starts
   */
  private void checkCanRemove(Note note, int beatNumber) {
    if (beatNumber >= this.noteMap.get(note).size()) {
      throw new IllegalArgumentException("beatNumber is out of range");
    }
    if (this.noteMap.get(note).get(beatNumber).getState() != MusicStates.START) {
      throw new IllegalArgumentException("no note starts at that beatNumber");
    }
  }

  /**
   * Checks to see if the note of given length is allowed to be added at the specified beat number.
   *
   * @param note       the note to be added
   * @param beatNumber the number of the beat at which the note starts
   * @param length     the number of beats long the note is
   */
  private void checkCanAdd(Note note, int beatNumber, int length, int volume) {
    if (!this.noteMap.containsKey(note)) {
      this.initializeStates(note.getInstrument());
    }

    if (beatNumber + length > this.noteMap.get(note).size()) {
      throw new IllegalArgumentException("note is too long.");
    }

    if (volume < 0 || volume > 127) {
      throw new IllegalArgumentException("volume must be between 0 and 127 (inclusive)");
    }

    if (length < 1) {
      throw new IllegalArgumentException("note must be at least one beat long.");
    }
  }

  @Override
  public String getState() {
    String state = " ";
    List<Note> noteRange = this.getNoteRange();

    state += getColumnHeaders(noteRange) + "\n";
    state += getRowStates(noteRange);

    return state;
  }

  /**
   * Gets the status of all the beats in the noteMap.
   *
   * @param noteRange the range of notes that comprise the piece
   * @return a String representing the MusicState of the notes for each beat of the piece.
   */
  private String getRowStates(List<Note> noteRange) {
    String rowStates = "";
    int beatColumnWidth = Integer.toString(this.duration).length();
    for (int i = 0; i < this.duration; i++) {
      rowStates += String.format("%1$" + beatColumnWidth + "s", i);
      for (Note note : noteRange) {
        rowStates += String.format("%1$10s", this.noteMap.get(note).get(i).getState().toString());
      }
      rowStates += "\n";
    }
    return rowStates;
  }

  /**
   * Gets the column headers for the state.
   *
   * @param noteRange the range of notes that comprise the piece
   * @return a String representing all the notes that make up the piece, from the lowest to highest
   */
  private String getColumnHeaders(List<Note> noteRange) {
    String columnHeaders = "";
    for (Note note : noteRange) {
      String noteColumn = String.format("%1$10s", note.toString());
      columnHeaders += noteColumn;
    }
    return columnHeaders;
  }

  /**
   * Gets the range of notes that comprise the piece, from the lowest to the highest note.
   *
   * @return a sublist of notes where the first item is the lowest note and the last item is the
   * highest note.
   */
  public List<Note> getNoteRange() {
    int lowestNoteIndex = getLowestNoteIndex();

    int highestNoteIndex = getHighestNoteIndex();

    // True if there are no notes contained in the model.
    if (lowestNoteIndex > highestNoteIndex) {
      return new ArrayList<Note>();
    }

    return new ArrayList<Note>(this.noteMap.keySet()).subList(lowestNoteIndex, highestNoteIndex);
  }

  @Override
  public String getNoteState(Note note, int beatNumber) throws IllegalArgumentException {
    if (beatNumber > this.duration) {
      throw new IllegalArgumentException("No note exists at that beat number");
    }
    return this.noteMap.get(note).get(beatNumber).getState().name().toLowerCase();
  }

  /**
   * Get the highest note that is not completely at rest.
   *
   * @return an int of the index in the set of notes.
   */
  private int getHighestNoteIndex() {
    int highestNoteIndex = this.noteMap.size();
    Iterator iter = new TreeSet<Note>(this.noteMap.keySet()).descendingIterator();
    while (iter.hasNext()) {
      if (!this.allAtRest(this.noteMap.get(iter.next()))) {
        break;
      }
      highestNoteIndex -= 1;
    }
    return highestNoteIndex;
  }

  /**
   * Get the lowest note that is not completely at rest.
   *
   * @return an int of the index in the set of notes.
   */
  private int getLowestNoteIndex() {
    int lowestNoteIndex = 0;
    for (Note note : this.noteMap.keySet()) {
      if (!this.allAtRest(this.noteMap.get(note))) {
        break;
      }
      lowestNoteIndex += 1;
    }
    return lowestNoteIndex;
  }

  /**
   * Determines if the list of cs3500.music.model.MusicStates contains only rests.
   *
   * @param playbackInfos the list of cs3500.music.model.PlaybackInfos to check
   * @return true if the list only contains rests, false otherwise.
   */
  private boolean allAtRest(List<PlaybackInfo> playbackInfos) {
    for (PlaybackInfo p : playbackInfos) {
      if (p.getState() != MusicStates.REST) {
        return false;
      }
    }
    return true;
  }

}
