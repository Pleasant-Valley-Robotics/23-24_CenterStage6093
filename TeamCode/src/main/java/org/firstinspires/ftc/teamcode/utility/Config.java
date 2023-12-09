package org.firstinspires.ftc.teamcode.utility;

public class Config {
    // Encoder/Inch = 360/Ticks per motor shaft revolution
    public static final double ENCODER_PER_REV = 7; //Encoder pulses per rev by supplier information
    public static final double GEARBOX_REDUCTION = 19.4 * (7/9); //Gearbox on motor * gearbox on robot
    public static final double WHEEL_DIAMETER_INCHES = 4; //Wheel diameter in inches
    public static final double ENCODER_PER_INCH = (ENCODER_PER_REV * GEARBOX_REDUCTION) / (WHEEL_DIAMETER_INCHES*Math.PI);
}
