package org.firstinspires.ftc.teamcode.opmodes;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.hardware.Drivebase;

@Autonomous(name = "BasicAuto")
public class BasicAuto extends LinearOpMode {
    @Override
    public void runOpMode() {
        // ALWAYS SET TELEMETRY AUTOCLEAR to TRUE!!!
        telemetry.setAutoClear(true);

        telemetry.addData("Status", "Initializing");
        telemetry.update();

        Drivebase drivebase = new Drivebase(hardwareMap, this::opModeIsActive);

        telemetry.addData("Status", "Initialized");
        telemetry.update();

        waitForStart();

        telemetry.addData("Status", "Running");
        telemetry.update();

        drivebase.driveForward(10.0, 1.0, telemetry);
        drivebase.driveSideways(10.0, 1.0, telemetry);
        drivebase.turnAngle(90.0, 1.0, telemetry);

        telemetry.clearAll();

        telemetry.addData("Status", "Finished");
        telemetry.update();
    }
}
