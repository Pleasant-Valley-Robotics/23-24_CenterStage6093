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
        drivebase.driveForward(20.0, 0.3, null);
        telemetry.update();
        switch (side) {
            case Right:
                //was 65 before
                drivebase.relativeTurn(-65, 0.6, null);
                break;
            case Left:
                //was -65 before.
                drivebase.relativeTurn(65, 0.6, null);
                break;
            case Middle:
                break;
        }
        // Score Pixel
        telemetry.update();
        drivebase.driveForward(5.0, 0.4, null);
        drivebase.driveForward(-5.0, 0.4, null);

    }
}
