package cn.gaein.java.video.tool.helper;

/**
 * @author Gaein
 */
public class BoxHelper<T> {
    private T value;

    public BoxHelper() {

    }

    public BoxHelper(T value) {
        this.value = value;
    }

    public T getValue() {
        return value;
    }

    public void setValue(T value) {
        this.value = value;
    }
}
