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
  public void setDuration(int duration) {

  }

  @Override
  public void setCombineNoteMap(Map<Integer, List<String>> notes) {

  }

  @Override
  public void setListener(ActionListener action, KeyListener key) {

  }

  @Override
  public void updateCurrentBeat(int beat) {

  }

  @Override
  public void update(IMusicEditor model) {
    this.model = model;
  }

}
