package cn.gaein.java.video.tool.controllers;

import cn.gaein.java.video.tool.models.VideoFragment;
import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXTextField;
import io.github.palexdev.materialfx.controls.MFXToggleButton;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * @author Gaein
 */
public class FragmentController implements Initializable {
    @FXML
    private MFXButton cancelBtn;
    @FXML
    private MFXButton saveBtn;
    @FXML
    private MFXToggleButton enableCropBtn;
    @FXML
    private MFXTextField cropFromText;
    @FXML
    private MFXTextField cropToText;
    @FXML
    private MFXTextField cropWidthText;
    @FXML
    private MFXTextField cropHeightText;
    @FXML
    private MFXToggleButton disableAudioBtn;

    private final VideoFragment fragment;

    public FragmentController(VideoFragment fragment) {
        this.fragment = fragment;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}
