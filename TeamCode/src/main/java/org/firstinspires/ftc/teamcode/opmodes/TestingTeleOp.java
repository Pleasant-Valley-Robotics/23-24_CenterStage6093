package org.firstinspires.ftc.teamcode.opmodes;


import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.teamcode.hardware.Drivebase;
import org.firstinspires.ftc.teamcode.hardware.DroneLauncher;
import org.firstinspires.ftc.teamcode.hardware.PixelDropper;

@TeleOp(name = "TestingTeleOp")
public class TestingTeleOp extends LinearOpMode {
    @Override
    public void runOpMode() {
        // ALWAYS SET TELEMETRY AUTOCLEAR to TRUE!!!
        // otherwise, it overflows the log.
        telemetry.setAutoClear(true);

        telemetry.addData("Status", "Initializing");
        telemetry.update();

        Drivebase drivebase = new Drivebase(hardwareMap, null);
        PixelDropper dropper = new PixelDropper(hardwareMap);
        DroneLauncher launcher = new DroneLauncher(hardwareMap, null);

        telemetry.addData("Status", "Initialized");
        telemetry.update();

        drivebase.clearEncoder(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        waitForStart();

        while (opModeIsActive()) {
            if (gamepad1.triangle) dropper.dropPixel();
            if (gamepad1.right_bumper) launcher.LaunchDathOe();
            // FTC controllers have inverted joystick y values

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
