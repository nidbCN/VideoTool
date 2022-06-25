package cn.gaein.java.video.tool.controllers;

import cn.gaein.java.video.tool.models.InputVideo;
import cn.gaein.java.video.tool.models.InputVideoCell;
import cn.gaein.java.video.tool.utils.FileExtensions;
import com.jfoenix.controls.JFXButton;
import javafx.fxml.FXML;
import com.jfoenix.controls.JFXListView;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import uk.co.caprica.vlcj.factory.MediaPlayerFactory;
import uk.co.caprica.vlcj.player.base.MediaPlayer;
import uk.co.caprica.vlcj.player.base.MediaPlayerEventAdapter;
import uk.co.caprica.vlcj.player.embedded.EmbeddedMediaPlayer;

import java.io.File;

import static uk.co.caprica.vlcj.javafx.videosurface.ImageViewVideoSurfaceFactory.videoSurfaceForImageView;

public class MainController {
    private final MediaPlayerFactory mediaPlayerFactory = new MediaPlayerFactory();

    private final EmbeddedMediaPlayer embeddedMediaPlayer;

    @FXML
    public JFXListView<InputVideoCell> inputFileList;
    @FXML
    public ImageView displayView;
    @FXML
    public JFXButton flagStartBtn;
    @FXML
    public JFXButton flagEndBtn;
    @FXML
    public JFXButton flagEditBtn;
    @FXML
    public JFXButton flagCancelBtn;
    @FXML
    public JFXListView outputFileList;
    @FXML
    public JFXButton exportOutputBtn;
    @FXML
    public JFXButton addInputBtn;

    public MainController() {
        embeddedMediaPlayer = mediaPlayerFactory.mediaPlayers().newEmbeddedMediaPlayer();
        embeddedMediaPlayer.events().addMediaPlayerEventListener(new MediaPlayerEventAdapter() {
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
    protected void onAddInputClick() {
        // TODO: move set video surface to init method
        embeddedMediaPlayer.videoSurface().set(videoSurfaceForImageView(displayView));

        var inputFileListArr = inputFileList.getItems();

        var chooser = new FileChooser();

        chooser.setTitle("导入视频文件");
        chooser.getExtensionFilters().addAll(FileExtensions.getVideoExtensions());
        var fileList = chooser.showOpenMultipleDialog(new Stage());

        if (fileList == null)
            return;

        fileList.forEach(file -> {
            var item = new InputVideoCell(new InputVideo(file));

            item.setOnDeleteClicked(e -> {
                // remove button clicked
                inputFileListArr.remove(item);
            });
            item.setOnMouseClicked(e -> {
                // video item clicked
                embeddedMediaPlayer.media().play(file.getPath());
            });

            inputFileListArr.add(item);
        });
    }
}