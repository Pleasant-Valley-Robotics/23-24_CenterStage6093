package org.firstinspires.ftc.teamcode;

public enum FieldSide {
    @Api RedClose,
    @Api RedFar,
    @Api BlueClose,
    @Api BlueFar;

    public @Api boolean isClose() {
        return this == RedClose || this == BlueClose;
    }

    public @Api boolean isFar() {
        return this == RedFar || this == BlueFar;
    }

    public @Api boolean isRed() {
        return this == RedClose || this == RedFar;
    }

    public @Api boolean isBlue() {
        return this == BlueClose || this == BlueFar;
    }
}
