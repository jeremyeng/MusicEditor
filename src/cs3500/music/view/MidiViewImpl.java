package cs3500.music.view;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseListener;
import java.util.List;


import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MidiChannel;
import javax.sound.midi.MidiMessage;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.MidiUnavailableException;
import javax.sound.midi.Receiver;
import javax.sound.midi.ShortMessage;
import javax.sound.midi.Synthesizer;
import javax.swing.*;

import cs3500.music.controller.ViewKeyBoardListener;
import cs3500.music.model.IReadOnlyMusicEditor;
import cs3500.music.model.Note;

/**
 * A skeleton for MIDI playback.
 */
public class MidiViewImpl extends JComponent implements IMidiView<Note> {
  private boolean _paused = false;
  private final Synthesizer synth;
  private final Receiver receiver;
  private IReadOnlyMusicEditor model;


  /**
   * Creates a new MidiViewImpl with a synthesizer and receiver from the built-in java Midi.
   */
  public MidiViewImpl() throws MidiUnavailableException {
    this.synth = MidiSystem.getSynthesizer();
    this.receiver = synth.getReceiver();
    this.synth.open();
  }

  /**
   * Creates a new MidiViewImpl with a synthesizer in.
   * Used for testing purposes.
   *
   * @param synth the synthesizer to use.
   */
  public MidiViewImpl(Synthesizer synth) throws MidiUnavailableException {
    this.synth = synth;
    this.receiver = this.synth.getReceiver();
  }


  /**
   * Relevant classes and methods from the javax.sound.midi library:
   * <ul>
   * <li>{@link MidiSystem#getSynthesizer()}</li>
   * <li>{@link Synthesizer}
   * <ul>
   * <li>{@link Synthesizer#open()}</li>
   * <li>{@link Synthesizer#getReceiver()}</li>
   * <li>{@link Synthesizer#getChannels()}</li>
   * </ul>
   * </li>
   * <li>{@link Receiver}
   * <ul>
   * <li>{@link Receiver#send(MidiMessage, long)}</li>
   * <li>{@link Receiver#close()}</li>
   * </ul>
   * </li>
   * <li>{@link MidiMessage}</li>
   * <li>{@link ShortMessage}</li>
   * <li>{@link MidiChannel}
   * <ul>
   * <li>{@link MidiChannel#getProgram()}</li>
   * <li>{@link MidiChannel#programChange(int)}</li>
   * </ul>
   * </li>
   * </ul>
   *
   * @see <a href="https://en.wikipedia.org/wiki/General_MIDI">
   *   https://en.wikipedia.org/wiki/General_MIDI
   * </a>
   */


  @Override
  public void makeVisible() throws InvalidMidiDataException {
    this.setVisible(true);
    this.playNote(this.model.getMidiInfo(), this.model.getTempo());
  }

  @Override
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
    }
  }

  @Override
  public synchronized void resume() {
    System.out.println("resumed");
    this._paused = false;
    notifyAll();
  }

  @Override
  public synchronized void pause() {
    System.out.println("paused");
    this._paused = true;
    notifyAll();
  }

  @Override
  public void update(IReadOnlyMusicEditor model) {
    this.model = model;
  }

  @Override
  public boolean isPaused() {
    return this._paused;
  }



}
