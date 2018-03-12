package com.kakao.event.queue;

public class TimeProvider {
    private final long timestamp;

    public TimeProvider() {
        this.timestamp = System.currentTimeMillis();
    }

    public long getTimestamp() {
        return this.timestamp;
    }
}
