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

        if(side == CubeSide.Left) {
            drivebase.relativeTurn(65, 0.4, telemetry);
            drivebase.driveForward(6.0, 0.3, telemetry);
            drivebase.driveForward(-6.0, 0.3, telemetry);
            drivebase.absoluteTurn(0, 0.4, telemetry);
        }

        switch (side) {
            case Right:
                //was 65 before

                break;
            case Left:
                drivebase.driveForward(13, 0.3, telemetry);
                drivebase.absoluteTurn(180, 0.3, telemetry);
                break;
            case Middle:
                drivebase.driveForward(29, 0.4, telemetry);
                drivebase.absoluteTurn(180, 0.2, telemetry);
                drivebase.driveForward(5,0.4, telemetry);
                drivebase.driveForward(-5, 0.4, telemetry);
                break;
        }
        // Score Pixel
        telemetry.update();
    }
}
