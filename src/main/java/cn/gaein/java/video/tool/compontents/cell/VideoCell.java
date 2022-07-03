package cn.gaein.java.video.tool.compontents.cell;

import cn.gaein.java.video.tool.videos.Video;
import io.github.palexdev.materialfx.controls.MFXListView;
import io.github.palexdev.materialfx.controls.cell.MFXListCell;
import io.github.palexdev.materialfx.font.MFXFontIcon;

/**
 * @author Gaein
 */
public class VideoCell extends MFXListCell<Video> {
    private final MFXFontIcon videoIcon
            = new MFXFontIcon("mfx-video", 18);

    public VideoCell(MFXListView<Video> listView, Video video) {
        super(listView, video);

        render(video);
    }

    @Override
    protected void render(Video data) {
        super.render(data);

        // first render is null
        if (videoIcon != null) {
            getChildren().add(0, videoIcon);
        }
    }
}
