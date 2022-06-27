package cn.gaein.java.video.tool.compontents;

import cn.gaein.java.video.tool.compontents.beans.PositionProperty;
import io.github.palexdev.materialfx.controls.MFXSlider;
import io.github.palexdev.materialfx.effects.DepthLevel;
import io.github.palexdev.materialfx.effects.MFXDepthManager;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import uk.co.caprica.vlcj.player.base.MediaPlayer;
import uk.co.caprica.vlcj.player.base.MediaPlayerEventAdapter;
import uk.co.caprica.vlcj.player.embedded.EmbeddedMediaPlayer;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

public class VFXPositionBar extends HBox {
    private final PositionProperty positionProperty;
    private final Label timeLabel = new Label();
    private final MFXSlider timeBar = new MFXSlider(0, 1000, 0);

    private final Date timeInDate = new Date();
    private final SimpleDateFormat formatter
            = new SimpleDateFormat("HH:mm:ss:SSS", Locale.UK);

    public VFXPositionBar(EmbeddedMediaPlayer player) {
        // self style
        setPadding(new Insets(2, 8, 2, 16));
        setSpacing(8);
        setStyle("-fx-background-color: white;");
        setEffect(MFXDepthManager.shadowOf(DepthLevel.LEVEL2));

        // timeBar style
        timeBar.setPopupSupplier(Region::new);
        timeBar.setPrefWidth(584);
        // bind value to position
        positionProperty = new PositionProperty(player);
        timeBar.valueProperty().bindBidirectional(positionProperty);

        // add player event listener
        player.events().addMediaPlayerEventListener(
                new MediaPlayerEventAdapter() {
                    @Override
                    public void opening(MediaPlayer mediaPlayer) {
                        Platform.runLater(() -> start());
                    }

                    @Override
                    public void stopped(MediaPlayer mediaPlayer) {
                        Platform.runLater(() -> initSet());
                    }

                    @Override
                    public void timeChanged(MediaPlayer mediaPlayer, long newTime) {
                        Platform.runLater(() -> update(newTime));
                    }
                });

        // init formatter
        formatter.setTimeZone(TimeZone.getTimeZone("UTC"));

        // timeLabel style
        timeLabel.getStyleClass().add("code-font");

        initSet();

        var barPane = new BorderPane();
        barPane.setCenter(timeBar);
        var labelPane = new BorderPane();
        labelPane.setCenter(timeLabel);
        getChildren().addAll(barPane, labelPane);
    }

    private void start() {
        timeBar.setDisable(false);
    }

    private void initSet() {
        positionProperty.set(0);
        positionProperty.update();
        timeLabel.setText("00:00:00:000");
        timeBar.setDisable(true);
    }

    public long getTime() {
        return timeInDate.getTime();
    }

    public void update(long timeInMillis) {
        timeInDate.setTime(timeInMillis);

        var text = formatter.format(timeInDate);
        timeLabel.setText(text);

        positionProperty.update();
    }
}
