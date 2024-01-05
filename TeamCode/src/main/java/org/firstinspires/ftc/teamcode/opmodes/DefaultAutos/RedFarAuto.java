package org.firstinspires.ftc.teamcode.opmodes.DefaultAutos;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.hardware.Drivebase;
import org.firstinspires.ftc.teamcode.hardware.PixelDropper;
import org.firstinspires.ftc.teamcode.hardware.VisionCamera;
import org.firstinspires.ftc.teamcode.utility.CubeSide;
import org.firstinspires.ftc.teamcode.utility.FieldSide;

@Autonomous(name = "RedFarAuto")
public class RedFarAuto extends LinearOpMode {

    public void runOpMode() throws InterruptedException {
        telemetry.addData("Status", "Initializing");
        telemetry.update();

        Drivebase drivebase = new Drivebase(hardwareMap, this::opModeIsActive);
        PixelDropper dropper = new PixelDropper(hardwareMap);
        VisionCamera camera = new VisionCamera(hardwareMap, FieldSide.RedFar);

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

        if(side == CubeSide.Left) {
            drivebase.relativeTurn(65, 0.4, telemetry);
            drivebase.driveForward(7.0, 0.3, telemetry);
            drivebase.driveForward(-7.0, 0.3, telemetry);
            drivebase.absoluteTurn(0, 0.4, telemetry);
        }

        switch (side) {
            case Right:
                drivebase.driveForward(20, 0.4, telemetry);
                drivebase.driveForward(3, 0.2, telemetry);
                //Was -140.
                drivebase.absoluteTurn(140, 0.2, telemetry);
                drivebase.driveForward(11,0.4, telemetry);
                drivebase.driveForward(-14, 0.4, telemetry);
                //Was -40.
                drivebase.relativeTurn(40, 0.4, telemetry);
                break;
            case Left:
                drivebase.driveForward(26, 0.3, telemetry);
                drivebase.absoluteTurn(180, 0.3, telemetry);
                break;
            case Middle:
                drivebase.driveForward(20, 0.25, telemetry);
                drivebase.driveForward(4, 0.3, telemetry);
                //Was 180.
                drivebase.absoluteTurn(-180, 0.2, telemetry);
                drivebase.driveForward(4,0.4, telemetry);
                drivebase.driveForward(-8, 0.4, telemetry);
                break;
        }
        // Score Pixel
        telemetry.update();

        drivebase.absoluteTurn(-90, 0.4, telemetry);
        drivebase.driveForward(80, 0.5, telemetry);
        drivebase.driveSideways(36, 0.3, telemetry);

        switch(side) {
            case Left:
                //Offset
                drivebase.driveSideways(-8, 0.3, telemetry);
                //Center To Apriltag
                break;
            case Right:
                //Offset
                drivebase.driveSideways(5, 0.3, telemetry);
                //Center to Apriltag
                break;
            case Middle:
                //Was -5.
                drivebase.driveSideways(-24, 0.3, telemetry);

                //Center to Apriltag
                drivebase.centerToAprilTag(2.0, camera.getTagById(2));
                break;
        }

        drivebase.driveForward(7, 0.3, telemetry);

        dropper.dropPixel();
        drivebase.driveForward(-2, 0.5, telemetry);
    }
}
