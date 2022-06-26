package cn.gaein.java.video.tool.controllers;

import cn.gaein.java.video.tool.compontents.VFXPositionBar;
import cn.gaein.java.video.tool.models.InputVideo;
import cn.gaein.java.video.tool.models.InputVideoCell;
import cn.gaein.java.video.tool.utils.FileExtensions;
import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXListView;
import io.github.palexdev.materialfx.effects.DepthLevel;
import io.github.palexdev.materialfx.effects.MFXDepthManager;
import io.github.palexdev.materialfx.utils.others.FunctionalStringConverter;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.kordamp.ikonli.javafx.FontIcon;
import uk.co.caprica.vlcj.factory.MediaPlayerFactory;
import uk.co.caprica.vlcj.player.base.MediaPlayer;
import uk.co.caprica.vlcj.player.base.MediaPlayerEventAdapter;
import uk.co.caprica.vlcj.player.embedded.EmbeddedMediaPlayer;

import java.util.Objects;

import static uk.co.caprica.vlcj.javafx.videosurface.ImageViewVideoSurfaceFactory.videoSurfaceForImageView;

public class MainController {
    private final MediaPlayerFactory mediaPlayerFactory = new MediaPlayerFactory();

    private final EmbeddedMediaPlayer mediaPlayer;

    @FXML
    public MFXListView<InputVideo> inputFileList;
    @FXML
    public BorderPane displayViewPane;
    @FXML
    public ImageView displayView = new ImageView();
    @FXML
    public MFXButton flagStartBtn;
    @FXML
    public MFXButton flagEndBtn;
    @FXML
    public MFXButton flagEditBtn;
    @FXML
    public MFXButton flagCancelBtn;
    @FXML
    public MFXListView outputFileList;
    @FXML
    public MFXButton exportOutputBtn;
    @FXML
    public MFXButton inputAddBtn;
    @FXML
    public MFXButton inputRemoveBtn;
    @FXML
    public FontIcon displayCtrlIcon;
    @FXML
    public MFXButton displayCtrlBtn;
    @FXML
    public MFXButton displayStopBtn;
    @FXML
    public StackPane displayCtrlPane;
    public VFXPositionBar displayPositionBar;

    public MainController() {
        mediaPlayer = mediaPlayerFactory.mediaPlayers().newEmbeddedMediaPlayer();
        mediaPlayer.events().addMediaPlayerEventListener(
                new MediaPlayerEventAdapter() {
                    @Override
                    public void playing(MediaPlayer mediaPlayer) {
                        // set icon to pause
                        displayCtrlIcon.setIconLiteral("mdi2p-pause");
                    }

                    @Override
                    public void paused(MediaPlayer mediaPlayer) {
                        // set icon to play
                        displayCtrlIcon.setIconLiteral("mdi2p-play");
                    }

                    @Override
                    public void stopped(MediaPlayer mediaPlayer) {
                        // set icon to play
                        displayCtrlIcon.setIconLiteral("mdi2p-play");
                        resetDisplayView();
                    }

                    @Override
                    public void timeChanged(MediaPlayer mediaPlayer, long newTime) {
                        Platform.runLater(() -> displayPositionBar.update(newTime));
                    }
                });
    }

    @FXML
    private void initialize() {
        // center player
        displayView.fitWidthProperty().bind(displayViewPane.widthProperty());
        displayView.fitHeightProperty().bind(displayViewPane.heightProperty());
        displayView.setPreserveRatio(true);
        displayViewPane.setCenter(displayView);
        displayViewPane.setEffect(MFXDepthManager.shadowOf(DepthLevel.LEVEL2));
        mediaPlayer.videoSurface().set(videoSurfaceForImageView(displayView));
        displayPositionBar = new VFXPositionBar(mediaPlayer);
        displayCtrlPane.getChildren().add(displayPositionBar);
        resetDisplayView();

        // input file list
        var converter = FunctionalStringConverter.to(InputVideo::getDisplayName);
        inputFileList.setConverter(converter);
        inputFileList.setCellFactory(v -> new InputVideoCell(inputFileList, v));
    }

    @FXML
    protected void onAddInputClick() {
        var inputFileListArr = inputFileList.getItems();

        var chooser = new FileChooser();

        chooser.setTitle("导入视频文件");
        chooser.getExtensionFilters().addAll(FileExtensions.getVideoExtensions());
        var fileList = chooser.showOpenMultipleDialog(new Stage());

        if (fileList == null)
            return;

        fileList.forEach(f -> {
            var video = new InputVideo(f);
            var index = inputFileListArr.size();

            inputFileListArr.add(index, video);

            var item = inputFileList.getCell(index);
            item.setOnMouseClicked(e -> {
                // video item clicked
                mediaPlayer.media().play(video.getFile().getPath());
                displayCtrlIcon.setIconLiteral("mdi2p-pause");
            });
        });
    }

    @FXML
    protected void onRemoveInputClick() {
        var inputFileListArr = inputFileList.getItems();
        var selectFileListArr = inputFileList.getSelectionModel().getSelectedValues();

        inputFileListArr.removeAll(selectFileListArr);

        // Only select one, must be the video witch are playing
        onStopDisplayClick();
    }

    @FXML
    protected void onCtrlDisplayClick() {
        if (mediaPlayer.status().isPlaying()) {
            if (mediaPlayer.status().canPause()) {
                // pause
                mediaPlayer.controls().pause();
            }
        } else {
            // play
            mediaPlayer.controls().play();
        }
    }

    @FXML
    protected void onStopDisplayClick() {
        mediaPlayer.controls().stop();
    }

    private void resetDisplayView() {
        displayView.setImage(new Image(Objects.requireNonNull(
                getClass().getResource("images/player_background.png")).toExternalForm()));
        displayPositionBar.reset();
    }
}