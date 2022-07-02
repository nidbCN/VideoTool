package cn.gaein.java.video.tool.ffmpeg;

import cn.gaein.java.video.tool.ffmpeg.models.ExtFfmpegOption;
import net.bramp.ffmpeg.FFmpegUtils;
import net.bramp.ffmpeg.builder.FFmpegBuilder;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @author Gaein
 */
public class ExtFfmpegBuilder extends FFmpegBuilder {
    private Long startTime;
    private Long stopTime;
    private final ArrayList<ExtFfmpegOption> options
            = new ArrayList<>();

    public ExtFfmpegBuilder setStartTime(long time, TimeUnit units) {
        startTime = units.toMillis(time);
        return this;
    }

    public ExtFfmpegBuilder setStopTime(long time, TimeUnit units) {
        stopTime = units.toMillis(time);
        return this;
    }

    public ExtFfmpegBuilder addOption(String command) {
        if (command == null) {
            throw new IllegalArgumentException("command cannot be null");
        }

        options.add(new ExtFfmpegOption(command));
        return this;
    }

    public ExtFfmpegBuilder addOption(String command, String value) {
        if (command == null) {
            throw new IllegalArgumentException("command cannot be null");
        }

        options.add(new ExtFfmpegOption(command, value));
        return this;
    }

    @Override
    public List<String> build() {
        var result = super.build();
        var list = new ArrayList<>(result);
        var inputIndex = 0;

        var i = 0;
        for (var command : result) {
            if ("-i".equals(command)) {
                inputIndex = i + 2;
                break;
            }
            i++;
        }

        if (startTime != null) {
            list.add(inputIndex++, "-ss");
            list.add(inputIndex++, FFmpegUtils.toTimecode(startTime, TimeUnit.MILLISECONDS));
        }
        // add stop time
        if (stopTime != null) {
            list.add(inputIndex++, "-to");
            list.add(inputIndex++, FFmpegUtils.toTimecode(stopTime, TimeUnit.MILLISECONDS));
        }

        // add options
        for (var option : options) {
            var optionInList = option.getOption();
            list.addAll(list.size() - 1, optionInList);
        }

        return list;
    }
}
