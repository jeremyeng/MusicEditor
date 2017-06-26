package cs3500.music.model;

import java.util.List;
import java.util.Map;

/**
 * This is the interface that defines behavior for a Music Editor.
 * It is parameterized over K, so any implementation of a cs3500.music.model.Note can be used.
 */
public interface IMusicEditor<K> {

  /**
   * Adds the given note to the Music Editor
   * @param note the note to be added.
   * @param beatNumber the number of the beat at which the note starts.
   * @param length the length of the note to be added.
   * @throws IllegalArgumentException if the note cannot be added.
   */
  void addNote(K note, int beatNumber, int length, int volume) throws IllegalArgumentException;

  /**
   * Removes the specified note from the Music Editor.
   * @param note the note to be removed
   * @param beatNumber the number of the beat that the note starts at.
   * @throws IllegalArgumentException if the note cannot be removed, or if it does not exist.
   */
  void removeNote(K note, int beatNumber) throws IllegalArgumentException;


  /**
   * Combines another piece of music so that its notes are played after the notes contained
   * in this cs3500.music.model.IMusicEditor.
   * NOTE: Only instances of the same implementations of the interface can be combined.
   * @param piece the piece to be combined.
   * @throws IllegalArgumentException if different implementation is attempted to be combined.
   */
  void addPieceConsecutively(IMusicEditor<K> piece) throws IllegalArgumentException;

  /**
   * Combines another piece of music so that its notes are played at te same time as
   * the notes contained in this cs3500.music.model.IMusicEditor.
   * The piece being added must be of the same or equal length of this piece.
   * If two notes overlap, the note of the second piece will override those of the first.z
   * NOTE: Only instances of the same implementations of the interface can be combined.
   * @param piece the piece to be combined.
   * @throws IllegalArgumentException if different implementation is attempted to be combined.
   */
  void addPieceSimultaneously(IMusicEditor<K> piece) throws IllegalArgumentException;

  /**
   * Sets the tempo of the model in microseconds per beat.
   * @param tempo the tempo in microseconds per beat.
   */
  void setTempo(long tempo);

  /**
   * Gets how long the piece is in number of beats.
   * @return the
   */
  int getDuration();

  /**
   * Gets the tempo of the piece in microseconds per beat.
   * @return microseconds per beat
   */
  long getTempo();

  /**
   * Gets the range of notes that make up the piece, from the lowest to highest.
   * The notes are ordered by octave first and then instrument.
   * @return a list of notes where the first element is the lowest note and the last element is
   *         the highest note.
   */
  List<K> getNoteRange();


  /**
   * Retrieves the state of a given note at a given beatNumber.
   * A note state will either be "start", "continue", or "rest"
   * @param note the note to get the state of.
   * @param beatNumber the number of the beat in the piece.
   * @return a string representing the state of the note at the given beat.
   */
  String getNoteState(K note, int beatNumber) throws IllegalArgumentException;

  /**
   * Gets the information to send to a midi receiver
   * @return a map of integers where the keys represent the beats and the values represent
   *         a nested list of integers containing information necessary to send messages to a midi
   *         such on and off, instrument, volume, etc.
   */
  List<List<List<Integer>>> getMidiInfo();

  /**
   * Maps the full ten octaves, regardless of instrument or volume to a list of strings representing
   * the state at a particular beat.
   * The keys of the map are the integer representation of a pitch-octave notation (ex. C4 = 60)
   * The values are a list of strings that are either "start", "continue", or "rest" that show
   * the state of that pitch at a particular beat.
   * @return a map of integer representations of notes to a list of their states.
   */
  Map<Integer, List<String>> getCombinedNoteMap();

  /**
   * Displays the notes in the model that are played. The rows represent the beat numbers of the
   * piece and the columns represent each note in the piece as well as during which beats they
   * start and continue.
   * @return a String illustrating the state of the notes.
   */
  String getState();

  List<Integer> notesPlaying(int beat);

}
