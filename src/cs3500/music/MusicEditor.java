package cs3500.music;

import cs3500.music.controller.IMusicEditorController;
import cs3500.music.controller.MusicEditorController;
import cs3500.music.model.IMusicEditor;
import cs3500.music.model.MusicEditorModel;
import cs3500.music.model.Note;
import cs3500.music.util.CompositionBuilder;
import cs3500.music.util.MusicReader;
import cs3500.music.view.IMusicEditorView;
import cs3500.music.view.ViewFactory;

import java.io.FileReader;
import java.io.IOException;

import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MidiEvent;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.MidiUnavailableException;
import javax.sound.midi.Sequence;
import javax.sound.midi.Sequencer;
import javax.sound.midi.ShortMessage;
import javax.sound.midi.Track;

/**
 * Initialize the music editor. The type of view can be selected by changing the argument of
 * getView, and the file can be selected by changing the argument of FileReader.
 */
public class MusicEditor {
  /**
   * Main method for our MusicEditor.
   * @param args two string arguments that specify which file to play and which view to create.
   * @throws IOException if there invalid input or output.
   * @throws InvalidMidiDataException if midi data is invalid.
   * @throws MidiUnavailableException if the midi is unavailable.
   */
  public static void main(String[] args) throws IOException, InvalidMidiDataException,
          MidiUnavailableException {
    IMusicEditorView view = ViewFactory.getView(
            args[1]
    );
    CompositionBuilder<IMusicEditor<Note>> builder = new MusicEditorModel.Builder();
    FileReader file = new FileReader(args[0]);
    IMusicEditor<Note> model = MusicReader.parseFile(file, builder);
    IMusicEditorController controller = new MusicEditorController(model, view);
    controller.execute();
//    Sequencer sequencer = MidiSystem.getSequencer();
//    Sequence s = new Sequence(Sequence.PPQ, 2000);
//    Track t1 = s.createTrack();
//    t1.add(new MidiEvent(new ShortMessage(ShortMessage.NOTE_ON, 0, 64, 100), 0));
//    t1.add(new MidiEvent(new ShortMessage(ShortMessage.NOTE_OFF, 0, 64, 100), 200));
//    t1.add(new MidiEvent(new ShortMessage(ShortMessage.NOTE_ON, 0, 64, 100), 2000));
//    sequencer.open();
//    sequencer.setSequence(s);
//    sequencer.start();
  }
}
