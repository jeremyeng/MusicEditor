package cs3500.music.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.TreeMap;

import javax.sound.midi.InvalidMidiDataException;

import cs3500.music.model.IMusicEditor;
import cs3500.music.model.Note;
import cs3500.music.view.GuiViewFrame;
import cs3500.music.view.IGuiView;
import cs3500.music.view.IMidiView;
import cs3500.music.view.IMusicEditorView;
import cs3500.music.view.MidiViewImpl;

/**
 * Implements the IMusicEditorController interface and facilitates interaction between the model
 * and the view.
 */
public class MusicEditorController implements IMusicEditorController<Note>, ActionListener, KeyListener{
  private IMusicEditor<Note> model;
  private IMusicEditorView<Note> view;

  /**
   * Constructs an instance of a controller.
   * @param model the model in which the controller is going to operate on.
   * @param view the view in which the controller is going to receive command from
   */
  public MusicEditorController(IMusicEditor<Note> model, IMusicEditorView<Note> view) {
    this.model = model;
    this.view = view;
  }

  @Override
  public void go() throws IOException {
    if (this.view instanceof IGuiView) {
      IGuiView guiView = (IGuiView) this.view;
      guiView.setListener(this,this);
      guiView.setDuration(model.getDuration());
      TreeMap<Note, List<String>> noteMap = new TreeMap<>();
      for (Note note : model.getNoteRange()) {
        List<String> stateList = new ArrayList<>();
        for (int i = 0; i < model.getDuration(); i++) {
          stateList.add(model.getNoteState(note,i));
        }
        noteMap.put(note,stateList);
      }
      guiView.setCombineNoteMap(model.getCombinedNoteMap());
    }
    this.view.update(this.model);
    try {
      this.view.makeVisible();
    } catch (InvalidMidiDataException e) {
      e.printStackTrace();
    }
  }

  @Override
  public void actionPerformed(ActionEvent e) {

  }

  @Override
  public void keyTyped(KeyEvent e) {

  }

  @Override
  public void keyPressed(KeyEvent e) {
    if (e.getKeyCode() == KeyEvent.VK_LEFT && this.view instanceof IGuiView) {
      IGuiView guiView = (IGuiView) this.view;
      guiView.updateCurrentBeat(-1);
    }
    if (e.getKeyCode() == KeyEvent.VK_RIGHT && this.view instanceof IGuiView) {
      IGuiView guiView = (IGuiView) this.view;
      guiView.updateCurrentBeat(1);
    }
  }

  @Override
  public void keyReleased(KeyEvent e) {

  }
}
