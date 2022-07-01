package cn.gaein.java.video.tool.models;

import javafx.beans.property.*;

/**
 * @author Gaein
 */
public class FragmentViewModel {
    private final BooleanProperty disableAudio
            = new SimpleBooleanProperty();
    private final BooleanProperty enableCrop
            = new SimpleBooleanProperty();
    private final StringProperty cropFrom
            = new SimpleStringProperty("0");
    private final StringProperty cropTo
            = new SimpleStringProperty("0");
    private final StringProperty cropWidth
            = new SimpleStringProperty("800");
    private final StringProperty cropHeight
            = new SimpleStringProperty("600");

    public BooleanProperty disableAudioProperty() {
        return disableAudio;
    }

    public BooleanProperty enableCropProperty() {
        return enableCrop;
    }

    public StringProperty cropFromProperty() {
        return cropFrom;
    }

    public StringProperty cropToProperty() {
        return cropTo;
    }

    public StringProperty cropWidthProperty() {
        return cropWidth;
    }

    public StringProperty cropHeightProperty() {
        return cropHeight;
    }
}
