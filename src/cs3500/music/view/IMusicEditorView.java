package cs3500.music.view;

import java.awt.event.ActionListener;
import java.awt.event.KeyListener;
import java.io.IOException;
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
  void makeVisible() throws InvalidMidiDataException, IOException;

  /**
   * Gives the view a model to operate on.
   * @param model the model that the view is going to operate on.
   */
  void update(IMusicEditor<K> model);

}
