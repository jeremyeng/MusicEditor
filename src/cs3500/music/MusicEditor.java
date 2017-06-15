package cs3500.music;

import cs3500.music.controller.IMusicEditorController;
import cs3500.music.controller.MusicEditorController;
import cs3500.music.model.IMusicEditor;
import cs3500.music.model.MusicEditorModel;
import cs3500.music.model.Note;
import cs3500.music.model.Pitch;
import cs3500.music.view.GuiViewFrame;
import cs3500.music.view.IMusicEditorView;
import cs3500.music.view.MidiViewImpl;

import java.io.IOException;

import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MidiUnavailableException;
import javax.swing.*;


public class MusicEditor {
  public static void main(String[] args) throws IOException, InvalidMidiDataException, MidiUnavailableException {
    IMusicEditorView view = new GuiViewFrame();
    MidiViewImpl midiView = new MidiViewImpl();
    IMusicEditor<Note> model = new MusicEditorModel(50);
    model.addNote(new Note(Pitch.C, 3, 0), 0, 10, 10);
    model.addNote(new Note(Pitch.C,3, 0),0,10, 10);
    model.addNote(new Note(Pitch.F, 3, 0), 10, 20, 10);
    model.addNote(new Note(Pitch.C,3, 0),8,22, 10);
    model.addNote(new Note(Pitch.CSharp, 3, 1),10,15, 10);
    IMusicEditorController controller = new MusicEditorController(model, view);
    controller.go();
  }
}
