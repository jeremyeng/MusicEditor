package cs3500.music.model;

import java.util.List;
import java.util.Map;

/**
 * Represents the notes contained in a piece of music.
 * Cannot be changed.
 */
public class ReadOnlyMusicEditorModel implements IReadOnlyMusicEditor<Note> {
  private IMusicEditor<Note> model;

  /**
   * Creates a read-only version of the given model.
   * @param model the model to make read-only.
   */
  public ReadOnlyMusicEditorModel(IMusicEditor<Note> model) {
    this.model = model;
  }

  @Override
  public int getDuration() {
    return model.getDuration();
  }

  @Override
  public long getTempo() {
    return model.getTempo();
  }

  @Override
  public List<Note> getNoteRange() {
    return model.getNoteRange();
  }

  @Override
  public String getNoteState(Note note, int beatNumber) throws IllegalArgumentException {
    return model.getNoteState(note, beatNumber);
  }

  @Override
  public List<List<List<Integer>>> getMidiInfo() {
    return model.getMidiInfo();
  }

  @Override
  public Map<Integer, List<String>> getCombinedNoteMap() {
    return model.getCombinedNoteMap();
  }
}
