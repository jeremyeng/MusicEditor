package cs3500.music.tests;

import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

import javax.sound.midi.MidiUnavailableException;
import javax.sound.midi.Synthesizer;

import cs3500.music.controller.IMusicEditorController;
import cs3500.music.controller.MusicEditorController;
import cs3500.music.model.IMusicEditor;
import cs3500.music.model.MusicEditorModel;
import cs3500.music.model.Note;
import cs3500.music.util.CompositionBuilder;
import cs3500.music.util.MusicReader;
import cs3500.music.view.MidiViewImpl;

import static org.junit.Assert.*;

/**
 * Tests the midi view implementation by using Mock Synthesizers and Receivers to log the
 * sent MidiMessages.
 */
public class MidiViewImplTest {
  private Synthesizer synth;
  private MidiViewImpl midiView;
  private CompositionBuilder<IMusicEditor<Note>> builder;
  private StringBuilder log;

  @Before
  public void initialize() throws FileNotFoundException, MidiUnavailableException {
    this.log = new StringBuilder();

    this.builder = new MusicEditorModel.Builder();
  }

  @Test
  public void testMidi() throws IOException, MidiUnavailableException {
    FileReader file = new FileReader("mary-little-lamb.txt");
    String content = new Scanner(new File("mary-little-lamb.txt")).useDelimiter("\\Z").next();
    IMusicEditor<Note> model = MusicReader.parseFile(file, builder);
    this.synth = new MockSynth(log, model.getTempo());
    this.midiView = new MidiViewImpl(synth);
    IMusicEditorController controller = new MusicEditorController(model, midiView);
    controller.go();
    assertEquals(content, log.toString());
  }

}