package cs3500.music.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
public class MusicEditorController implements IMusicEditorController<Note>, ActionListener{
  private IMusicEditor<Note> model;
  private IMusicEditorView view;

  public MusicEditorController(IMusicEditor<Note> model, IMusicEditorView view) {
    this.model = model;
    this.view = view;
  }

  @Override
  public String processCommand(String command) {
    return null;
  }

  @Override
  public void go() {
    this.view.setCommandButtonListener(this);
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
    this.view.makeVisible();
  }

  @Override
  public void actionPerformed(ActionEvent e) {

  }
}
