package org.firstinspires.ftc.teamcode.opmodes.DefaultAutos;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.hardware.Drivebase;
import org.firstinspires.ftc.teamcode.hardware.PixelDropper;
import org.firstinspires.ftc.teamcode.hardware.VisionCamera;
import org.firstinspires.ftc.teamcode.utility.CubeSide;
import org.firstinspires.ftc.teamcode.utility.FieldSide;

@Autonomous(name = "BlueClose")
public class BlueCloseAuto extends LinearOpMode {
    @Override
    public void runOpMode() throws InterruptedException {
        telemetry.addData("Status", "Initializing");
        telemetry.update();

        PixelDropper pixelDropper = new PixelDropper(hardwareMap);
        Drivebase drivebase = new Drivebase(hardwareMap, this::opModeIsActive);
        VisionCamera camera = new VisionCamera(hardwareMap, FieldSide.BlueClose);
        // we enable and disable pipelines in order to save on processing power.
        camera.enableCubePipeline();

        telemetry.addData("Status", "Initialized");
        telemetry.update();
        waitForStart();

        // wait until we see 15 of the same side in a row, and then store that for later use.
        final CubeSide side = camera.getStableCubePrediction(15);

        camera.disableDetection();

        drivebase.driveForward(20.0, 0.3, telemetry);
        drivebase.driveForward(4.0, 0.2, telemetry);


        switch (side) {
            case Right:
                drivebase.relativeTurn(-65, 0.6, telemetry);
                break;
            case Left:
                drivebase.relativeTurn(65, 0.6, telemetry);
                break;
            case Middle:
                break;
            default:
                return;
        }


        drivebase.driveForward(6, 0.4, telemetry);
        drivebase.driveForward(-6, 0.4, telemetry);
        //Pixel HAS BEEN PLACED!!!

        drivebase.absoluteTurn(0,0.4,telemetry);
        drivebase.driveForward(-12, 0.4, telemetry);
        drivebase.relativeTurn(90, 0.5, telemetry);
        drivebase.driveForward(24, 0.4, telemetry);
        drivebase.relativeTurn(-90, 0.4, telemetry);
        drivebase.driveForward(56, 0.4, telemetry);
        //Gotten Through truss

        drivebase.driveSideways(38, 0.4, telemetry);
        // Infront of DropBase
        camera.enableAprilTags();
        switch (side) {
            case Right:
                drivebase.driveSideways(2, 0.4, telemetry);
                break;
            case Middle:

                break;
            case Left:

                break;
            default:
                return;
        }


    }
}
