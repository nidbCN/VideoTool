package cn.gaein.java.video.tool.compontents;

import cn.gaein.java.video.tool.compontents.beans.PositionProperty;
import io.github.palexdev.materialfx.controls.MFXSlider;
import io.github.palexdev.materialfx.effects.DepthLevel;
import io.github.palexdev.materialfx.effects.MFXDepthManager;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import uk.co.caprica.vlcj.player.embedded.EmbeddedMediaPlayer;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

public class VFXPositionBar extends HBox {
    private final PositionProperty positionProperty;
    private final Label timeLabel = new Label("00:00:00:000");
    private final MFXSlider timeBar = new MFXSlider(0, 1000, 0);

    private final Date timeInDate = new Date(0);
    private final SimpleDateFormat formatter
            = new SimpleDateFormat("HH:mm:ss:SSS", Locale.UK);

    public VFXPositionBar(EmbeddedMediaPlayer player) {
        // self style
        setPadding(new Insets(8, 8, 0, 16));
        setSpacing(8);
        setStyle("-fx-background-color: white;");
        setEffect(MFXDepthManager.shadowOf(DepthLevel.LEVEL2));

        // timeBar style
        timeBar.setDisable(true);
        timeBar.setPopupSupplier(Region::new);
        timeBar.setPrefWidth(584);
        // bind value to position
        positionProperty = new PositionProperty(player);
        timeBar.valueProperty().bindBidirectional(positionProperty);

        // init formatter
        formatter.setTimeZone(TimeZone.getTimeZone("UTC"));

        // timeLabel style
        timeLabel.getStyleClass().add("code-font");

        getChildren().addAll(timeBar, timeLabel);
    }

    public long getTime() {
        return timeInDate.getTime();
    }

    public void start() {
        timeBar.setDisable(false);
    }

    public void update(long timeInMillis) {
        timeInDate.setTime(timeInMillis);

        var text = formatter.format(timeInDate);
        timeLabel.setText(text);

        positionProperty.update();
    }

    public void reset() {
        positionProperty.set(0);
        positionProperty.update();
        timeLabel.setText("00:00:00:000");
        timeBar.setDisable(true);
    }
}
