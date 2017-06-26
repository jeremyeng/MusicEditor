package cs3500.music.controller;

import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import javax.sound.midi.InvalidMidiDataException;

import cs3500.music.model.IMusicEditor;
import cs3500.music.model.Note;
import cs3500.music.model.ReadOnlyMusicEditorModel;
import cs3500.music.view.CombinedView;
import cs3500.music.view.GuiViewFrame;
import cs3500.music.view.IGuiView;
import cs3500.music.view.IMidiView;
import cs3500.music.view.IMusicEditorView;
import cs3500.music.view.IPracticeView;
import cs3500.music.view.MidiViewImpl;
import cs3500.music.view.PracticeView;

/**
 * Implements the IMusicEditorController interface and facilitates interaction between the model
 * and the view.
 */
public class MusicEditorController implements IMusicEditorController<Note> {
  private IMusicEditor<Note> model;
  private IMusicEditorView<Note> view;
  Timer timer = new Timer();
  TimerTask task = new AdvanceBeatWithTempo();
  private int beatStart = 0;

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
    keyPresses.put(KeyEvent.VK_Q, new LowerTempo());
    keyPresses.put(KeyEvent.VK_W, new RaiseTempo());

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
      combined.getMidiView().setActionBetweenBeats(new AdvanceBeat());
      combined.addKeyListener(kbd);
    }

    if (view instanceof MidiViewImpl) {
      MidiViewImpl midiView = (MidiViewImpl) view;
      midiView.setActionBetweenBeats(new AdvanceBeat());
      midiView.addKeyListener(kbd);
    }

    if (view instanceof PracticeView) {
      PracticeView practiceView = (PracticeView) view;
      practiceView.addKeyListener(kbd);
    }

  }

  private void configureMouseListener() {
    Map<Integer, Runnable> mouseClicks = new HashMap<>();

    mouseClicks.put(MouseEvent.MOUSE_PRESSED, new ContinuouslyAdvance());
    mouseClicks.put(MouseEvent.MOUSE_RELEASED, new PutNoteExtended());
    mouseClicks.put(MouseEvent.MOUSE_CLICKED, new PracticeClick());

    ViewMouseListener mouseListener = new ViewMouseListener();
    mouseListener.setMouseClicksMap(mouseClicks);

    if (view instanceof GuiViewFrame) {
      GuiViewFrame guiView = (GuiViewFrame) view;
      guiView.addMouseListener(mouseListener);
    } else if (view instanceof MidiViewImpl) {
      MidiViewImpl midiView = (MidiViewImpl) view;
      midiView.addMouseListener(mouseListener);
    } else if (view instanceof CombinedView) {
      CombinedView combinedView = (CombinedView) view;
      combinedView.addMouseListener(mouseListener);
    } else if (view instanceof PracticeView) {
      PracticeView practiceView = (PracticeView) view;
      practiceView.addMouseListener(mouseListener);
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
        beatStart = guiView.getCurrentBeat();
      }
      if (view instanceof CombinedView) {
        ((CombinedView) view).getGuiView().updateCurrentBeat(-1);
        beatStart = ((CombinedView) view).getGuiView().getCurrentBeat();
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
        beatStart = guiView.getCurrentBeat();
      }
      if (view instanceof CombinedView) {
        ((CombinedView) view).getGuiView().updateCurrentBeat(1);
        beatStart = ((CombinedView) view).getGuiView().getCurrentBeat();
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
        IGuiView guiView = ((CombinedView) view).getGuiView();
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
        IGuiView guiView = ((CombinedView) view).getGuiView();
        ((CombinedView) view).getGuiView().updateCurrentBeat(
                guiView.getDuration() - guiView.getCurrentBeat());
      }
    }
  }

  class Pause implements Runnable {
    @Override
    public void run() {
      if (view instanceof IMidiView) {
        IMidiView midiView = (IMidiView) view;
        if (midiView.isPaused()) {
          midiView.resume();
        } else {
          midiView.pause();
        }
      }

      if (view instanceof CombinedView) {
        CombinedView combinedView = (CombinedView) view;
        if (combinedView.getMidiView().isPaused()) {
          combinedView.getMidiView().resume();
        } else {
          combinedView.getMidiView().pause();
        }
      }
    }
  }

  class LowerTempo implements Runnable {

    @Override
    public void run() {
      model.setTempo(model.getTempo() - 1000);
    }
  }

  class RaiseTempo implements Runnable {

    @Override
    public void run() {
      model.setTempo(model.getTempo() + 1000);
    }
  }

  class ContinuouslyAdvance implements Runnable {
    @Override
    public void run() {
      task = new AdvanceBeatWithTempo();
      timer.scheduleAtFixedRate(task, 0, model.getTempo() / 1000);
    }
  }

  private class AdvanceBeatWithTempo extends TimerTask {
    public void run() {
      if (view instanceof GuiViewFrame) {
        GuiViewFrame guiView = (GuiViewFrame) view;
        guiView.updateCurrentBeat(1);

      }

      if (view instanceof CombinedView) {
        IGuiView guiView = ((CombinedView) view).getGuiView();
        guiView.updateCurrentBeat(1);
      }
    }
  }

  class PutNoteExtended implements Runnable {

    @Override
    public void run() {
      task.cancel();
      IGuiView guiView;

      if (view instanceof GuiViewFrame) {
        guiView = (GuiViewFrame) view;
      } else if (view instanceof CombinedView) {
        guiView = ((CombinedView) view).getGuiView();
      } else {
        return;
      }

      int noteNumber = guiView.noteClicked();
      int length = guiView.getCurrentBeat() - beatStart;
      model.addNote(new Note(noteNumber, 0), beatStart, length, 60);
      beatStart = guiView.getCurrentBeat();
      guiView.update(new ReadOnlyMusicEditorModel(model));
      guiView.updateCurrentBeat(0);
    }
  }

  class PracticeClick implements Runnable {
    @Override
    public void run() {
      if (view instanceof IPracticeView) {
        PracticeView practiceView = (PracticeView) view;
        practiceView.getNotesToClick(practiceView.getCurrentBeat()).remove(new Integer(practiceView.noteClicked()));
        if (practiceView.getNotesToClick(practiceView.getCurrentBeat()).size() == 0) {
          while (practiceView.getNotesToClick(practiceView.getCurrentBeat()).size() == 0) {
            practiceView.updateCurrentBeat(1);
          }
        }
      }
    }
  }

}
