package cs3500.music.view;

/**
 * The interface for a view that combines both the GUI and the MIDI views.
 */
public interface ICombinedView<K> extends IMusicEditorView<K> {

  /**
   * Gets the GUI view that comprises the combined view.
   *
   * @return an instance of the GUI View implementation.
   */
  IGuiView getGuiView();

  /**
   * Gets the MIDI view that comprisese the combined view.
   *
   * @return an instance of the MIDI View implementation.
   */
  IMidiView getMidiView();
}
