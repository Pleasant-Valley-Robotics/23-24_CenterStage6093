package org.firstinspires.ftc.teamcode.opmodes;


import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.hardware.Drivebase;

@TeleOp(name = "BasicTeleOp")
public class BasicTeleOp extends LinearOpMode {
    @Override
    public void runOpMode() {
        telemetry.setAutoClear(false);

        telemetry.addData("Status", "Initializing");
        telemetry.update();

        Drivebase drivebase = new Drivebase(hardwareMap, null);

        telemetry.addData("Status", "Initialized");
        telemetry.update();

        waitForStart();

        while (opModeIsActive()) {
            double yInput = -gamepad1.left_stick_y;
            double xInput = gamepad1.left_stick_x;
            double turnInput = gamepad1.right_stick_x;

            drivebase.mecanumDrive(yInput, xInput, turnInput);
            drivebase.addTelemetry(telemetry);

            telemetry.addData("Status", "Running");
            telemetry.update();
        }

        telemetry.clearAll();

        telemetry.addData("Status", "Finished");
        telemetry.update();
    }
}
