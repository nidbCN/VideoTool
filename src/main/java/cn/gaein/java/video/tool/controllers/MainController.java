package cn.gaein.java.video.tool.controllers;

import cn.gaein.java.video.tool.models.InputVideo;
import cn.gaein.java.video.tool.models.InputVideoCell;
import cn.gaein.java.video.tool.utils.FileExtensions;
import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXListView;
import io.github.palexdev.materialfx.controls.cell.MFXListCell;
import io.github.palexdev.materialfx.utils.others.FunctionalStringConverter;
import javafx.fxml.FXML;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.StringConverter;
import org.kordamp.ikonli.javafx.FontIcon;
import uk.co.caprica.vlcj.factory.MediaPlayerFactory;
import uk.co.caprica.vlcj.player.base.MediaPlayer;
import uk.co.caprica.vlcj.player.base.MediaPlayerEventAdapter;
import uk.co.caprica.vlcj.player.embedded.EmbeddedMediaPlayer;

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

    public MainController() {
        mediaPlayer = mediaPlayerFactory.mediaPlayers().newEmbeddedMediaPlayer();
        mediaPlayer.events().addMediaPlayerEventListener(
                new MediaPlayerEventAdapter() {
                    @Override
                    public void playing(MediaPlayer mediaPlayer) {
                    }

                    @Override
                    public void paused(MediaPlayer mediaPlayer) {
                    }

                    @Override
                    public void stopped(MediaPlayer mediaPlayer) {
                    }

                    @Override
                    public void timeChanged(MediaPlayer mediaPlayer, long newTime) {
                    }
                });
    }

    @FXML
    private void initialize() {
        displayView.fitWidthProperty().bind(displayViewPane.widthProperty());
        displayView.fitHeightProperty().bind(displayViewPane.heightProperty());
        displayView.setPreserveRatio(true);
        displayViewPane.setCenter(displayView);

        mediaPlayer.videoSurface().set(videoSurfaceForImageView(displayView));

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

                displayCtrlIcon.setIconLiteral("mdi2p-play");
            }
        } else {
            // play
            mediaPlayer.controls().play();

            displayCtrlIcon.setIconLiteral("mdi2p-pause");
        }
    }

    @FXML
    protected void onStopDisplayClick() {
        mediaPlayer.controls().stop();
        displayCtrlIcon.setIconLiteral("mdi2p-play");
        displayView.setImage(null);
    }
}