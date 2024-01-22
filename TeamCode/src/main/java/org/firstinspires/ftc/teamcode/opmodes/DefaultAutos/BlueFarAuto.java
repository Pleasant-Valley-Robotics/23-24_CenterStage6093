package org.firstinspires.ftc.teamcode.opmodes.DefaultAutos;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.hardware.Drivebase;
import org.firstinspires.ftc.teamcode.hardware.PixelDropper;
import org.firstinspires.ftc.teamcode.hardware.VisionCamera;
import org.firstinspires.ftc.teamcode.utility.Config;
import org.firstinspires.ftc.teamcode.utility.CubeSide;
import org.firstinspires.ftc.teamcode.utility.FieldSide;

@Autonomous(name = "BlueFar")
public class BlueFarAuto extends LinearOpMode {

    public void runOpMode() throws InterruptedException {
        telemetry.addData("Status", "Initializing");
        telemetry.update();

        Drivebase drivebase = new Drivebase(hardwareMap, this::opModeIsActive);
        PixelDropper dropper = new PixelDropper(hardwareMap);
        VisionCamera camera = new VisionCamera(hardwareMap, FieldSide.BlueFar);

        telemetry.addData("Status", "Initialized");
        telemetry.update();
        camera.enableCubePipeline();

        waitForStart();

        CubeSide side = camera.getStableCubePrediction(15);
        telemetry.addData("Side", side);
        telemetry.update();
        telemetry.update();

        drivebase.driveForward(24, 0.3, telemetry);
        camera.disableDetection();

        if (side == CubeSide.Left) {
            drivebase.relativeTurn(65, 0.4, telemetry);
            drivebase.driveForward(7.0, 0.3, telemetry);
            drivebase.driveForward(-7.0, 0.3, telemetry);
            drivebase.absoluteTurn(0, 0.4, telemetry);
        }

        switch (side) {
            case Right:
                //Code that we know works though it's not the most practical.
//                drivebase.driveForward(20, 0.4, telemetry);
//                drivebase.driveForward(3, 0.2, telemetry);
//                drivebase.absoluteTurn(-140, 0.2, telemetry);
//                drivebase.driveForward(11, 0.4, telemetry);
//                drivebase.driveForward(-16, 0.4, telemetry);
//                drivebase.relativeTurn(-40, 0.4, telemetry);

                //New code that might not work. Meant to be a mirror of left side. (Left side works).
                drivebase.relativeTurn(-65, 0.4, telemetry);
                drivebase.driveForward(7.0, 0.3, telemetry);
                drivebase.driveForward(-7.0, 0.3, telemetry);
                drivebase.absoluteTurn(0, 0.4, telemetry);
                drivebase.driveForward(26, 0.3, telemetry);
                break;
            case Left:
                drivebase.driveForward(26, 0.3, telemetry);
                break;
            case Middle:
                drivebase.driveForward(20, 0.25, telemetry);
                drivebase.driveForward(4, 0.3, telemetry);
                drivebase.absoluteTurn(180, 0.2, telemetry);
                drivebase.driveForward(4, 0.4, telemetry);
                drivebase.driveForward(-8, 0.4, telemetry);
                break;
        }
        // Score Pixel
        telemetry.update();

        drivebase.absoluteTurn(90, 0.4, telemetry);
        drivebase.driveForward(80, 0.5, telemetry);
        sleep(4000); //1000 is a second.
        drivebase.driveSideways(-30, 0.4, telemetry);

        camera.enableAprilTags();

        switch (side) {
            case Left:
                //Offset
                drivebase.centerToAprilTag(14.0, camera.getTagById(Config.APRILTAGS.ID_BLUE_LEFT));
                drivebase.driveSideways(-7, 0.3, telemetry);
                //centerToApriltag.
                break;
            case Middle:
                drivebase.centerToAprilTag(14.0, camera.getTagById(Config.APRILTAGS.ID_BLUE_LEFT));

                //Center to Apriltag
                break;
            case Right:
                //Offset
                drivebase.driveSideways(5, 0.3, telemetry);
                drivebase.centerToAprilTag(14.0, camera.getTagById(Config.APRILTAGS.ID_BLUE_MIDDLE));
                //Center to Apriltag
                break;
        }

        drivebase.driveForward(8, 0.3, telemetry);

        dropper.dropPixel();
        drivebase.driveForward(-2, 0.5, telemetry);

        //Experimental
        //drivebase.driveForward(-4, 0.5, telemetry);
        //drivebase.driveSideways(46, 0.3, telemetry);
    }
}
