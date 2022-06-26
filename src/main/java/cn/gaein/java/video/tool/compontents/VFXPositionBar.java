package cn.gaein.java.video.tool.compontents;

import cn.gaein.java.video.tool.compontents.beans.PositionProperty;
import io.github.palexdev.materialfx.controls.MFXSlider;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import uk.co.caprica.vlcj.player.embedded.EmbeddedMediaPlayer;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class VFXPositionBar extends HBox {
    private final PositionProperty positionProperty;
    private final Label timeLabel = new Label("00:00:00:000");
    private final MFXSlider timeBar = new MFXSlider(0, 1000, 0);

    private final Date timeInDate = new Date(0);
    private final SimpleDateFormat formatter
            = new SimpleDateFormat("HH:mm:ss:SSS", Locale.UK);

    public VFXPositionBar(EmbeddedMediaPlayer player) {
        setPadding(new Insets(8, 2, 8, 4));
        setSpacing(4);

        timeBar.setDisable(true);
        timeBar.setPopupSupplier(Region::new);
        timeBar.setPrefWidth(616);

        positionProperty = new PositionProperty(player);

        timeBar.valueProperty().bindBidirectional(positionProperty);

        getChildren().addAll(timeBar, timeLabel);
    }

    public long getTime() {
        return timeInDate.getTime();
    }

    public void update(long timeInMillis) {
        timeInDate.setTime(timeInMillis);

        if (timeBar.disabledProperty().get()) {
            timeBar.setDisable(false);
        }

        var text = formatter.format(timeInDate);

        timeLabel.setText(text);
        positionProperty.update();
    }

    public void reset() {
        timeInDate.setTime(0);
        timeBar.setDisable(true);
    }
}
