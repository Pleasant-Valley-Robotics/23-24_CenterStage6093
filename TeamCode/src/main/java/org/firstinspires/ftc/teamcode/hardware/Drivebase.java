package org.firstinspires.ftc.teamcode.hardware;

import static org.firstinspires.ftc.teamcode.utility.Config.ENCODER_PER_INCH;
import static org.firstinspires.ftc.teamcode.utility.Config.HUB_FACING;
import static org.firstinspires.ftc.teamcode.utility.Config.TURNING_P_GAIN;

import androidx.annotation.Nullable;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.IMU;
import com.qualcomm.robotcore.util.Range;

import org.firstinspires.ftc.robotcore.external.Supplier;
import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.teamcode.utility.Api;

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
        imu.initialize(new IMU.Parameters(HUB_FACING));

        this.opModeIsActive = opModeIsActive;

        fldrive.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        frdrive.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        bldrive.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        brdrive.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        fldrive.setDirection(DcMotorSimple.Direction.FORWARD);
        frdrive.setDirection(DcMotorSimple.Direction.REVERSE);
        bldrive.setDirection(DcMotorSimple.Direction.FORWARD);
        brdrive.setDirection(DcMotorSimple.Direction.REVERSE);

        setMotorModes(DcMotor.RunMode.RUN_USING_ENCODER);
    }

    /**
     * Utility method to set all the motor modes at the same time.
     */
    private void setMotorModes(DcMotor.RunMode mode) {
        fldrive.setMode(mode);
        frdrive.setMode(mode);
        bldrive.setMode(mode);
        brdrive.setMode(mode);
    }

    /**
     * Utility method to set all the motor powers at the same time.
     */
    private void setMotorPowers(double power) {
        fldrive.setPower(power);
        frdrive.setPower(power);
        bldrive.setPower(power);
        brdrive.setPower(power);
    }

    /**
     * Utility method to set all the motor powers individually.
     */
    public void setMotorPowers(double flpower, double frpower, double blpower, double brpower) {
        fldrive.setPower(flpower);
        frdrive.setPower(frpower);
        bldrive.setPower(blpower);
        brdrive.setPower(brpower);
    }

    /**
     * Utility method to set all the motor targets individually.
     */
    private void setMotorTargets(int fltarget, int frtarget, int bltarget, int brtarget) {
        fldrive.setTargetPosition(fltarget);
        frdrive.setTargetPosition(frtarget);
        bldrive.setTargetPosition(bltarget);
        brdrive.setTargetPosition(brtarget);
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
     * For use wherever.
     * This resets the drivebase encoders.
     * Called before `waitForStart()` and after drivebase init.
     *
     * @param choice If passed, what mode to set the motors to after resetting them, otherwise whatever they were before.
     */

    public @Api void clearEncoder(@Nullable DcMotor.RunMode choice) {
        DcMotor.RunMode oldMode = choice == null ? fldrive.getMode() : choice;

        setMotorModes(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        setMotorModes(oldMode);
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

        setMotorPowers(
                frontLeft / clamp,
                frontRight / clamp,
                backLeft / clamp,
                backRight / clamp
        );
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
        setMotorTargets((int) target, (int) target, (int) target, (int) target);

        setMotorModes(DcMotor.RunMode.RUN_TO_POSITION);
        setMotorPowers(power);
        waitForMotors(telemetry);

        setMotorPowers(0);
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
        setMotorTargets((int) target, (int) -target, (int) -target, (int) target);

        setMotorModes(DcMotor.RunMode.RUN_TO_POSITION);
        setMotorPowers(power);
        waitForMotors(telemetry);

        setMotorPowers(0);
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

        while (opModeIsActive.get() && Math.abs(getHeading() - angle) > 1) {
            final double adjustedPower = power * getTurningCorrection(angle);

            setMotorPowers(adjustedPower, -adjustedPower, adjustedPower, -adjustedPower);

            if (telemetry == null) continue;
            addTelemetry(telemetry);
        }

        setMotorPowers(0);
    }

    /**
     * Ensures that an angle is within [-180, 180].
     * @param n Angle in degrees.
     * @return Angle wrapped into [-180, 180].
     */
    private static double wrapAngle(double n) {
        double x = (n % 360 + 360) % 360;
        return x < 180 ? x : x - 360;
    }

    private double getTurningCorrection(double angle) {
        return Range.clip(wrapAngle(angle - getHeading()) * TURNING_P_GAIN, -1, 1);
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

    /**
     * Waits until the motors are finished moving, or the driver presses stop.
     *
     * @param telemetry Pass this if you want to log to telemetry.
     */
    private void waitForMotors(@Nullable Telemetry telemetry) {
        while (opModeIsActive.get() && (fldrive.isBusy() || frdrive.isBusy() || bldrive.isBusy() || brdrive.isBusy())) {
            if (telemetry == null) continue;
            addTelemetry(telemetry);
        }
    }

    /**
     * @param doubles Numbers to look over.
     * @return The maximum number.
     */
    private double maxOf(double... doubles) {
        double max = -Double.MAX_VALUE;
        for (double x : doubles) if (x > max) max = x;
        return max;
    }

    /**
     * @return The robot heading in degrees.
     */
    private double getHeading() {
        return imu.getRobotYawPitchRollAngles().getYaw(AngleUnit.DEGREES);
    }
}
