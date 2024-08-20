package com.casumo.videorental.enums;

public enum VideoType {
    NEW_RELEASES("NEW_RELEASES"),
    REGULAR("REGULAR"),
    OLD_RELEASES("OLD_RELEASES");
    private final String value;
    VideoType(String videoType) {
        value = videoType;
    }

    @Override
    public String toString() {
        return value;
    }

    public static VideoType fromValue(String value) {
        for (VideoType type : VideoType.values()) {
            if (type.toString().equals(value)) {
                return type;
            }
        }
        throw new IllegalArgumentException("Unknown value: " + value);
    }
}
