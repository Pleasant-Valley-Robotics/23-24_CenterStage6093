package org.firstinspires.ftc.teamcode.opmodes.DefaultAutos;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.hardware.Drivebase;
import org.firstinspires.ftc.teamcode.hardware.PixelDropper;
import org.firstinspires.ftc.teamcode.hardware.VisionCamera;
import org.firstinspires.ftc.teamcode.utility.CubeSide;
import org.firstinspires.ftc.teamcode.utility.FieldSide;

@Autonomous(name = "BlueFarDefault")
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

        if(side == CubeSide.Left) {
            drivebase.relativeTurn(65, 0.4, telemetry);
            drivebase.driveForward(7.0, 0.3, telemetry);
            drivebase.driveForward(-7.0, 0.3, telemetry);
            drivebase.absoluteTurn(0, 0.4, telemetry);
        }

        switch (side) {
            case Right:
                drivebase.driveForward(28, 0.4, telemetry);
                drivebase.absoluteTurn(150, 0.2, telemetry);
                drivebase.driveForward(8,0.4, telemetry);
                drivebase.driveForward(-10, 0.4, telemetry);
                drivebase.relativeTurn(30, 0.4, telemetry);
                break;
            case Left:
                //Was 23 before.
                drivebase.driveForward(26, 0.3, telemetry);
                drivebase.absoluteTurn(180, 0.3, telemetry);
                break;
            case Middle:
                drivebase.driveForward(20, 0.25, telemetry);
                drivebase.driveForward(4, 0.3, telemetry);
                drivebase.absoluteTurn(180, 0.2, telemetry);
                drivebase.driveForward(4,0.4, telemetry);
                drivebase.driveForward(-8, 0.4, telemetry);
                break;
        }
        // Score Pixel
        telemetry.update();

        drivebase.absoluteTurn(90, 0.4, telemetry);
        drivebase.driveForward(80, 0.5, telemetry);
        drivebase.driveSideways(-36, 0.4, telemetry);

        switch(side) {
            case Left:
                //Offset
                drivebase.driveSideways(-8, 0.3, telemetry);
                //Center To Apriltag
                break;
            case Right:
                //Offset
                drivebase.driveSideways(6, 0.3, telemetry);
                //Center to Apriltag
                break;
            case Middle:
                //Was -6 before.
                drivebase.driveSideways(-5, 0.3, telemetry);

                //Center to Apriltag
                break;
        }

        drivebase.driveForward(7, 0.3, telemetry);

        dropper.dropPixel();
        drivebase.driveForward(-2, 0.5, telemetry);githu
    }
}
