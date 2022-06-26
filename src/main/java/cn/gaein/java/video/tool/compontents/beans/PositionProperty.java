package cn.gaein.java.video.tool.compontents.beans;

import javafx.beans.InvalidationListener;
import javafx.beans.property.DoubleProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableDoubleValue;
import javafx.beans.value.ObservableValue;
import uk.co.caprica.vlcj.player.base.MediaPlayer;

import java.util.ArrayList;

public class PositionProperty extends DoubleProperty {
    private final ArrayList<ChangeListener<? super Number>> changeListenerList
            = new ArrayList<>();
    private final ArrayList<InvalidationListener> invalidationListenerList
            = new ArrayList<>();

    private final MediaPlayer player;
    private double value;
    private final ObservableDoubleValue fakeValue = new ObservableDoubleValue() {
        @Override
        public double get() {
            return value;
        }

        @Override
        public int intValue() {
            return (int) get();
        }

        @Override
        public long longValue() {
            return intValue();
        }

        @Override
        public float floatValue() {
            return (float) get();
        }

        @Override
        public double doubleValue() {
            return get();
        }

        @Override
        public Number getValue() {
            return get();
        }

        @Override
        public void addListener(ChangeListener<? super Number> listener) {
            // no-pe
        }

        @Override
        public void removeListener(ChangeListener<? super Number> listener) {
            // no-pe
        }

        @Override
        public void addListener(InvalidationListener listener) {
            // no-pe
        }

        @Override
        public void removeListener(InvalidationListener listener) {
            // no-pe
        }
    };

    public PositionProperty(MediaPlayer player) {
        this.player = player;
    }

    @Override
    public void bind(ObservableValue<? extends Number> rawObservable) {
        // no-pe
    }

    @Override
    public void unbind() {
        // no-pe
    }

    @Override
    public boolean isBound() {
        return false;
    }

    @Override
    public Object getBean() {
        return null;
    }

    @Override
    public String getName() {
        return "PositionProperty";
    }

    @Override
    public double get() {
        var position = player.status().position();
        return position < 0 ? 0 : position * 1000;
    }

    @Override
    public void set(double newValue) {
        if (value != newValue) {
            value = newValue;
        }

        player.controls().setPosition((float) (value / 1000));
    }

    @Override
    public void addListener(ChangeListener<? super Number> listener) {
        changeListenerList.add(listener);
    }

    @Override
    public void removeListener(ChangeListener<? super Number> listener) {
        changeListenerList.remove(listener);
    }

    @Override
    public void addListener(InvalidationListener listener) {
        invalidationListenerList.add(listener);
    }

    @Override
    public void removeListener(InvalidationListener listener) {
        invalidationListenerList.remove(listener);
    }

    public void update() {
        changeListenerList.forEach(action -> action.changed(fakeValue, value, get()));
        invalidationListenerList.forEach(action -> action.invalidated(fakeValue));
    }

}
