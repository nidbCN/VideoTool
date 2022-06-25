package cn.gaein.java.video.tool.models;

import net.bramp.ffmpeg.FFmpeg;
import net.bramp.ffmpeg.builder.FFmpegBuilder;

import java.io.File;

public class InputVideo {
    private final File file;
    private final FFmpegBuilder builder = new FFmpegBuilder();

    public InputVideo(File inputFile) {
        file = inputFile;
    }

    public InputVideo(String path) {
        file = new File(path);
        builder.setInput(file.getPath());
    }

    public File getFile() {
        return file;
    }

    public boolean getIsExists() {
        return file.exists();
    }

    public String getDisplayName() {
        var LENGTH = 15;

        var fullName = file.getName();
        var extIndex = fullName.lastIndexOf('.');
        var pureName = fullName.substring(0, extIndex);
        var extName = fullName.substring(extIndex);

        var builder = new StringBuilder(fullName.length());

        if (pureName.length() <= LENGTH) {
            builder.append(fullName);
        } else {
            builder.append(pureName, 0, LENGTH - 6);
            builder.append("...");
            builder.append(pureName, pureName.length() - 3, pureName.length() - 1);
            builder.append(extName);
        }

        return builder.toString();
    }

    @Override
    public String toString() {
        return getDisplayName();
    }

    public FFmpegBuilder getBuilder() {
        return builder;
    }
}
