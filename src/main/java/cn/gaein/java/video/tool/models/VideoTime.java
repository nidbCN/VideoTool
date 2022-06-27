package cn.gaein.java.video.tool.models;

import java.util.Calendar;
import java.util.Date;

public class VideoTime {
    private int hour;
    private int minute;
    private int second;
    private int millSec;
    private long time;

    public VideoTime() {

    }

    public VideoTime(long time) {
        this.time = time;
        setTime(time);
    }

    public String toLongString() {
        return String.format("%02d:%02d:%02d.%03d", hour, minute, second, millSec);
    }

    public String toShortString() {
        return hour + ":" + minute + ":" + second + "." + millSec;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        if (time < 0) {
            throw new IllegalArgumentException("time cannot be less than zero");
        }

        millSec = (int) (time % 1000);
        var tempSec = (int) (time / 1000);
        second = tempSec % 60;
        var tempMin = tempSec / 60;
        minute = tempMin % 60;
        hour = tempMin / 60;
    }

    public int getHour() {
        return hour;
    }

    public int getMinute() {
        return minute;
    }

    public int getSecond() {
        return second;
    }

    public int getMillSec() {
        return millSec;
    }

    @Override
    public String toString() {
        return toLongString();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof VideoTime otherTime) {
            return getTime() == otherTime.getTime();
        } else if (obj instanceof Date otherTime) {
            return getTime() == otherTime.getTime();
        } else if (obj instanceof Calendar otherTime) {
            return getTime() == otherTime.getTimeInMillis();
        } else if (obj instanceof Number otherTime) {
            return getTime() == otherTime.longValue();
        }
        return super.equals(obj);
    }
}