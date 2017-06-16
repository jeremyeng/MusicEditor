package cs3500.music.view;

import java.awt.event.ActionListener;
import java.awt.event.KeyListener;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sound.midi.InvalidMidiDataException;
import javax.swing.*;

/**
 * Defines the behavior for the music editor.
 */
public interface IMusicEditorView<K> {

  /**
   * Makes the music editor visible to the user.
   */
  void makeVisible();

  void playNote(List<List<List<Integer>>> info, long tempo) throws InvalidMidiDataException;

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

  void setListener(ActionListener action,KeyListener key);

  void updateCurrentBeat(int beat);

}
