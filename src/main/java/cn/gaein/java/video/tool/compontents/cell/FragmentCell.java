package cn.gaein.java.video.tool.compontents.cell;

import cn.gaein.java.video.tool.videos.VideoFragment;
import io.github.palexdev.materialfx.controls.MFXListView;
import io.github.palexdev.materialfx.controls.MFXTooltip;
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

        MFXTooltip.of(this,
                fragment.getFullDisplayName()).install();
        render(fragment);
    }

    @Override
    protected void render(VideoFragment fragment) {
        super.render(fragment);

        // first render is null
        if (fragmentIcon != null) {
            getChildren().add(0, fragmentIcon);
        }
    }
}
