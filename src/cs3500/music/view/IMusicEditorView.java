package cs3500.music.view;

import java.awt.event.KeyListener;
import java.awt.event.MouseListener;
import java.io.IOException;

import javax.sound.midi.InvalidMidiDataException;

import cs3500.music.model.IReadOnlyMusicEditor;

/**
 * Defines the behavior for the music editor.
 */
public interface IMusicEditorView<K> {

  /**
   * Makes the music editor visible to the user.
   */
  void makeVisible() throws InvalidMidiDataException, IOException;

  /**
   * Gives the view a model to operate on.
   * @param model the model that the view is going to operate on.
   */
  void update(IReadOnlyMusicEditor<K> model);

  void addKeyListener(KeyListener key);

  void addMouseListener(MouseListener mouse);

}
