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
    public void runOpMode() throws InterruptedException {
        telemetry.setAutoClear(true);

        telemetry.addData("Status", "Initializing");
        telemetry.update();

        Drivebase drivebase = new Drivebase(hardwareMap, this::opModeIsActive);
        VisionCamera camera = new VisionCamera(hardwareMap, FieldSide.BlueClose);

        telemetry.addData("Status", "Initialized");
        telemetry.update();
        waitForStart();

        camera.enableAprilTags();

        sleep(300);

        for (int i = 0; i < 19; i++) {
            sleep(100);
            AprilTagDetection detection = camera.getTagById(3);
            if (detection == null) {
                telemetry.addData("x", "");
                telemetry.addData("y", "");
            } else {
                telemetry.addData("x", detection.ftcPose.x);
                telemetry.addData("y", detection.ftcPose.y);
            }
            telemetry.update();
        }

        telemetry.update();

        drivebase.centerToAprilTag(10, () -> camera.getTagById(3));


        telemetry.addData("Status", "Running");
        telemetry.update();


        telemetry.clearAll();
        telemetry.addData("Status", "Finished");
        telemetry.update();
    }
}
