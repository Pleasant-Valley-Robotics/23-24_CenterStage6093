package org.firstinspires.ftc.teamcode;

import static org.firstinspires.ftc.teamcode.Config.ENCODER_PER_INCH;

import androidx.annotation.Nullable;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.IMU;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;

public class Drivebase {
    private final DcMotor frdrive;
    private final DcMotor fldrive;
    private final DcMotor brdrive;
    private final DcMotor bldrive;
    private final IMU imu;


    public Drivebase(HardwareMap hardwareMap) {
        frdrive = hardwareMap.get(DcMotor.class, "FRDrive");
        fldrive = hardwareMap.get(DcMotor.class, "FLDrive");
        brdrive = hardwareMap.get(DcMotor.class, "BRDrive");
        bldrive = hardwareMap.get(DcMotor.class, "BLDrive");
        imu = hardwareMap.get(IMU.class, "IMU");

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

    private void addTelemetry(Telemetry telemetry) {
        telemetry.addData("FLDrive", fldrive.getCurrentPosition());
        telemetry.addData("FRDrive", frdrive.getCurrentPosition());
        telemetry.addData("BLDrive", bldrive.getCurrentPosition());
        telemetry.addData("BRDrive", brdrive.getCurrentPosition());
        telemetry.addData("Heading", getHeading());
    }

    private void waitForMotors(@Nullable Telemetry telemetry) {
        while (fldrive.isBusy() || frdrive.isBusy() || bldrive.isBusy() || brdrive.isBusy()) {
            if (telemetry == null) continue;
            addTelemetry(telemetry);
        }
    }

    public void mecanumDrive(double xInput, double yInput, double turnInput) {
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

    private double maxOf(double... doubles) {
        double max = 0;
        for (double x : doubles) if (x > max) max = x;
        return max;
    }

    public void driveForward(double inches, double power, @Nullable Telemetry telemetry) {
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

    public void driveSideways(double inches, double power, @Nullable Telemetry telemetry) {
        setMotorModes(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        double target = inches * ENCODER_PER_INCH;

        fldrive.setTargetPosition((int) target);
        frdrive.setTargetPosition((int) target);
        bldrive.setTargetPosition((int) target);
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

    private double getHeading() {
        return imu.getRobotYawPitchRollAngles().getYaw(AngleUnit.DEGREES);
    }

    public void turnAngle(double angle, double power, @Nullable Telemetry telemetry) {
        setMotorModes(DcMotor.RunMode.RUN_USING_ENCODER);
        imu.resetYaw();

        while (Math.abs(getHeading() - angle) > 10) {
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
}




















