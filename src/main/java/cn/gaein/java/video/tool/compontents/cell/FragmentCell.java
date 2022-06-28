package cn.gaein.java.video.tool.compontents.cell;

import cn.gaein.java.video.tool.models.Video;
import io.github.palexdev.materialfx.controls.MFXListView;
import io.github.palexdev.materialfx.controls.cell.MFXListCell;
import org.kordamp.ikonli.javafx.FontIcon;

/**
 * @author Gaein
 */
public class FragmentCell extends MFXListCell<Video> {
    private final FontIcon fragmentIcon
            = new FontIcon("mdi2m-movie-edit:18");

    public FragmentCell(MFXListView<Video> listView, Video video) {
        super(listView, video);

        render(video);
    }

    @Override
    protected void render(Video data) {
        super.render(data);

        // first render is null
        if (fragmentIcon != null) {
            getChildren().add(fragmentIcon);
        }
    }
}
