package cn.gaein.java.video.tool.models;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * @author Gaein
 */
public class FragmentViewModel {
    private final BooleanProperty disableAudio
            = new SimpleBooleanProperty();

    public boolean isDisableAudio() {
        return disableAudio.get();
    }

    public boolean isEnableCrop() {
        return enableCrop.get();
    }

    public String getCropFrom() {
        return cropFrom.get();
    }

    public String getCropTo() {
        return cropTo.get();
    }

    public String getCropWidth() {
        return cropWidth.get();
    }

    public String getCropHeight() {
        return cropHeight.get();
    }

    public boolean isEnableEncode() {
        return enableEncode.get();
    }

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
    private final BooleanProperty enableEncode
            = new SimpleBooleanProperty();

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

    public BooleanProperty enableEncodeProperty() {
        return enableEncode;
    }
}
