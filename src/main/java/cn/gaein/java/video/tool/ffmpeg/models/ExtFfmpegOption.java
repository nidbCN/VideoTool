package cn.gaein.java.video.tool.ffmpeg.models;

import java.util.Collection;
import java.util.List;

/**
 * @author Gaein
 */
public class ExtFfmpegOption {
    private final String command;
    private final String value;

    public ExtFfmpegOption(String command) {
        this(command, null);
    }

    public ExtFfmpegOption(String command, String value) {
        this.command = command;
        this.value = value;
    }

    public Collection<String> get() {
        if (value == null) {
            return List.of(command);
        }
        return List.of(command, value);
    }
}
