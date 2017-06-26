package cs3500.music.view;

import javax.sound.midi.MidiUnavailableException;

/**
 * A factory of views, with a single method that takes in a String name for a view
 * (e.g. “console” or “visual” or “midi”), and constructs an instance of the
 * appropriate concrete view.
 */
public class ViewFactory {
  /**
   * Generates an implementation of the IMusicEditorView interface from the given String.
   * @param viewType a string representing the type of view to be created.
   * @return the corresponding IMusiEditorView
   * @throws MidiUnavailableException if midi data is unavailable
   * @throws IllegalArgumentException if given an invalid view type.
   */
  public static IMusicEditorView getView(String viewType) throws MidiUnavailableException {
    switch (viewType.toLowerCase()) {
      case "console":
        return new ConsoleView(System.out);
      case "visual":
        return new GuiViewFrame();
      case "midi":
        return new MidiViewImpl();
      case "combined":
        return new CombinedView(new GuiViewFrame(), new MidiViewImpl());
      case "practice":
        return new PracticeView(new GuiViewFrame());
      default:
        throw new IllegalArgumentException("Invalid view type!");
    }
  }
}
