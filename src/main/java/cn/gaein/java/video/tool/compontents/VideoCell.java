package cn.gaein.java.video.tool.compontents;

import cn.gaein.java.video.tool.models.Video;
import io.github.palexdev.materialfx.controls.MFXListView;
import io.github.palexdev.materialfx.controls.cell.MFXListCell;
import io.github.palexdev.materialfx.font.MFXFontIcon;

/**
 * @author Gaein
 */
public class VideoCell extends MFXListCell<Video> {
    private final MFXFontIcon videoIcon;

    public VideoCell(MFXListView<Video> listView, Video video) {
        super(listView, video);

        videoIcon = new MFXFontIcon("mfx-video", 18);

        render(video);
    }

    @Override
    protected void render(Video data) {
        super.render(data);

        if (videoIcon != null) {
            getChildren().add(0, videoIcon);
        }
    }
}
