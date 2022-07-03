package cn.gaein.java.video.tool.ffmpeg.listener;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import net.bramp.ffmpeg.progress.Progress;
import net.bramp.ffmpeg.progress.ProgressListener;

import java.util.concurrent.TimeUnit;

/**
 * @author Gaein
 */
public class ExtFfmpegProgressListener implements ProgressListener {
    private final long totalTime;
    private final DoubleProperty workProgress
            = new SimpleDoubleProperty(0.0);

    public DoubleProperty workProgressProperty() {
        return workProgress;
    }

    public ExtFfmpegProgressListener(long totalTime) {
        this.totalTime = totalTime;
    }

    @Override
    public void progress(Progress progress) {
        var percent = (double) progress.out_time_ns / TimeUnit.MILLISECONDS.toNanos(totalTime);
        workProgress.set(percent);
    }
}
