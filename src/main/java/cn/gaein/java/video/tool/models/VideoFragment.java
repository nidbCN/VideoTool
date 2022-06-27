package cn.gaein.java.video.tool.models;

import net.bramp.ffmpeg.builder.FFmpegBuilder;

import java.util.function.Consumer;

/**
 * @author Gaein
 */
public class VideoFragment {
    private final Video video;
    private final FFmpegBuilder builder = new FFmpegBuilder();
    private VideoTime startTime;
    private VideoTime endTime;

    public VideoFragment(Video video) {
        this(video, null, null);
    }

    public VideoFragment(Video video, VideoTime startTime) {
        this(video, startTime, null);
    }

    public VideoFragment(Video video, VideoTime startTime, VideoTime endTime) {
        this.video = video;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public Video getVideo() {
        return video;
    }

    public VideoTime getStartTime() {
        return startTime;
    }

    public void setStartTime(VideoTime startTime) {
        this.startTime = startTime;
    }

    public VideoTime getEndTime() {
        return endTime;
    }

    public void setEndTime(VideoTime endTime) {
        this.endTime = endTime;
    }

    public void edit(Consumer<? super FFmpegBuilder> action) {
        action.accept(builder);
    }

    @Override
    public String toString() {
        var nullStr = "??:??:??.???";

        return (startTime == null ? nullStr : startTime) +
                "-" +
                (endTime == null ? nullStr : endTime);
    }
}
