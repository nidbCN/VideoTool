package cn.gaein.java.video.tool.models;

import javafx.beans.property.*;

/**
 * @author Gaein
 */
public class ExportViewModel {
    private final BooleanProperty disableAudio
            = new SimpleBooleanProperty();
    private final BooleanProperty enableEncode
            = new SimpleBooleanProperty();
    private final StringProperty videoCodec
            = new SimpleStringProperty();
    private final StringProperty audioCodec
            = new SimpleStringProperty();
    private final StringProperty videoRate
            = new SimpleStringProperty("2048");
    private final StringProperty audioRate
            = new SimpleStringProperty("512");

    public BooleanProperty disableAudioProperty() {
        return disableAudio;
    }

    public BooleanProperty enableEncodeProperty() {
        return enableEncode;
    }

    public StringProperty videoCodecProperty() {
        return videoCodec;
    }

    public StringProperty audioCodecProperty() {
        return audioCodec;
    }

    public StringProperty videoRateProperty() {
        return videoRate;
    }

    public StringProperty audioRateProperty() {
        return audioRate;
    }
}
