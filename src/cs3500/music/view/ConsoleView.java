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
  public void playNote(List<List<List<Integer>>> info, long tempo) throws InvalidMidiDataException {

  }

  @Override
  public void setNoteRange(List<Note> noteRange) {

  }

  @Override
  public void setDuration(int duration) {

  }

  @Override
  public void setNoteMap(Map<Note, List<String>> notes) {

  }

  @Override
  public void update(IMusicEditor model) {
    this.model = model;
  }

  @Override
  public void setListener(ActionListener action, KeyListener key) {

  }

  @Override
  public void resetFocus() {

  }

  @Override
  public void updateCurrentBeat(int beat) {

  }

  @Override
  public void showErrorMessage() {

  }

  @Override
  public void refresh() {

  }

}
