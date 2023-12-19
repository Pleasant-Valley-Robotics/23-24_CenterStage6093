package org.firstinspires.ftc.teamcode.utility;

import com.qualcomm.hardware.rev.RevHubOrientationOnRobot;

public class Config {
    // Encoder/Inch = 360/Ticks per motor shaft revolution
    public static final double TICKS_PER_REVOLUTION = 3007.2; //Encoder pulses per rev by supplier information
    public static final double GEARBOX_REDUCTION = 1; //Gearbox on motor * gearbox on robot 26.9:1, this is already included in
    //ticks per revoloution, given by goBuildA.
    public static final double WHEEL_DIAMETER_INCHES = 5.512; //Wheel diameter in inches
    public static final double ENCODER_PER_INCH = (TICKS_PER_REVOLUTION * GEARBOX_REDUCTION) / (WHEEL_DIAMETER_INCHES * Math.PI);

    public static final RevHubOrientationOnRobot HUB_FACING = new RevHubOrientationOnRobot(
            RevHubOrientationOnRobot.LogoFacingDirection.LEFT,
            RevHubOrientationOnRobot.UsbFacingDirection.UP
    );

    public static final double TURNING_P_GAIN = 0.02;
    public static final double STRAFING_P_GAIN = 0.07;
}
