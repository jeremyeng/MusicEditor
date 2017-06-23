package cs3500.music.view;

import java.util.List;

import javax.sound.midi.InvalidMidiDataException;

/**
 * An interface designed for a midi view that plays notes base on the information given.
 */
public interface IMidiView<K> extends IMusicEditorView<K> {

  /**
   * A method that plays a note base on the information given by a model.
   * @param info the most nested list contains 5 integers that represents a midi message, the one
   *             outer represents all the midi messages in one beat, the most outer list represent
   *             the list of midi messages in all beats.
   * @param tempo the speed of the piece in microseconds per beat.
   * @throws InvalidMidiDataException if no midi data
   */
  void playNote(List<List<List<Integer>>> info, long tempo) throws InvalidMidiDataException;

  /**
   * Resumes playback of the MidiView.
   */
  void resume();

  /**
   * Pauses playback of the MidiView.
   */
  void pause();

  /**
   * Return true if the music is currently paused and false otherwise.
   * @return true if the music is true, false otherwise.
   */
  boolean isPaused();

  /**
   * Pass in a runnable to be executed between every beat.
   * @param action the runnable to be run.
   */
  void setActionBetweenBeats(Runnable action);
}
