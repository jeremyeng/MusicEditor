package cs3500.music.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.TreeMap;

import javax.sound.midi.InvalidMidiDataException;

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
  public String processCommand(String command) {
    return null;
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
    this.view.update(this.model);
    try {
      this.view.makeVisible();
    } catch (InvalidMidiDataException e) {
      e.printStackTrace();
    }
  }

  @Override
  public void actionPerformed(ActionEvent e) {
    System.out.println("some action performed!");
  }

  @Override
  public void keyTyped(KeyEvent e) {
    System.out.print("key typed!");
  }

  @Override
  public void keyPressed(KeyEvent e) {
    switch (e.getKeyCode()) {
      case KeyEvent.VK_LEFT:
        this.view.updateCurrentBeat(-1);
      break;
      case KeyEvent.VK_RIGHT: this.view.updateCurrentBeat(1); break;
      default: throw new IllegalArgumentException("Unsupported Key!");
    }
  }

  @Override
  public void keyReleased(KeyEvent e) {

  }
}
