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

  /**
   * Forces views to set a listener for keyboard input.
   * @param keyListener the processor of keyboard input.
   */
  void addKeyListener(KeyListener keyListener);

  /**
   * Forces views to set a listener for mouse input.
   * @param mouseListener the processor of mouse input.
   */
  void addMouseListener(MouseListener mouseListener);

}
