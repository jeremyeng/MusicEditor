package cs3500.music.view;

import javax.sound.midi.InvalidMidiDataException;

/**
 * Defines the behavior for.
 */
public interface IMusicEditorView {

  void initialize();

  void playNote() throws InvalidMidiDataException;

  void showText();

  void showVisual();


}
