package cs3500.music;

import cs3500.music.controller.IMusicEditorController;
import cs3500.music.controller.MusicEditorController;
import cs3500.music.model.IMusicEditor;
import cs3500.music.model.MusicEditorModel;
import cs3500.music.model.Note;
import cs3500.music.model.Pitch;
import cs3500.music.util.CompositionBuilder;
import cs3500.music.util.MusicReader;
import cs3500.music.view.GuiViewFrame;
import cs3500.music.view.IMusicEditorView;
import cs3500.music.view.MidiViewImpl;

import java.io.FileReader;
import java.io.IOException;
import java.io.StringReader;

import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MidiUnavailableException;
import javax.swing.*;



public class MusicEditor {
  public static void main(String[] args) throws IOException, InvalidMidiDataException, MidiUnavailableException {
    IMusicEditorView view = new GuiViewFrame();
    MidiViewImpl midiView = new MidiViewImpl();
    IMusicEditor<Note> model = new MusicEditorModel(50);

    CompositionBuilder<IMusicEditor<Note>> builder = new MusicEditorModel.Builder();
//    builder = builder.addNote(0, 2, 60, 60, 100);
//    builder = builder.addNote(1, 3, 60, 60, 100);
//    builder = builder.addNote(2, 4, 60, 60, 100);
//    model = builder.build();

    FileReader littleLamb = new FileReader("mystery-2.txt");
    model = MusicReader.parseFile(littleLamb, builder);

//    model.addNote(new Note(Pitch.C, 3, 0), 0, 10, 10);
//    model.addNote(new Note(Pitch.E, 3, 0), 1, 10, 10);
//    model.addNote(new Note(Pitch.G, 3, 0), 2, 10, 10);
//    model.addNote(new Note(Pitch.C,3, 0),0,10, 10);
//    model.addNote(new Note(Pitch.F, 3, 0), 10, 20, 10);
//    model.addNote(new Note(Pitch.C,3, 0),8,22, 10);
//    model.addNote(new Note(Pitch.CSharp, 3, 1),10,15, 10);
//    IMusicEditorController controller = new MusicEditorController(model, view);
    System.out.println(model.getMidiInfo());
//    controller.go();
    midiView.playNote(model.getMidiInfo(), model.getTempo());
  }
}
