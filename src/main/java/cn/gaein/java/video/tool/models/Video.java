package cn.gaein.java.video.tool.models;

import net.bramp.ffmpeg.builder.FFmpegBuilder;

import java.io.File;

/**
 * @author Gaein
 */
public class Video {
    private final File file;
    private final FFmpegBuilder builder = new FFmpegBuilder();

    public Video(File inputFile) {
        file = inputFile;
    }

    public Video(String path) {
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
        var length = 15;

        var fullName = file.getName();
        var extIndex = fullName.lastIndexOf('.');
        var pureName = fullName.substring(0, extIndex);
        var extName = fullName.substring(extIndex);

        var builder = new StringBuilder(fullName.length());

        if (pureName.length() <= length) {
            builder.append(fullName);
        } else {
            builder.append(pureName, 0, length - 6);
            builder.append("...");
            builder.append(pureName, pureName.length() - 3, pureName.length() - 1);
            builder.append(extName);
        }

        return builder.toString();
    }

    public FFmpegBuilder getBuilder() {
        return builder;
    }

    @Override
    public String toString() {
        return getDisplayName();
    }
}
