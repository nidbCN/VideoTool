package cn.gaein.java.video.tool.models;

import net.bramp.ffmpeg.builder.FFmpegBuilder;

import java.util.function.Consumer;

/**
 * @author Gaein
 */
public class VideoFragment {
    private final Video video;
    private final FFmpegBuilder builder = new FFmpegBuilder();
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
