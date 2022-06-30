package cn.gaein.java.video.tool.models;

import cn.gaein.java.video.tool.ffmpeg.ExtFfmpegBuilder;

import java.util.function.Consumer;

/**
 * @author Gaein
 */
public class VideoFragment {
    private final Video video;
    private final ExtFfmpegBuilder builder = new ExtFfmpegBuilder();
    private final int fragmentId;
    private VideoTime startTime;
    private VideoTime endTime;

    public VideoFragment(Video video, int fragmentId) {
        this(video, fragmentId, null, null);
    }

    public VideoFragment(Video video, VideoTime startTime, int fragmentId) {
        this(video, fragmentId, startTime, null);
    }

    public VideoFragment(Video video, int fragmentId, VideoTime startTime, VideoTime endTime) {
        this.video = video;
        this.fragmentId = fragmentId;
        this.startTime = startTime;
        this.endTime = endTime;
        builder
                .setInput(video.getFile().getPath())
                .overrideOutputFiles(true);
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

    public void edit(Consumer<? super ExtFfmpegBuilder> action) {
        action.accept(builder);
    }

    public ExtFfmpegBuilder getBuilder() {
        return builder;
    }

    public String getDisplayName() {
        var videoName = video.getDisplayName();
        return videoName + "-" + fragmentId;
    }

    public String getFullDisplayName() {
        var videoName = video.getDisplayName();
        var nullStr = "??:??:??.???";

        var timeStr = (startTime == null ? nullStr : startTime) +
                "-" +
                (endTime == null ? nullStr : endTime);
        return videoName + "_" + timeStr;
    }

    @Override
    public String toString() {
        return getFullDisplayName();
    }
}
