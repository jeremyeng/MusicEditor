package cs3500.music.model;

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
  void addNote(K note, int beatNumber, int length) throws IllegalArgumentException;

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
   * Displays the notes in the model that are played. The rows represent the beat numbers of the
   * piece and the columns represent each note in the piece as well as during which beats they
   * start and continue.
   * @return a String
   */
  String getState();

}