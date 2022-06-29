package cn.gaein.java.video.tool.helper;

import java.util.HashMap;

/**
 * @author Gaein
 */
public class ResourceHelper {
    private final HashMap<String, String> storage
            = new HashMap<>();

    public String getResourceLocation(String key) {
        return storage.get(key);
    }

    public void setResourceLocation(String key, String location) {
        storage.put(key, location);
    }
}
