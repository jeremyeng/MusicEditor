package cs3500.music.view;

import java.awt.event.ActionListener;
import java.awt.event.KeyListener;
import java.util.List;
import java.util.Map;

import javax.sound.midi.InvalidMidiDataException;

import cs3500.music.model.IMusicEditor;
import cs3500.music.model.MusicEditorModel;
import cs3500.music.model.Note;

/**
 * Represents the music editor as text printed to the console.
 */
public class ConsoleView implements IMusicEditorView<Note> {

  private IMusicEditor model;

  @Override
  public void makeVisible() {
    System.out.println(this.model.getState());
  }

  @Override
  public void update(IMusicEditor model) {
    this.model = model;
  }

}
