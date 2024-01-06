package org.firstinspires.ftc.teamcode.utility;

import com.qualcomm.hardware.rev.RevHubOrientationOnRobot;

public class Config {
    // Encoder/Inch = 360/Ticks per motor shaft revolution
    public static final double TICKS_PER_REVOLUTION = 537.6; //Encoder pulses per rev by supplier information
    public static final double GEARBOX_REDUCTION = 7.0 / 9.0; //Gearbox on motor * gearbox on robot
    public static final double WHEEL_DIAMETER_INCHES = 4; //Wheel diameter in inches
    public static final double ENCODER_PER_INCH = (TICKS_PER_REVOLUTION * GEARBOX_REDUCTION) / (WHEEL_DIAMETER_INCHES * Math.PI);

    public static final RevHubOrientationOnRobot HUB_FACING = new RevHubOrientationOnRobot(
            RevHubOrientationOnRobot.LogoFacingDirection.LEFT,
            RevHubOrientationOnRobot.UsbFacingDirection.UP
    );

    public static class DRIVING {
        public static final double TURNING_P_GAIN = 0.02;
        public static final double DRIVING_P_GAIN = 2;
    }


    public static class APRILTAGS {
        public static final double X_P_GAIN = 0.1;
        public static final double X_D_GAIN = -0.02;
        public static final double Y_P_GAIN = 0.06;
        public static final double Y_D_GAIN = -0.015;
        public static final double YAW_P_GAIN = 0.03;

        public static final int ID_BLUE_LEFT = 1;
        public static final int ID_BLUE_MIDDLE = 2;
        public static final int ID_BLUE_RIGHT = 3;
        public static final int ID_RED_LEFT = 6;
        public static final int ID_RED_MIDDLE = 5;
        public static final int ID_RED_RIGHT = 4;
    }
}
