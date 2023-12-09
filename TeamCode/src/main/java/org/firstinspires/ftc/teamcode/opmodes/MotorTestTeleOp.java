package org.firstinspires.ftc.teamcode.opmodes;


import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.teamcode.hardware.Drivebase;

@TeleOp(name = "MotorTestTeleOp")
public class MotorTestTeleOp extends LinearOpMode {
    @Override
    public void runOpMode() {
        // ALWAYS SET TELEMETRY AUTOCLEAR to TRUE!!!
        // otherwise, it overflows the log.
        telemetry.setAutoClear(true);

        telemetry.addData("Status", "Initializing");
        telemetry.update();

        Drivebase drivebase = new Drivebase(hardwareMap, null);

        telemetry.addData("Status", "Initialized");
        telemetry.update();

        drivebase.clearEncoder(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        waitForStart();

        while (opModeIsActive()) {
            double input = gamepad1.right_trigger;

            if (gamepad1.cross) {
                drivebase.setMotorPowers(0.0, 0.0, 0.0, input);
            } else if (gamepad1.square) {
                drivebase.setMotorPowers(0.0, 0.0, input, 0.0);
            } else if (gamepad1.circle) {
                drivebase.setMotorPowers(0.0, input, 0.0, 0.0);
            } else if (gamepad1.triangle) {
                drivebase.setMotorPowers(input, 0.0, 0.0, 0.0);
            } else {
                drivebase.setMotorPowers(0.0, 0.0, 0.0, 0.0);
            }

            drivebase.addTelemetry(telemetry);

            telemetry.addData("Status", "Running");
            telemetry.update();
        }

        telemetry.clearAll();

        telemetry.addData("Status", "Finished");
        telemetry.update();
    }
}
