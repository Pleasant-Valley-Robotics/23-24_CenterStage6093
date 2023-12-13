package org.firstinspires.ftc.teamcode.opmodes;


import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.hardware.Drivebase;
import org.firstinspires.ftc.teamcode.hardware.Suspension;

@TeleOp(name = "Jaren's TestTele")
public class TeleOpTestFOME extends LinearOpMode {
    @Override
    public void runOpMode() {
        telemetry.addData("Status", "Initialized");
        telemetry.update();

        waitForStart();
        while (opModeIsActive()) {
            Suspension suspension = new Suspension(hardwareMap);
            Drivebase drivebase = new Drivebase(hardwareMap, null);

            drivebase.mecanumDrive(gamepad1);

            suspension.spoolDaSpool(gamepad2, null);
        }

        telemetry.clearAll();
        telemetry.addData("Status", "Finished");
        telemetry.update();
    }
}
