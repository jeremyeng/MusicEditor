package cs3500.music.view;

import java.awt.event.KeyListener;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.MidiUnavailableException;
import javax.sound.midi.Receiver;
import javax.sound.midi.ShortMessage;
import javax.sound.midi.Synthesizer;
import javax.swing.JFrame;

import cs3500.music.model.IReadOnlyMusicEditor;

/**
 * Combines both the Visual GUI View and the MIDI Playback View.
 */
public class CombinedView extends JFrame implements IMusicEditorView{
  GuiViewFrame guiView;
  MidiViewImpl midiView;
  private boolean _paused = false;
  private final Synthesizer synth;
  private final Receiver receiver;
  private IReadOnlyMusicEditor model;


  public CombinedView(GuiViewFrame guiView, MidiViewImpl midiView) throws MidiUnavailableException {
    this.guiView = guiView;
    this.midiView = midiView;
    this.synth = MidiSystem.getSynthesizer();
    this.receiver = synth.getReceiver();
    this.synth.open();
  }

  @Override
  public void makeVisible() throws InvalidMidiDataException, IOException {
    this.guiView.makeVisible();
    this.playNote(this.model.getMidiInfo(), this.model.getTempo());

  }

  @Override
  public void update(IReadOnlyMusicEditor model) {
    this.model = model;
    this.guiView.update(model);
    this.midiView.update(model);
  }

  @Override
  public void addKeyListener(KeyListener key) {
    this.guiView.addKeyListener(key);
    this.midiView.addKeyListener(key);
  }

  public GuiViewFrame getGuiView() {
    return this.guiView;
  }

  public MidiViewImpl getMidiView() {
    return this.midiView;
  }



  public void playNote(List<List<List<Integer>>> info, long tempo) throws InvalidMidiDataException {
    long start = this.synth.getMicrosecondPosition();
    for (int beat = 0; beat < info.size(); beat++) {
      for (List<Integer> l : info.get(beat)) {
        synchronized (this) {
          while (_paused) {
            System.out.println("Im paused!");
            try {
              wait();
            } catch (InterruptedException e) {
              e.printStackTrace();
            }
          }
        }
        this.receiver.send(new ShortMessage(ShortMessage.PROGRAM_CHANGE, l.get(1), l.get(1),
                l.get(1)), -1);
        this.receiver.send(new ShortMessage(ShortMessage.NOTE_ON, l.get(1), l.get(2), l.get(3)),
                start + beat * tempo);
        this.receiver.send(new ShortMessage(ShortMessage.NOTE_OFF, l.get(1), l.get(2), l.get(3)),
                start + (beat * tempo) + (l.get(4) * tempo));
      }
      try {
        Thread.sleep(tempo / 1000);
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
//      this.guiView.updateCurrentBeat(1);
    }
  }
}
