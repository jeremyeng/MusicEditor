package cs3500.music.view;

import java.awt.event.ActionListener;
import java.util.List;

import javax.sound.midi.InvalidMidiDataException;

/**
 * Defines the behavior for.
 */
public interface IMusicEditorView<K> {

  void initialize();

  void playNote() throws InvalidMidiDataException;

  void makeVisible();

  void setCommandButtonListener(ActionListener actionEvent);

  void setNoteRange(List<K> noteRange);

  void setDuration(int duration);

  void showErrorMessage();

  void refresh();
}
