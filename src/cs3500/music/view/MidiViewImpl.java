package cs3500.music.view;

import java.awt.*;
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
 * An implementation for a MIDI view that plays back notes using synthesizer and a receiver.
 */
public class MidiViewImpl extends JFrame implements IMidiView<Note> {
  private boolean _paused = false;
  private final Synthesizer synth;
  private final Receiver receiver;
  private IReadOnlyMusicEditor model;
  private Runnable action;


  /**
   * Creates a new MidiViewImpl with a synthesizer and receiver from the built-in java Midi.
   */
  public MidiViewImpl() throws MidiUnavailableException {
    super();
    this.setTitle("Midi");
    this.setSize(100, 100);
    this.setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
    this.setFocusable(true);
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
   * @see <a href="https://en.wikipedia.org/wiki/General_MIDI"> https://en.wikipedia.org/wiki/General_MIDI
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
    long timePaused = 0;
    for (int beat = 0; beat < info.size(); beat++) {
      long startPause = this.synth.getMicrosecondPosition();
      tempo = model.getTempo();
      synchronized (this) {
        while (_paused) {
          try {
            wait();
            timePaused += this.synth.getMicrosecondPosition() - startPause;
          } catch (InterruptedException e) {
            e.printStackTrace();
          }

        }
      }
      for (List<Integer> l : info.get(beat)) {

        this.receiver.send(new ShortMessage(ShortMessage.PROGRAM_CHANGE, l.get(1), l.get(1),
                l.get(1)), -1);
        this.receiver.send(new ShortMessage(ShortMessage.NOTE_ON, l.get(1), l.get(2), l.get(3)),
                start + timePaused + beat * tempo);
        this.receiver.send(new ShortMessage(ShortMessage.NOTE_OFF, l.get(1), l.get(2), l.get(3)),
                start + timePaused + (beat * tempo) + (l.get(4) * tempo));
      }

      startPause = this.synth.getMicrosecondPosition();
      this.action.run();
      timePaused += this.synth.getMicrosecondPosition() - startPause;
      try {
        Thread.sleep((tempo / 1000));
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
    }
  }

  @Override
  public synchronized void resume() {
    this._paused = false;
    notifyAll();
  }

  @Override
  public synchronized void pause() {
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

  @Override
  public void setActionBetweenBeats(Runnable action) {
    this.action = action;
  }

  @Override
  public boolean isFocusable() {
    return true;
  }
}
