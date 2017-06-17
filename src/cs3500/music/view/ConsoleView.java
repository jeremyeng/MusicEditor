package cs3500.music.view;

import java.io.IOException;
import cs3500.music.model.IMusicEditor;


/**
 * Represents the music editor as text printed to the console.
 */
public class ConsoleView implements IMusicEditorView {

  private IMusicEditor model;
  private Appendable text;

  /**
   * Construct a console view with the given appendable as its text.
   * @param app the appendable to be set as the console view's text.
   */
  public ConsoleView(Appendable app) {
    this.text = app;
  }

  @Override
  public void makeVisible() throws IOException {
    text.append(this.model.getState());
  }

  @Override
  public void update(IMusicEditor model) {
    this.model = model;
  }

}
