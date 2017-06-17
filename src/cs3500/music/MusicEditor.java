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
import javax.sound.midi.MidiUnavailableException;

/**
 * Initialize the music editor. The type of view can be selected by changing the argument of
 * getView, and the file can be selected by changing the argument of FileReader.
 */
public class MusicEditor {
  public static void main(String[] args) throws IOException, InvalidMidiDataException, MidiUnavailableException {
    IMusicEditorView view = ViewFactory.getView(
            args[1]
    );
    CompositionBuilder<IMusicEditor<Note>> builder = new MusicEditorModel.Builder();
    FileReader file = new FileReader(args[0]);
    IMusicEditor<Note> model = MusicReader.parseFile(file, builder);
    IMusicEditorController controller = new MusicEditorController(model, view);
    controller.go();
  }
}
