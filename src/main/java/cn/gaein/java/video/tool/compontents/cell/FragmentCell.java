package cn.gaein.java.video.tool.compontents.cell;

import cn.gaein.java.video.tool.models.Video;
import cn.gaein.java.video.tool.models.VideoFragment;
import io.github.palexdev.materialfx.controls.MFXListView;
import io.github.palexdev.materialfx.controls.cell.MFXListCell;
import org.kordamp.ikonli.javafx.FontIcon;

/**
 * @author Gaein
 */
public class FragmentCell extends MFXListCell<VideoFragment> {
    private final FontIcon fragmentIcon
            = new FontIcon("mdi2m-movie-edit:18");

    public FragmentCell(MFXListView<VideoFragment> listView, VideoFragment fragment) {
        super(listView, fragment);

        render(fragment);
    }

    @Override
    protected void render(VideoFragment fragment) {
        super.render(fragment);

        // first render is null
        if (fragmentIcon != null) {
            getChildren().add(fragmentIcon);
        }
    }
}
