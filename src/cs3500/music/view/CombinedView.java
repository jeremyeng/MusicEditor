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
public class CombinedView extends JFrame implements ICombinedView<Note>{
  private IGuiView guiView;
  private  IMidiView midiView;
  private IReadOnlyMusicEditor model;


  /**
   * Creates an instance of a view comprised of both visual and audio representations of music.
   * @param guiView the visual representation.
   * @param midiView the audio representation.
   * @throws MidiUnavailableException if the midi is unavailable.
   */
  public CombinedView(IGuiView guiView, IMidiView midiView) throws MidiUnavailableException {
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


  @Override
  public IGuiView getGuiView() {
    return this.guiView;
  }


  @Override
  public IMidiView getMidiView() {
    return this.midiView;
  }
}
