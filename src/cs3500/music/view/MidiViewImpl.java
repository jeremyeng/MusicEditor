package cs3500.music.view;

import java.awt.event.ActionListener;
import java.awt.event.KeyListener;
import java.util.List;
import java.util.Map;

import javax.sound.midi.*;

import cs3500.music.model.IMusicEditor;
import cs3500.music.model.MusicEditorModel;
import cs3500.music.model.Note;

/**
 * A skeleton for MIDI playback
 */
public class MidiViewImpl implements IMidiView {
  private final Synthesizer synth;
  private final Receiver receiver;
  private IMusicEditor model;


  public MidiViewImpl() throws MidiUnavailableException {
    this.synth = MidiSystem.getSynthesizer();
    this.receiver = synth.getReceiver();
    this.synth.open();
  }


  /**
   * Relevant classes and methods from the javax.sound.midi library:
   * <ul>
   *  <li>{@link MidiSystem#getSynthesizer()}</li>
   *  <li>{@link Synthesizer}
   *    <ul>
   *      <li>{@link Synthesizer#open()}</li>
   *      <li>{@link Synthesizer#getReceiver()}</li>
   *      <li>{@link Synthesizer#getChannels()}</li>
   *    </ul>
   *  </li>
   *  <li>{@link Receiver}
   *    <ul>
   *      <li>{@link Receiver#send(MidiMessage, long)}</li>
   *      <li>{@link Receiver#close()}</li>
   *    </ul>
   *  </li>
   *  <li>{@link MidiMessage}</li>
   *  <li>{@link ShortMessage}</li>
   *  <li>{@link MidiChannel}
   *    <ul>
   *      <li>{@link MidiChannel#getProgram()}</li>
   *      <li>{@link MidiChannel#programChange(int)}</li>
   *    </ul>
   *  </li>
   * </ul>
   * @see <a href="https://en.wikipedia.org/wiki/General_MIDI">
   *   https://en.wikipedia.org/wiki/General_MIDI
   *   </a>
   */


  @Override
  public void makeVisible() throws InvalidMidiDataException {
    this.playNote(this.model.getMidiInfo(), this.model.getTempo());
  }

  @Override
  public void playNote(List<List<List<Integer>>> info, long tempo) throws InvalidMidiDataException {
    for (int beat = 0; beat < info.size(); beat++) {
      for (List<Integer> l: info.get(beat)) {
        this.receiver.send(new ShortMessage(ShortMessage.PROGRAM_CHANGE, l.get(1), l.get(1), l.get(1)), -1);
        this.receiver.send(new ShortMessage(ShortMessage.NOTE_ON, l.get(1), l.get(2), l.get(3)), this.synth.getMicrosecondPosition());
        this.receiver.send(new ShortMessage(ShortMessage.NOTE_OFF, l.get(1), l.get(2), l.get(3)), this.synth.getMicrosecondPosition() + l.get(4) * tempo);
      }
      try {
        Thread.sleep(tempo / 1000);
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
    }
  }

  @Override
  public void update(IMusicEditor model) {
    this.model = model;
  }

  @Override
  public void setDuration(int duration) {

  }

  @Override
  public void setListener(ActionListener action, KeyListener key) {

  }

  @Override
  public void updateCurrentBeat(int beat) {

  }

  @Override
  public void setCombineNoteMap(Map notes) {

  }


}
