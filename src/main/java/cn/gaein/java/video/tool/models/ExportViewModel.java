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
    private final DoubleProperty videoRate
            = new SimpleDoubleProperty(2048);
    private final DoubleProperty audioRate
            = new SimpleDoubleProperty(512);

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

    public DoubleProperty videoRateProperty() {
        return videoRate;
    }

    public DoubleProperty audioRateProperty() {
        return audioRate;
    }
}
