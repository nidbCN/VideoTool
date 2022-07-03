package cn.gaein.java.video.tool.compontents;

import cn.gaein.java.video.tool.compontents.beans.PositionProperty;
import cn.gaein.java.video.tool.videos.Video;
import cn.gaein.java.video.tool.videos.VideoTime;
import cn.gaein.java.video.tool.videosurface.PixelBufferVideoSurface;
import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXSlider;
import io.github.palexdev.materialfx.effects.DepthLevel;
import io.github.palexdev.materialfx.effects.MFXDepthManager;
import io.github.palexdev.materialfx.enums.ButtonType;
import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import org.kordamp.ikonli.javafx.FontIcon;
import uk.co.caprica.vlcj.factory.MediaPlayerFactory;
import uk.co.caprica.vlcj.player.base.MediaPlayer;
import uk.co.caprica.vlcj.player.base.MediaPlayerEventAdapter;
import uk.co.caprica.vlcj.player.embedded.EmbeddedMediaPlayer;

import java.util.Objects;

/**
 * @author Gaein
 */
public class PlayerView extends VBox {
    private MediaPlayerFactory playerFactory;
    private EmbeddedMediaPlayer player;
    private Video video;
    private final VideoTime time = new VideoTime();
    private final StringProperty timeString
            = new SimpleStringProperty("00:00:00.000");

    public StringProperty timeStringProperty() {
        return timeString;
    }

    private final PositionProperty position;
    private final ImageView displayView = new ImageView();
    private final BorderPane displayPane
            = new BorderPane(displayView);
    private final FontIcon displayCtrlIcon
            = new FontIcon("mdi2p-play:24:#7a0ed9");
    private final MFXButton displayCtrlBtn
            = new MFXButton("", displayCtrlIcon);
    private final FontIcon displayStopIcon
            = new FontIcon("mdi2s-stop:24:#7a0ed9");
    private final MFXButton displayStopBtn
            = new MFXButton("", displayStopIcon);
    private final MFXSlider timeBar = new MFXSlider(0, 10000, 0);
    private final Label timeLabel = new Label("00:00:00.000");

    public PlayerView() {
        this(new MediaPlayerFactory());
    }

    public PlayerView(MediaPlayerFactory factory) {
        playerFactory = factory;
        setSpacing(4);

        initPlayer();


        position = new PositionProperty(player);

        // init view
        var positionAndTimeBar = initPositionAndTimeBar();
        var controlBar = new HBox(4);
        controlBar.getChildren().addAll(
                displayCtrlBtn, displayStopBtn, positionAndTimeBar
        );
        initButtons();

        // add node to view
        getChildren().addAll(displayPane, controlBar);
    }

    private void initPlayer() {
        // player setting
        displayPane.setPrefWidth(800);
        displayPane.setPrefHeight(450);
        displayPane.setBackground(new Background(
                new BackgroundFill(Color.BLACK, null, null)
        ));
        displayPane.setEffect(MFXDepthManager.shadowOf(DepthLevel.LEVEL2));

        displayView.fitWidthProperty().bind(displayPane.widthProperty());
        displayView.fitHeightProperty().bind(displayPane.heightProperty());
        displayView.setPreserveRatio(true);
        player = playerFactory.mediaPlayers().newEmbeddedMediaPlayer();
        player.events().addMediaPlayerEventListener(
                new MediaPlayerEventAdapter() {
                    @Override
                    public void opening(MediaPlayer mediaPlayer) {
                        Platform.runLater(() -> {
                            timeBar.setDisable(false);
                            displayStopBtn.setDisable(false);
                        });
                    }

                    @Override
                    public void playing(MediaPlayer mediaPlayer) {
                        // set icon to pause
                        Platform.runLater(() ->
                                displayCtrlIcon.setIconLiteral("mdi2p-pause"));
                    }

                    @Override
                    public void paused(MediaPlayer mediaPlayer) {
                        // set icon to play
                        Platform.runLater(() ->
                                displayCtrlIcon.setIconLiteral("mdi2p-play"));
                    }

                    @Override
                    public void stopped(MediaPlayer mediaPlayer) {
                        Platform.runLater(() -> {
                            displayCtrlIcon.setIconLiteral("mdi2p-play");
                            resetDisplayView();
                            resetPositionAndTimeBar();
                        });
                    }

                    @Override
                    public void timeChanged(MediaPlayer mediaPlayer, long newTime) {
                        Platform.runLater(() -> {
                            time.setTime(newTime);
                            timeString.set(time.toLongString());

                            position.update();
                        });

                    }
                });
        player.videoSurface().set(
                PixelBufferVideoSurface.pixelBufferVideoSurfaceForImageView(displayView)
        );

        resetDisplayView();
    }

    private void initButtons() {
        displayCtrlBtn.setButtonType(ButtonType.RAISED);
        displayStopBtn.setButtonType(ButtonType.RAISED);

        displayCtrlBtn.setOnMouseClicked(e -> {
            if (player.status().isPlaying()) {
                pause();
            } else {
                play();
            }
        });

        displayStopBtn.setOnMouseClicked(e -> stop());
    }

    private HBox initPositionAndTimeBar() {
        // ensure position is set

        var positionBar = new HBox(8);
        positionBar.setPadding(new Insets(2, 8, 2, 16));
        positionBar.setBackground(new Background(
                new BackgroundFill(Color.WHITE, null, null)
        ));
        positionBar.setEffect(MFXDepthManager.shadowOf(DepthLevel.LEVEL2));

        timeString.set(time.toLongString());

        timeBar.setPopupSupplier(Region::new);
        timeBar.setPrefWidth(584);
        timeBar.valueProperty().bindBidirectional(position);
        timeBar.valueProperty().addListener(((observable, oldValue, newValue) -> {
            if (!player.status().isPlaying()) {
                player.controls().nextFrame();
                time.setTime(player.status().time());
                timeString.set(time.toLongString());
            }
        }));

        timeLabel.getStyleClass().add("code-font");
        timeLabel.textProperty().bind(timeString);

        positionBar.getChildren().addAll(
                new BorderPane(timeBar),
                new BorderPane(timeLabel)
        );

        return positionBar;
    }

    private void resetDisplayView() {
        displayView.setImage(new Image(Objects.requireNonNull(
                getClass().getResource("images/player_background.png")).toExternalForm()));
        displayStopBtn.setDisable(true);
    }

    private void resetPositionAndTimeBar() {
        position.set(0);
        position.update();

        time.setTime(0);
        timeString.set(time.toLongString());
        timeBar.setDisable(true);
    }

    private PositionProperty positionProperty() {
        return position;
    }

    public void setPlayerFactory(MediaPlayerFactory playerFactory) {
        this.playerFactory = playerFactory;
    }

    public String getMediaInfo() {
        var info = player.media().info();

        return info == null
                ? "未打开媒体，无法查看媒体信息"
                : "文件:\n\t" + info.mrl() +
                "\n" +
                "时长:\n\t" + new VideoTime(info.duration()).toLongString() +
                "\n" +
                "视频轨道:\n\t" + String.join("\n\t", info.videoTracks().stream().map(t ->
                "[" + t.id() + "]" + t.description() + "-" + t.language() + ":" + t.width() + "x" + t.height()
                        + " in " + t.codecName()
        ).toList()) +
                "\n" +
                "音频轨道:\n\t" + String.join("\n\t", info.audioTracks().stream().map(t ->
                "[" + t.id() + "]" + t.description() + "-" + t.language() + ": in " + t.codecName() + " at " + t.rate()
        ).toList()) +
                "\n" +
                "字幕轨道:\n\t" + String.join("\n\t", info.textTracks().stream().map(t ->
                "[" + t.id() + "]" + t.description() + "-" + t.language() + ":" + " in " + t.codecName()
        ).toList());
    }

    public Video getVideo() {
        return video;
    }

    public void setVideo(Video video) {
        this.video = video;
    }

    public VideoTime getTime() {
        return time.getVideoTime();
    }

    public void open() {
        player.media().play(
                video.getFile().getAbsolutePath(), "play-and-pause"
        );
    }

    public void open(Video newVideo) {
        player.media().play(
                newVideo.getFile().getAbsolutePath(), "play-and-pause"
        );

        this.video = newVideo;
    }

    public void pause() {
        if (player.status().canPause()) {
            if (player.status().isPlaying()) {
                player.controls().pause();
            }
        }
    }

    public void play() {
        if (player.status().isPlayable()) {
            player.controls().play();
        }
    }

    public void stop() {
        player.controls().stop();
    }

    public void stopAndRelease() {
        stop();
        player.release();
    }

    public void dispose() {
        stopAndRelease();
        playerFactory.release();
    }
}
