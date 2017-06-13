package cs3500.music.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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
    this.view.makeVisible();
  }

  @Override
  public void actionPerformed(ActionEvent e) {

  }
}
