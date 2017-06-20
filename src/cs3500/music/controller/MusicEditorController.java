package cs3500.music.controller;

import com.sun.media.sound.MidiInDeviceProvider;

import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.sound.midi.InvalidMidiDataException;

import cs3500.music.model.IMusicEditor;
import cs3500.music.model.Note;
import cs3500.music.model.ReadOnlyMusicEditorModel;
import cs3500.music.view.GuiViewFrame;
import cs3500.music.view.IGuiView;
import cs3500.music.view.IMidiView;
import cs3500.music.view.IMusicEditorView;
import cs3500.music.view.MidiViewImpl;

/**
 * Implements the IMusicEditorController interface and facilitates interaction between the model
 * and the view.
 */
public class MusicEditorController implements IMusicEditorController<Note> {
  private IMusicEditor<Note> model;
  private IMusicEditorView<Note> view;

  /**
   * Constructs an instance of a controller.
   *
   * @param model the model in which the controller is going to operate on.
   * @param view  the view in which the controller is going to receive command from
   */
  public MusicEditorController(IMusicEditor<Note> model, IMusicEditorView<Note> view) {
    this.model = model;
    this.view = view;
    configureKeyBoardListener();
    configureMouseListener();
  }

  @Override
  public void execute() throws IOException {
    if (this.view instanceof IGuiView) {
      IGuiView guiView = (IGuiView) this.view;
      guiView.setDuration(model.getDuration());
      TreeMap<Note, List<String>> noteMap = new TreeMap<>();
      for (Note note : model.getNoteRange()) {
        List<String> stateList = new ArrayList<>();
        for (int i = 0; i < model.getDuration(); i++) {
          stateList.add(model.getNoteState(note, i));
        }
        noteMap.put(note, stateList);
      }
      guiView.setCombineNoteMap(model.getCombinedNoteMap());
    }
    this.view.update(new ReadOnlyMusicEditorModel(model));
    try {
      this.view.makeVisible();
    } catch (InvalidMidiDataException e) {
      e.printStackTrace();
    }
  }

  private void configureKeyBoardListener() {
    Map<Character, Runnable> keyTypes = new HashMap<>();
    Map<Integer,Runnable> keyPresses = new HashMap<>();
    Map<Integer,Runnable> keyReleases = new HashMap<>();

    keyPresses.put(KeyEvent.VK_LEFT, new RetractBeat());
    keyPresses.put(KeyEvent.VK_RIGHT, new AdvanceBeat());
    keyPresses.put(KeyEvent.VK_HOME, new ToBeginning());
    keyPresses.put(KeyEvent.VK_END, new ToEnd());
    keyPresses.put(KeyEvent.VK_P, new Pause());

    ViewKeyBoardListener kbd = new ViewKeyBoardListener();
    kbd.setKeyPressedMap(keyPresses);
    kbd.setKeyReleasedMap(keyReleases);
    kbd.setKeyTypedMap(keyTypes);

    if (view instanceof GuiViewFrame) {
      GuiViewFrame guiView = (GuiViewFrame) view;
      guiView.addKeyListener(kbd);
    }

    else if (view instanceof MidiViewImpl) {
      MidiViewImpl midiView = (MidiViewImpl) view;
      midiView.addKeyListener(kbd);
    }

  }

  private void configureMouseListener() {
    if (view instanceof GuiViewFrame) {
      GuiViewFrame guiView = (GuiViewFrame) view;
      guiView.addMouseListener(new ViewMouseListener(guiView));
    }
    else if (view instanceof MidiViewImpl) {
      MidiViewImpl midiView = (MidiViewImpl) view;
      midiView.addMouseListener(new ViewMouseListener(midiView));
    }
  }


  /**
   * A functional class that allows the view to retract a beat.
   */
  class RetractBeat implements Runnable {
    @Override
    public void run() {
      if (view instanceof GuiViewFrame) {
        GuiViewFrame guiView = (GuiViewFrame) view;
        guiView.updateCurrentBeat(-1);
      }
    }
  }

  /**
   * A functional class that allows the view to advance a beat.
   */
  class AdvanceBeat implements Runnable {
    @Override
    public void run() {
      if (view instanceof GuiViewFrame) {
        GuiViewFrame guiView = (GuiViewFrame) view;
        guiView.updateCurrentBeat(1);
      }
    }
  }

  class ToBeginning implements Runnable {
    @Override
    public void run() {
      if (view instanceof GuiViewFrame) {
        GuiViewFrame guiView = (GuiViewFrame) view;
        guiView.updateCurrentBeat( -1 * guiView.getCurrentBeat());
      }
    }
  }

  class ToEnd implements Runnable {
    @Override
    public void run() {
      if (view instanceof GuiViewFrame) {
        GuiViewFrame guiView = (GuiViewFrame) view;
        guiView.updateCurrentBeat(guiView.getDuration() - guiView.getCurrentBeat());
      }
    }
  }

  class Pause implements Runnable {
    @Override
    public void run() {
      System.out.println("Im running!");
      if (view instanceof MidiViewImpl) {
         MidiViewImpl midiView = (MidiViewImpl) view;
         if (midiView.isPaused()) {
           System.out.println("Im resuming!");
           midiView.resume();
         }
         else {
           midiView.pause();
           System.out.println("Im pausing!");
         }
      }
    }
  }

}
