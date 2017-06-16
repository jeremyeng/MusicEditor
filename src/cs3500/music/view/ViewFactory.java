package cs3500.music.view;

import javax.sound.midi.MidiUnavailableException;

/**
 * A factory of views, with a single method that takes in a String name for a view
 * (e.g. “console” or “visual” or “midi”), and constructs an instance of the
 * appropriate concrete view.
 */
public class ViewFactory {
  public static IMusicEditorView getView(String viewType) throws MidiUnavailableException {
    switch (viewType.toLowerCase()) {
      case "console" :
        return new ConsoleView();
      case "visual":
        return new GuiViewFrame();
      case "midi":
        return new MidiViewImpl();
      default:
        throw new IllegalArgumentException("");
    }
  }
}
