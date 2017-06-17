package cs3500.music.view;

import java.awt.event.ActionListener;
import java.awt.event.KeyListener;
import java.util.List;
import java.util.Map;

/**
 * Created by Hoyin on 6/17/2017.
 */

/**
 * An interface that defines the functionality of what a gui (visual) view should have_
 */
public interface IGuiView extends IMusicEditorView{

  /**
   * Sets the duration of the music editor.
   * @param duration the total duration of the music editor
   */
  void setDuration(int duration);

  /**
   * Sets the combined note map for the view.
   * A combine map maps the full ten octaves, regardless of instrument or volume to a list of strings representing
   * the state at a particular beat.
   * The keys of the map are the integer representation of a pitch-octave notation (ex. C4 = 60)
   * The values are a list of strings that are either "start", "continue", or "rest" that show
   * the state of that pitch at a particular beat.
   * @param notes the combined note map that the view is going to use
   */
  void setCombineNoteMap(Map<Integer, List<String>> notes);

  /**
   * Sets the listener of the guiview
   * @param action the action listener of the guiview
   * @param key the key listener of the guiview
   */
  void setListener(ActionListener action, KeyListener key);

  /**
   * Updates the beat the music model is current on by the given parameter, positive
   * number would advance the beat and negative number would go back beats.
   * @param beat the amount of beat that the user wishes to go back / advance by.
   */
  void updateCurrentBeat(int beat);

}
