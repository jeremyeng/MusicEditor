package cs3500.music.view;

import java.awt.event.ActionListener;
import java.awt.event.KeyListener;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sound.midi.InvalidMidiDataException;
import javax.swing.*;

import cs3500.music.model.IMusicEditor;
import cs3500.music.model.MusicEditorModel;

import cs3500.music.model.Note;

/**
 * Defines the behavior for the music editor.
 */
public interface IMusicEditorView<K> {

  /**
   * Makes the music editor visible to the user.
   */
  void makeVisible() throws InvalidMidiDataException;

  void update(IMusicEditor<K> model);

  /**
   * Sets the duration of the music editor.
   * @param duration the total duration of the music editor
   */
  void setDuration(int duration);

  void setCombineNoteMap(Map<Integer, List<String>> notes);

  void setListener(ActionListener action, KeyListener key);

  void updateCurrentBeat(int beat);

}
