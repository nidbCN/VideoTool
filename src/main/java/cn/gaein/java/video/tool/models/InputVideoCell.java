package cn.gaein.java.video.tool.models;

import io.github.palexdev.materialfx.controls.MFXListView;
import io.github.palexdev.materialfx.controls.cell.MFXListCell;
import io.github.palexdev.materialfx.font.MFXFontIcon;

public class InputVideoCell extends MFXListCell<InputVideo> {
    private final MFXFontIcon videoIcon;

    public InputVideoCell(MFXListView<InputVideo> listView, InputVideo video) {
        super(listView, video);

        videoIcon = new MFXFontIcon("mfx-video", 18);

        render(video);
    }

    @Override
    protected void render(InputVideo data) {
        super.render(data);

        if (videoIcon != null) {
            getChildren().add(0, videoIcon);
        }
    }
}
