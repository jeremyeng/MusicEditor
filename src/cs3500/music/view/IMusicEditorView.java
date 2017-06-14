package cs3500.music.view;

import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sound.midi.InvalidMidiDataException;

/**
 * Defines the behavior for the music editor.
 */
public interface IMusicEditorView<K> {

  void initialize();

  void playNote() throws InvalidMidiDataException;

  /**
   * Makes the music editor visible to the user.
   */
  void makeVisible();

  /**
   * Sets the view with an action listener so when the user interacts with the
   * view the controller knows what to do.
   * @param actionEvent
   */
  void setCommandButtonListener(ActionListener actionEvent);

  /**
   * Sets the range of note of the music editor.
   * @param noteRange the range of notes in the music editor
   */
  void setNoteRange(List<K> noteRange);

  /**
   * Sets the duration of the music editor.
   * @param duration the total duration of the music editor
   */
  void setDuration(int duration);

  /**
   * Sets a note map for the music editor that maps a note to its state on every beat.
   * @param notes the note map that describe the behavior of each note at every beat
   */
  void setNoteMap(Map<K, List<String>> notes);

  void setCurrentBeat(int beat);

  void showErrorMessage();

  void refresh();
}
