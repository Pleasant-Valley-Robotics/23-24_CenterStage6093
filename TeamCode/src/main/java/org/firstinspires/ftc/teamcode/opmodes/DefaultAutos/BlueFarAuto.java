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

        drivebase.driveForward(22, 0.7, telemetry);
        drivebase.driveForward(2, 0.2, telemetry);
        camera.disableDetection();

        switch (side) {
            case Left:
                drivebase.relativeTurn(65, 0.6, telemetry);

                drivebase.driveForward(6, 0.5, telemetry);
                drivebase.driveForward(-7, 0.5, telemetry);

                drivebase.absoluteTurn(0, 0.6, telemetry);

                drivebase.driveForward(26, 0.6, telemetry);
                break;
            case Middle:
                drivebase.driveForward(20, 0.7, telemetry);
                drivebase.driveForward(2, 0.2, telemetry);

                drivebase.absoluteTurn(180, 0.6, telemetry);

                drivebase.driveForward(6, 0.4, telemetry);
                drivebase.driveForward(-7, 0.4, telemetry);
                break;
            case Right:
                drivebase.relativeTurn(-65, 0.6, telemetry);

                drivebase.driveForward(6, 0.5, telemetry);
                drivebase.driveForward(-7, 0.5, telemetry);

                drivebase.absoluteTurn(0, 0.6, telemetry);

                drivebase.driveForward(26, 0.6, telemetry);
                break;
        }

        drivebase.absoluteTurn(90, 0.6, telemetry);
        drivebase.driveForward(48, 0.7, telemetry);

        drivebase.driveSideways(-18, 0.5, telemetry);
        drivebase.driveSideways(-17, 0.5, telemetry);
        camera.enableAprilTags();


        switch (side) {
            case Left:
                drivebase.driveSideways(-6, 0.6, telemetry);
                drivebase.centerToAprilTag(15, camera.getTagById(Config.APRILTAGS.ID_BLUE_LEFT));
                drivebase.driveSideways(-6, 0.6, telemetry);

                break;
            case Middle:
                drivebase.driveSideways(-6, 0.6, telemetry);
                drivebase.centerToAprilTag(15, camera.getTagById(Config.APRILTAGS.ID_BLUE_LEFT));
                drivebase.driveSideways(2, 0.6, telemetry);

                break;
            case Right:
                //Offset
                drivebase.centerToAprilTag(15, camera.getTagById(Config.APRILTAGS.ID_BLUE_MIDDLE));
                drivebase.driveSideways(2, 0.6, telemetry);
                break;
        }

        telemetry.update();

        drivebase.driveForward(6, 0.4, telemetry);
        drivebase.driveForward(2, 0.3, telemetry);

        dropper.dropPixel();
        drivebase.driveForward(-4, 0.5, telemetry);


        //Experimental
        if (side == CubeSide.Middle || side == CubeSide.Right) {
            drivebase.driveSideways(24, 0.3, telemetry);
        }
    }
}
