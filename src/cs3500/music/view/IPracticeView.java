package cs3500.music.view;

import java.util.List;

/**
 * Created by Hoyin on 6/25/2017.
 */
public interface IPracticeView extends IMusicEditorView, IGuiView {

  List<Integer> getNotesToClick(int beat);

}
