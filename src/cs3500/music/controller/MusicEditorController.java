package cs3500.music.controller;

import com.sun.media.sound.MidiInDeviceProvider;

import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.sound.midi.InvalidMidiDataException;

import cs3500.music.model.IMusicEditor;
import cs3500.music.model.Note;
import cs3500.music.model.ReadOnlyMusicEditorModel;
import cs3500.music.view.CombinedView;
import cs3500.music.view.GuiViewFrame;
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
    this.view.update(new ReadOnlyMusicEditorModel(model));
    try {
      this.view.makeVisible();
    } catch (InvalidMidiDataException e) {
      e.printStackTrace();
    }
  }

  private void configureKeyBoardListener() {
    Map<Character, Runnable> keyTypes = new HashMap<>();
    Map<Integer, Runnable> keyPresses = new HashMap<>();
    Map<Integer, Runnable> keyReleases = new HashMap<>();

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

    if (view instanceof CombinedView) {
      CombinedView combined = (CombinedView) view;
      combined.addKeyListener(kbd);
    } else if (view instanceof MidiViewImpl) {
      MidiViewImpl midiView = (MidiViewImpl) view;
      midiView.addKeyListener(kbd);
    }

  }

  private void configureMouseListener() {
    Map<Integer, Runnable> mouseClicks = new HashMap<>();

    mouseClicks.put(MouseEvent.MOUSE_CLICKED, new PutNote());

    ViewMouseListener mouseListener = new ViewMouseListener();
    mouseListener.setMouseClicksMap(mouseClicks);

    if (view instanceof GuiViewFrame) {
      GuiViewFrame guiView = (GuiViewFrame) view;
      guiView.addMouseListener(mouseListener);
    } else if (view instanceof MidiViewImpl) {
      MidiViewImpl midiView = (MidiViewImpl) view;
      midiView.addMouseListener(mouseListener);
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
      if (view instanceof CombinedView) {
        ((CombinedView) view).getGuiView().updateCurrentBeat(-1);
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
      if (view instanceof CombinedView) {
        ((CombinedView) view).getGuiView().updateCurrentBeat(1);
      }
    }
  }

  class ToBeginning implements Runnable {
    @Override
    public void run() {
      if (view instanceof GuiViewFrame) {
        GuiViewFrame guiView = (GuiViewFrame) view;
        guiView.updateCurrentBeat(-1 * guiView.getCurrentBeat());
      }
      if (view instanceof CombinedView) {
        GuiViewFrame guiView = ((CombinedView) view).getGuiView();
        guiView.updateCurrentBeat(-1 * guiView.getCurrentBeat());
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
      if (view instanceof CombinedView) {
        GuiViewFrame guiView = ((CombinedView) view).getGuiView();
        ((CombinedView) view).getGuiView().updateCurrentBeat(
                guiView.getDuration() - guiView.getCurrentBeat());
      }
    }
  }

  class Pause implements Runnable {
    @Override
    public void run() {
      if (view instanceof CombinedView) {
        CombinedView combinedView = (CombinedView) view;
        if (combinedView.isPaused()) {
          combinedView.resume();
        } else {
          combinedView.pause();
        }
      }
    }
  }

  class PutNote implements Runnable {
    @Override
    public void run() {
      if (view instanceof GuiViewFrame) {
        GuiViewFrame guiView = (GuiViewFrame) view;
        int noteNumber = guiView.noteClicked();
        model.addNote(new Note(noteNumber, 0), guiView.getCurrentBeat(), 1, 60);
        guiView.update(new ReadOnlyMusicEditorModel(model));
        guiView.updateCurrentBeat(1);
      }
    }
  }

}
