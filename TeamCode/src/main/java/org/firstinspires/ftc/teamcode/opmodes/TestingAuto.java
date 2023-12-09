package org.firstinspires.ftc.teamcode.opmodes;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.hardware.Drivebase;

@Autonomous(name = "TestingAuto")
public class TestingAuto extends LinearOpMode {
    @Override
    public void runOpMode() {
        // ALWAYS SET TELEMETRY AUTOCLEAR to TRUE!!!
        // INIT CODE.
        telemetry.setAutoClear(true);

        telemetry.addData("Status", "Initializing");
        telemetry.update();

        Drivebase drivebase = new Drivebase(hardwareMap, this::opModeIsActive);
//        VisionCamera camera = new VisionCamera(hardwareMap, FieldSide.BlueClose);

        telemetry.addData("Status", "Initialized");
        telemetry.update();
        waitForStart();
        // RUNNING CODE.

        drivebase.driveForward(24.0, 0.25, telemetry);

//        switch (Objects.requireNonNull(camera.getCubePrediction())) {
//            case Right:
//                drivebase.turnAngle(90, 1, null);
//                break;
//            case Left:
//                drivebase.turnAngle(-90, 1, null);
//                break;
//            case Middle:
//                drivebase.driveForward(10, 1, null);
//                break;
//            default:
//
//                break;
//        }


        telemetry.addData("Status", "Running");
        telemetry.update();


        telemetry.clearAll();
        // STOP CODE
        telemetry.addData("Status", "Finished");
        telemetry.update();
    }
}
