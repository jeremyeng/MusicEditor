package cs3500.music;

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
    view.initialize();
    // You probably need to connect these views to your model, too...
  }
}
