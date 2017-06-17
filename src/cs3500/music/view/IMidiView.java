package cs3500.music.view;

import java.util.List;

import javax.sound.midi.InvalidMidiDataException;

/**
 * Created by Hoyin on 6/16/2017.
 */

/**
 * An interface designed for a midi view that plays notes base on the information given.
 */
public interface IMidiView extends IMusicEditorView {

  void playNote(List<List<List<Integer>>> info, long tempo) throws InvalidMidiDataException;

}
