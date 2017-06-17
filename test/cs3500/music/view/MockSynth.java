package cs3500.music.view;

import java.util.List;

import javax.sound.midi.Instrument;
import javax.sound.midi.MidiChannel;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.MidiUnavailableException;
import javax.sound.midi.Patch;
import javax.sound.midi.Receiver;
import javax.sound.midi.Soundbank;
import javax.sound.midi.Synthesizer;
import javax.sound.midi.Transmitter;
import javax.sound.midi.VoiceStatus;

/**
 * Emulates a Java MidiDevice but only supports the getReceiver() method that in returns a
 * Mock Java Receiver.
 */
public class MockSynth implements Synthesizer {

  private final Synthesizer realSynth;
  private final StringBuilder log;
  private final long tempo;

  /**
   * Creates a new mock synthesizer that uses a real synthesizer for some of its funcitons
   * @throws MidiUnavailableException if the synthesizer cannot be retrieved.
   */
  public MockSynth(StringBuilder log, long tempo) throws MidiUnavailableException {
    this.realSynth = MidiSystem.getSynthesizer();
    this.log = log;
    this.tempo  = tempo;
  }

  @Override
  public Info getDeviceInfo() {
    return null;
  }

  @Override
  public void open() throws MidiUnavailableException {
    throw new UnsupportedOperationException("I'm not a real MidiDevice!");
  }

  @Override
  public void close() {
    throw new UnsupportedOperationException("I'm not a real MidiDevice!");
  }

  @Override
  public boolean isOpen() {
    throw new UnsupportedOperationException("I'm not a real MidiDevice!");
  }

  @Override
  public long getMicrosecondPosition() {
    return this.realSynth.getMicrosecondPosition();
  }

  @Override
  public int getMaxReceivers() {
    throw new UnsupportedOperationException("I'm not a real MidiDevice!");
  }

  @Override
  public int getMaxTransmitters() {
    throw new UnsupportedOperationException("I'm not a real MidiDevice!");
  }

  @Override
  public Receiver getReceiver() throws MidiUnavailableException {
    return new MockReceiver(this.log ,this.tempo);
  }

  @Override
  public List<Receiver> getReceivers() {
    throw new UnsupportedOperationException("I'm not a real MidiDevice!");
  }

  @Override
  public Transmitter getTransmitter() throws MidiUnavailableException {
    throw new UnsupportedOperationException("I'm not a real MidiDevice!");
  }

  @Override
  public List<Transmitter> getTransmitters() {
    throw new UnsupportedOperationException("I'm not a real MidiDevice!");
  }

  @Override
  public int getMaxPolyphony() {
    return 0;
  }

  @Override
  public long getLatency() {
    return 0;
  }

  @Override
  public MidiChannel[] getChannels() {
    return new MidiChannel[0];
  }

  @Override
  public VoiceStatus[] getVoiceStatus() {
    return new VoiceStatus[0];
  }

  @Override
  public boolean isSoundbankSupported(Soundbank soundbank) {
    return false;
  }

  @Override
  public boolean loadInstrument(Instrument instrument) {
    return false;
  }

  @Override
  public void unloadInstrument(Instrument instrument) {

  }

  @Override
  public boolean remapInstrument(Instrument from, Instrument to) {
    return false;
  }

  @Override
  public Soundbank getDefaultSoundbank() {
    return null;
  }

  @Override
  public Instrument[] getAvailableInstruments() {
    return new Instrument[0];
  }

  @Override
  public Instrument[] getLoadedInstruments() {
    return new Instrument[0];
  }

  @Override
  public boolean loadAllInstruments(Soundbank soundbank) {
    return false;
  }

  @Override
  public void unloadAllInstruments(Soundbank soundbank) {

  }

  @Override
  public boolean loadInstruments(Soundbank soundbank, Patch[] patchList) {
    return false;
  }

  @Override
  public void unloadInstruments(Soundbank soundbank, Patch[] patchList) {

  }
}