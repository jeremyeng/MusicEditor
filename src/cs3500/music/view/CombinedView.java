package cs3500.music.view;

import java.awt.event.KeyListener;
import java.awt.event.MouseListener;
import java.io.IOException;

import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MidiUnavailableException;
import javax.swing.JFrame;

import cs3500.music.model.IReadOnlyMusicEditor;
import cs3500.music.model.Note;

/**
 * Combines both the Visual GUI View and the MIDI Playback View.
 */
public class CombinedView extends JFrame implements IMusicEditorView<Note>{
  GuiViewFrame guiView;
  MidiViewImpl midiView;
  private IReadOnlyMusicEditor model;


  public CombinedView(GuiViewFrame guiView, MidiViewImpl midiView) throws MidiUnavailableException {
    this.guiView = guiView;
    this.midiView = midiView;
  }

  @Override
  public void makeVisible() throws InvalidMidiDataException, IOException {
    this.guiView.makeVisible();
    this.midiView.makeVisible();
  }

  @Override
  public void update(IReadOnlyMusicEditor model) {
    this.model = model;
    this.guiView.update(model);
    this.midiView.update(model);
  }

  @Override
  public void addKeyListener(KeyListener keyListener) {
    this.guiView.addKeyListener(keyListener);
    this.midiView.addKeyListener(keyListener);
  }

  @Override
  public void addMouseListener(MouseListener mouseListener) {
    this.guiView.addMouseListener(mouseListener);
    this.midiView.addMouseListener(mouseListener);
  }

  /**
   * Gets the GUI view that comprises the combined view.
   * @return an instance of the GUI View implementation.
   */
  public GuiViewFrame getGuiView() {
    return this.guiView;
  }

  /**
   * Gets the MIDI view that comprisese the combined view.
   * @return an instance of the MIDI View implementation.
   */
  public MidiViewImpl getMidiView() {
    return this.midiView;
  }
}
