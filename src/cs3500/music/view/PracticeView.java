package cs3500.music.view;

import java.awt.event.KeyListener;
import java.awt.event.MouseListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.sound.midi.InvalidMidiDataException;
import javax.swing.*;

import cs3500.music.model.IReadOnlyMusicEditor;
import cs3500.music.model.Note;

/**
 * Created by Hoyin on 6/25/2017.
 */
public class PracticeView extends JFrame implements IPracticeView {

  private IGuiView guiView;
  private IReadOnlyMusicEditor<Note> model;
  private List<Integer> notesPressed;

  public PracticeView(IGuiView guiview) {
    this.guiView = guiview;
    this.notesPressed = new ArrayList<Integer>();
  }


  @Override
  public void makeVisible() throws InvalidMidiDataException, IOException {
    this.setVisible(true);
    this.guiView.makeVisible();
  }

  @Override
  public void update(IReadOnlyMusicEditor model) {
    this.model = model;
    this.guiView.update(model);
  }


  @Override
  public void setDuration(int duration) {
    this.guiView.setDuration(duration);
  }

  @Override
  public void setCombineNoteMap(Map<Integer, List<String>> notes) {
    this.guiView.setCombineNoteMap(notes);
  }

  @Override
  public void updateCurrentBeat(int beat) {
    this.guiView.updateCurrentBeat(beat);
  }

  @Override
  public int getDuration() {
    return this.guiView.getDuration();
  }

  @Override
  public int getCurrentBeat() {
    return this.guiView.getCurrentBeat();
  }

  @Override
  public int noteClicked() {
    return this.guiView.noteClicked();
  }

  @Override
  public List<Integer> getNotesToClick(int beat) {
    List<Integer> listToReturn = new ArrayList<>();
    for (Map.Entry<Integer, String> entry : this.model.getAllStatesAtBeat(beat).entrySet()) {
      if (entry.getValue().equals("start") || entry.getValue().equals("continue")) {
        listToReturn.add(entry.getKey());
      }
    }
    listToReturn.removeAll(this.notesPressed);
    System.out.println("getnoteclck");
    return listToReturn;
  }

  @Override
  public IGuiView getGuiView() {
    return this.guiView;
  }

  @Override
  public void addKeyListener(KeyListener keyListener) {
    this.guiView.addKeyListener(keyListener);
  }

  @Override
  public void addMouseListener(MouseListener mouseListener) {
    this.guiView.addMouseListener(mouseListener);
  }


}
