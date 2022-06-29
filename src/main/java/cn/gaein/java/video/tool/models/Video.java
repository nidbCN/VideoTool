package cn.gaein.java.video.tool.models;

import javafx.scene.text.Text;

import java.io.File;
import java.util.ArrayList;

/**
 * @author Gaein
 */
public class Video {
    private final File file;
    private final ArrayList<VideoFragment> fragments = new ArrayList<>();
    private VideoFragment fragmentNotComplete;

    public Video(File inputFile) {
        file = inputFile;
    }

    public Video(String path) {
        this(new File(path));
    }

    public File getFile() {
        return file;
    }

    public boolean getIsExists() {
        return file.exists();
    }

    public String getDisplayName() {
        // TODO: calculate text display width

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

    public ArrayList<VideoFragment> getFragments() {
        return fragments;
    }

    public boolean startFragment(VideoTime startTime) {
        if (fragmentNotComplete != null) {
            return false;
        }

        fragmentNotComplete = new VideoFragment(this, startTime, fragments.size() + 1);
        return true;
    }

    public VideoFragment completeFragment(VideoTime endTime) {
        if (fragmentNotComplete == null) {
            return null;
        }
        if (endTime.getTime() < fragmentNotComplete.getStartTime().getTime()) {
            return null;
        }

        var result = fragmentNotComplete;
        result.setEndTime(endTime);
        fragments.add(result);

        fragmentNotComplete = null;
        return result;
    }

    public void deprecateFragment() {
        if (fragmentNotComplete != null) {
            fragmentNotComplete = null;
        }
    }

    @Override
    public String toString() {
        return getDisplayName();
    }
}
