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

        drivebase.driveForward(22, 0.7, telemetry);
        drivebase.driveForward(2, 0.2, telemetry);
        camera.disableDetection();


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
                drivebase.relativeTurn(65, 0.4, telemetry);
                drivebase.driveForward(26, 0.3, telemetry);
                break;
            case Left:
                drivebase.relativeTurn(65, 0.4, telemetry);
                drivebase.driveForward(7.0, 0.4, telemetry);
                drivebase.driveForward(-7.0, 0.3, telemetry);
                drivebase.absoluteTurn(0, 0.4, telemetry);
                drivebase.driveForward(26, 0.3, telemetry);
                break;
            case Middle:
                drivebase.driveForward(15, 0.7, telemetry);
                drivebase.driveForward(5, 0.2, telemetry);
                drivebase.driveForward(2, 0.2, telemetry);
                //Was 0.2
                drivebase.absoluteTurn(180, 0.3, telemetry);
                //Was 6.
                drivebase.driveForward(6, 0.4, telemetry);
                drivebase.driveForward(-6, 0.4, telemetry);
                break;
        }
        // Score Pixel
        telemetry.update();

        drivebase.absoluteTurn(90, 0.3, telemetry);
        //Was 80
        drivebase.driveForward(46, 0.7, telemetry);
        telemetry.addData("Position", "1");

//        if (side == CubeSide.Left) sleep(5000);

        drivebase.driveSideways(-35, 0.4, telemetry);
        telemetry.addData("Position", "Strafed to backboard.");
        camera.enableAprilTags();
        telemetry.addData("Position", "Apriltags enabled.");

        telemetry.update();

        switch (side) {
            case Left:
                //Offset
                drivebase.driveSideways(-6, 0.3, telemetry);
                drivebase.centerToAprilTag(14, camera.getTagById(Config.APRILTAGS.ID_BLUE_LEFT));
                drivebase.driveSideways(-6, 0.3, telemetry);

                //centerToApriltag.
                break;
            case Middle:
                drivebase.driveSideways(-6, 0.3, telemetry);
                drivebase.centerToAprilTag(14, camera.getTagById(Config.APRILTAGS.ID_BLUE_LEFT));
                drivebase.driveSideways(1, 0.3, telemetry);

                //Center to Apriltag
                break;
            case Right:
                //Offset
                drivebase.driveSideways(7, 0.4, telemetry);
                telemetry.addData("Position", "Strafed to right tag.");
                drivebase.centerToAprilTag(14, camera.getTagById(Config.APRILTAGS.ID_BLUE_MIDDLE));
                drivebase.driveSideways(1, 0.4, telemetry);
                telemetry.addData("Position", "Centered to tag.");
                //Center to Apriltag
                break;
        }

        telemetry.update();

        //Was 6.
        drivebase.driveForward(6, 0.4, telemetry);
        drivebase.driveForward(3, 0.3, telemetry);

        dropper.dropPixel();
        telemetry.addData("Position", "Dropped pixel.");
        drivebase.driveForward(-4, 0.5, telemetry);

        telemetry.update();

        //Experimental
        if (side == CubeSide.Middle || side == CubeSide.Right)
        {
            drivebase.driveSideways(24, 0.3, telemetry);
        }
    }
}
