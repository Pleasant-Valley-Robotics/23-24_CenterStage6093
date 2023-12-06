package org.firstinspires.ftc.teamcode.hardware;

import static org.firstinspires.ftc.teamcode.Config.ENCODER_PER_INCH;

import androidx.annotation.Nullable;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.IMU;

import org.firstinspires.ftc.robotcore.external.Supplier;
import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.teamcode.Api;

/**
 * Class that contains the entire drivebase for the robot. Contains methods to move the robot both in auto and in teleop.
 */
public class Drivebase {
    private final DcMotor frdrive;
    private final DcMotor fldrive;
    private final DcMotor brdrive;
    private final DcMotor bldrive;
    private final IMU imu;
    private final Supplier<Boolean> opModeIsActive;

    /**
     * Uses `hardwareMap` to initialize the motors and imu.
     *
     * @param hardwareMap    The hardware map to use for initialization.
     * @param opModeIsActive Supplier that returns false if the program needs to stop.
     */
    public @Api Drivebase(HardwareMap hardwareMap, Supplier<Boolean> opModeIsActive) {
        frdrive = hardwareMap.get(DcMotor.class, "FRDrive");
        fldrive = hardwareMap.get(DcMotor.class, "FLDrive");
        brdrive = hardwareMap.get(DcMotor.class, "BRDrive");
        bldrive = hardwareMap.get(DcMotor.class, "BLDrive");
        imu = hardwareMap.get(IMU.class, "IMU");
        this.opModeIsActive = opModeIsActive;

        fldrive.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        frdrive.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        bldrive.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        brdrive.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        fldrive.setDirection(DcMotorSimple.Direction.REVERSE);
        frdrive.setDirection(DcMotorSimple.Direction.FORWARD);
        bldrive.setDirection(DcMotorSimple.Direction.REVERSE);
        brdrive.setDirection(DcMotorSimple.Direction.FORWARD);

        setMotorModes(DcMotor.RunMode.RUN_USING_ENCODER);
    }

    private void setMotorModes(DcMotor.RunMode mode) {
        fldrive.setMode(mode);
        frdrive.setMode(mode);
        bldrive.setMode(mode);
        brdrive.setMode(mode);
    }

    /**
     * Adds the position of all the motors and the current heading to telemetry.
     * Note that this is already called for you in auto, so you should only be calling this in teleop.
     *
     * @param telemetry The telemetry board to add the data to.
     */
    public @Api void addTelemetry(Telemetry telemetry) {
        telemetry.addData("FLDrive", fldrive.getCurrentPosition());
        telemetry.addData("FRDrive", frdrive.getCurrentPosition());
        telemetry.addData("BLDrive", bldrive.getCurrentPosition());
        telemetry.addData("BRDrive", brdrive.getCurrentPosition());
        telemetry.addData("Heading", getHeading());
        telemetry.update();
    }


    /**
     * For use in teleop, controlled by a controller.
     *
     * @param yInput    Driving input. Negative is back, positive is forward. [-1, 1].
     * @param xInput    Strafing input. Negative is left, positive is right. [-1, 1].
     * @param turnInput Turning input. Negative is ccw, positive is clockwise. [-1, 1].
     */
    public @Api void mecanumDrive(double yInput, double xInput, double turnInput) {
        double frontLeft = yInput + xInput + turnInput;
        double frontRight = yInput - xInput - turnInput;
        double backLeft = yInput - xInput + turnInput;
        double backRight = yInput + xInput - turnInput;

        double clamp = maxOf(1, frontLeft, frontRight, backLeft, backRight);

        fldrive.setPower(frontLeft / clamp);
        frdrive.setPower(frontRight / clamp);
        bldrive.setPower(backLeft / clamp);
        brdrive.setPower(backRight / clamp);
    }


    /**
     * For use in auto. Drives forward a specified distance, using the encoders.
     *
     * @param inches    The amount of inches to drive forward, negative is backwards.
     * @param power     How fast to drive. (0, 1].
     * @param telemetry Pass telemetry if you want this method to log to telemetry.
     * @see Drivebase#driveSideways
     * @see Drivebase#turnAngle
     * @see Drivebase#turnToAngle
     */
    public @Api void driveForward(double inches, double power, @Nullable Telemetry telemetry) {
        setMotorModes(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        double target = inches * ENCODER_PER_INCH;

        fldrive.setTargetPosition((int) target);
        frdrive.setTargetPosition((int) target);
        bldrive.setTargetPosition((int) target);
        brdrive.setTargetPosition((int) target);

        fldrive.setPower(power);
        frdrive.setPower(power);
        bldrive.setPower(power);
        brdrive.setPower(power);

        setMotorModes(DcMotor.RunMode.RUN_TO_POSITION);

        waitForMotors(telemetry);

        fldrive.setPower(0);
        frdrive.setPower(0);
        bldrive.setPower(0);
        brdrive.setPower(0);
    }

    /**
     * For use in auto. Strafes sideways a specified amount of inches, using encoders.
     *
     * @param inches    How many inches to strafe, negative is left.
     * @param power     How fast to strafe. (0, 1].
     * @param telemetry Pass this if you want the method to log to telemetry.
     * @see Drivebase#driveForward
     * @see Drivebase#turnAngle
     * @see Drivebase#turnToAngle
     */
    public @Api void driveSideways(double inches, double power, @Nullable Telemetry telemetry) {
        setMotorModes(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        double target = inches * ENCODER_PER_INCH;

        fldrive.setTargetPosition((int) target);
        frdrive.setTargetPosition((int) -target);
        bldrive.setTargetPosition((int) -target);
        brdrive.setTargetPosition((int) target);

        fldrive.setPower(power);
        frdrive.setPower(-power);
        bldrive.setPower(-power);
        brdrive.setPower(power);

        setMotorModes(DcMotor.RunMode.RUN_TO_POSITION);

        waitForMotors(telemetry);

        fldrive.setPower(0);
        frdrive.setPower(0);
        bldrive.setPower(0);
        brdrive.setPower(0);
    }

    /**
     * For auto. Turns to a specified angle, without resetting the heading.
     *
     * @param angle     How many degrees to turn. [-180, 180].
     * @param power     How fast to turn. (0, 1].
     * @param telemetry Pass this if you want this method to log to telemetry.
     * @see Drivebase#driveSideways
     * @see Drivebase#driveForward
     * @see Drivebase#turnAngle
     */
    public @Api void turnToAngle(double angle, double power, @Nullable Telemetry telemetry) {
        setMotorModes(DcMotor.RunMode.RUN_USING_ENCODER);

        while (opModeIsActive.get() && Math.abs(getHeading() - angle) > 10) {
            if (angle > 0) {
                fldrive.setPower(power);
                frdrive.setPower(-power);
                bldrive.setPower(power);
                brdrive.setPower(-power);
            } else {
                fldrive.setPower(-power);
                frdrive.setPower(power);
                bldrive.setPower(-power);
                brdrive.setPower(power);
            }

            if (telemetry == null) continue;
            addTelemetry(telemetry);
        }

        fldrive.setPower(0);
        frdrive.setPower(0);
        bldrive.setPower(0);
        brdrive.setPower(0);
    }

    /**
     * For auto. Turns a specified angle in degrees, as an offset from the current heading.
     *
     * @param angle     How many degrees to turn. [-180, 180].
     * @param power     How fast to turn. (0, 1].
     * @param telemetry Pass this if you want this method to log to telemetry.
     * @see Drivebase#driveSideways
     * @see Drivebase#driveForward
     * @see Drivebase#turnToAngle
     */
    public @Api void turnAngle(double angle, double power, @Nullable Telemetry telemetry) {
        imu.resetYaw();
        turnToAngle(angle, power, telemetry);
    }

    private void waitForMotors(@Nullable Telemetry telemetry) {
        while (opModeIsActive.get() && (fldrive.isBusy() || frdrive.isBusy() || bldrive.isBusy() || brdrive.isBusy())) {
            if (telemetry == null) continue;
            addTelemetry(telemetry);
        }
    }

    private double maxOf(double... doubles) {
        double max = -Double.MAX_VALUE;
        for (double x : doubles) if (x > max) max = x;
        return max;
    }

    private double getHeading() {
        return imu.getRobotYawPitchRollAngles().getYaw(AngleUnit.DEGREES);
    }
}





















