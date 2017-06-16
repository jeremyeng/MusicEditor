package cs3500.music.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.TreeMap;

import cs3500.music.model.IMusicEditor;
import cs3500.music.model.Note;
import cs3500.music.view.IMusicEditorView;

/**
 * Implements the IMusicEditorController interface and facilitates interaction between the model
 * and the view.
 */
public class MusicEditorController implements IMusicEditorController<Note>, ActionListener, KeyListener{
  private IMusicEditor<Note> model;
  private IMusicEditorView<Note> view;

  public MusicEditorController(IMusicEditor<Note> model, IMusicEditorView<Note> view) {
    this.model = model;
    this.view = view;
  }

  @Override
  public void go() {
    this.view.setListener(this,this);
    this.view.setDuration(model.getDuration());
    this.view.setNoteRange(model.getNoteRange());
    TreeMap<Note, List<String>> noteMap = new TreeMap<>();
    for (Note note : model.getNoteRange()) {
      List<String> stateList = new ArrayList<>();
      for (int i = 0; i < model.getDuration(); i++) {
        stateList.add(model.getNoteState(note,i));
      }
      noteMap.put(note,stateList);
    }
    this.view.setNoteMap(noteMap);
    this.view.setCombineNoteMap(model.getCombinedNoteMap());
    this.view.makeVisible();
  }

  @Override
  public void actionPerformed(ActionEvent e) {

  }

  @Override
  public void keyTyped(KeyEvent e) {

  }

  @Override
  public void keyPressed(KeyEvent e) {
    if (e.getKeyCode() == KeyEvent.VK_LEFT) {
      this.view.updateCurrentBeat(-1);
    }
    if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
      this.view.updateCurrentBeat(1);
    }
  }

  @Override
  public void keyReleased(KeyEvent e) {

  }
}
