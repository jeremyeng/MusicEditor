package cs3500.music.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.TreeSet;

import cs3500.music.util.CompositionBuilder;

/**
 * Represents the notes contained in a piece of music.
 */
public class MusicEditorModel implements IMusicEditor<Note> {
  private int duration;
  private long tempo;
  private Map<Note, ArrayList<PlaybackInfo>> noteMap;


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
  public void setTempo(long tempo) {
    this.tempo = tempo;
  }

  @Override
  public int getDuration() {
    return this.duration;
  }

  @Override
  public long getTempo() {
    return this.tempo;
  }


  /**
   * Takes the notes from the other cs3500.music.model.MusicEditorModel and overlays them over the
   * notes of this cs3500.music.model.MusicEditorModel so that they are simultaneous.
   *
   * @param other the cs3500.music.model.MusicEditorModel to get notes from.
   */
  private void overlayNotes(MusicEditorModel other) {
    for (Note note : other.noteMap.keySet()) {
      this.checkCanAdd(note, 0, 1, 1);
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
      this.expand((beatNumber + length) - this.duration);
    }

    if (volume < 0 || volume > 127) {
      throw new IllegalArgumentException("volume must be between 0 and 127 (inclusive)");
    }

    if (length < 0) {
      throw new IllegalArgumentException("cannot have negative beats.");
    }

  }

  /**
   * Expands the size of the noteMap to hold more notes.
   *
   * @param expandSize the amount to expand the notemap by
   */
  private void expand(int expandSize) {
    this.duration += expandSize;
    PlaybackInfo[] initial = new PlaybackInfo[expandSize];
    List<PlaybackInfo> initalList = new ArrayList<>(Arrays.asList(initial));
    Collections.fill(initalList, new PlaybackInfo(MusicStates.REST, 0));
    for (Note note : this.noteMap.keySet()) {
      this.noteMap.get(note).addAll(initalList);
    }
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

  @Override
  public List<List<List<Integer>>> getMidiInfo() {
    List<List<List<Integer>>> info = new ArrayList<>();
    for (int i = 0; i < this.duration; i++) {
      info.add(i, new ArrayList<>());
    }

    for (Note note : this.noteMap.keySet()) {
      for (int i = 0; i < this.duration; i++) {
        if (this.noteMap.get(note).get(i).getState() == MusicStates.START) {
          int volume = this.noteMap.get(note).get(i).getVolume();
          info.get(i).add(new ArrayList<Integer>(Arrays.asList(1, note.getInstrument() - 1, note.getNoteNumber(), volume, this.getLength(note, i))));
        }
      }
    }
    return info;
  }

  @Override
  public Map<Integer, List<String>> getCombinedNoteMap() {
    Map<Integer, List<String>> combined = new TreeMap<>();

    String[] initial = new String[this.duration];
    List<String> initalList = new ArrayList<>(Arrays.asList(initial));
    Collections.fill(initalList, "rest");
    for (int i = 12; i <= 143; i++) {
      combined.put(i, new ArrayList<>(initalList));
    }

    for (Note note : this.noteMap.keySet()) {
      for (int i = 0; i < this.duration; i++) {
        String storedState = combined.get(note.getNoteNumber()).get(i);
        String currentState = this.noteMap.get(note).get(i).getState().name().toLowerCase();
        if (!storedState.equals(currentState) && !currentState.equals("rest")) {
          combined.get(note.getNoteNumber()).set(i, currentState);
        }

      }
    }
    return combined;
  }

  /**
   * Gets the length of a note starting at a given beat.
   *
   * @param note  the Note to get the length of.
   * @param start the beat the note starts at.
   * @return the length of the note.
   */
  private int getLength(Note note, int start) {
    int length = 1;
    for (int beat = start + 1; beat < this.duration
            && this.noteMap.get(note).get(beat).getState() != MusicStates.REST
            && this.noteMap.get(note).get(beat).getState() != MusicStates.START; beat++) {
      length += 1;
    }
    return length;
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

  /**
   * Builder class for MusicEditorModel that allows the user to add notes and set the tempo
   * without needing access to the constructor.
   */
  public static class Builder implements CompositionBuilder<IMusicEditor<Note>> {

    private IMusicEditor<Note> model = new MusicEditorModel(10);

    @Override
    public IMusicEditor<Note> build() {
      return this.model;
    }

    @Override
    public CompositionBuilder<IMusicEditor<Note>> setTempo(int tempo) {
      this.model.setTempo(tempo);
      return this;
    }

    @Override
    public CompositionBuilder<IMusicEditor<Note>> addNote(int start, int end, int instrument, int pitch, int volume) {
      this.model.addNote(new Note(pitch, instrument), start, end - start, volume);
      return this;
    }
  }

  /**
   * Displays the notes in the model that are played. The rows represent the beat numbers of the
   * piece and the columns represent each note in the piece as well as during which beats they
   * start and continue.
   * @return a String
   */
  public String getState() {
    String state = "";
    Map<Integer, List<String>> combined = this.getCombinedNoteMap();

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
    int beatColumnWidth = Integer.toString(this.getDuration() - 1).length();
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
    int beatColumnWidth = Integer.toString(this.getDuration() - 1).length();
    for (int i = 0; i < this.getDuration(); i++) {
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
    Map<Integer, List<String>> combined = this.getCombinedNoteMap();
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
    Map<Integer, List<String>> combined = this.getCombinedNoteMap();
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
