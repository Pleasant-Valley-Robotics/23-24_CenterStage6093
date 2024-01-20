package org.firstinspires.ftc.teamcode.opmodes;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.hardware.Drivebase;
import org.firstinspires.ftc.teamcode.hardware.VisionCamera;
import org.firstinspires.ftc.teamcode.utility.FieldSide;
import org.firstinspires.ftc.vision.apriltag.AprilTagDetection;

@Autonomous(name = "TestingAuto")
public class TestingAuto extends LinearOpMode {
    @Override
    public void runOpMode() {
        telemetry.setAutoClear(true);

        telemetry.addData("Status", "Initializing");
        telemetry.update();

        Drivebase drivebase = new Drivebase(hardwareMap, this::opModeIsActive);
        VisionCamera camera = new VisionCamera(hardwareMap, FieldSide.BlueClose);

        camera.enableCubePipeline();
        camera.enableAprilTags();

        telemetry.addData("Status", "Initialized");
        telemetry.update();
        waitForStart();

        drivebase.centerToAprilTag(14.0, camera.getTagById(1));

        telemetry.clearAll();
        telemetry.addData("Status", "Finished");
        telemetry.update();
    }
}
