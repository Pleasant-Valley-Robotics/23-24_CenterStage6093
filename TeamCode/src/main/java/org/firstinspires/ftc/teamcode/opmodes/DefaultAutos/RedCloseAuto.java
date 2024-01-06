package org.firstinspires.ftc.teamcode.opmodes.DefaultAutos;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.hardware.Drivebase;
import org.firstinspires.ftc.teamcode.hardware.PixelDropper;
import org.firstinspires.ftc.teamcode.hardware.VisionCamera;
import org.firstinspires.ftc.teamcode.utility.Config;
import org.firstinspires.ftc.teamcode.utility.CubeSide;
import org.firstinspires.ftc.teamcode.utility.FieldSide;

@Autonomous(name = "RedClose")
public class RedCloseAuto extends LinearOpMode {
    @Override
    public void runOpMode() throws InterruptedException {
        telemetry.addData("Status", "Initializing");
        telemetry.update();

        PixelDropper pixelDropper = new PixelDropper(hardwareMap);
        Drivebase drivebase = new Drivebase(hardwareMap, this::opModeIsActive);
        VisionCamera camera = new VisionCamera(hardwareMap, FieldSide.RedClose);
        // we enable and disable pipelines in order to save on processing power.
        camera.enableCubePipeline();

        telemetry.addData("Status", "Initialized");
        telemetry.update();
        waitForStart();

        // wait until we see 15 of the same side in a row, and then store that for later use.
        final CubeSide side = camera.getStableCubePrediction(15);

        camera.disableDetection();

        drivebase.driveForward(24.0, 0.3, telemetry);

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
        drivebase.driveForward(-7, 0.4, telemetry);

        drivebase.absoluteTurn(0, 0.5, telemetry);
        drivebase.driveForward(-12, 0.5, telemetry);
        drivebase.relativeTurn(-90, 0.5, telemetry);

        drivebase.driveForward(30, 0.4, telemetry);

        drivebase.absoluteTurn(-90, 0.7, telemetry);

        drivebase.driveSideways(-19, 0.3, telemetry);
        drivebase.absoluteTurn(-90, 0.7, telemetry);

        camera.enableAprilTags();

        switch (side) {
            case Left:
                drivebase.driveSideways(-10, 0.3, telemetry);
                drivebase.centerToAprilTag(15, camera.getTagById(Config.APRILTAGS.ID_RED_LEFT), telemetry);
                drivebase.driveSideways(-7, 0.3, telemetry);
                drivebase.driveForward(6, 0.3, telemetry);
                drivebase.driveForward(2, 0.2, telemetry);
                break;
            case Middle:
                drivebase.driveSideways(-10, 0.3, telemetry);
                drivebase.centerToAprilTag(15, camera.getTagById(Config.APRILTAGS.ID_RED_LEFT), telemetry);
                drivebase.driveForward(6, 0.3, telemetry);
                drivebase.driveForward(2, 0.2, telemetry);
                break;
            case Right:
                drivebase.centerToAprilTag(15, camera.getTagById(Config.APRILTAGS.ID_RED_MIDDLE), telemetry);
                drivebase.driveForward(6, 0.3, telemetry);
                drivebase.driveForward(2, 0.2, telemetry);
                break;
        }

        pixelDropper.dropPixel();
        drivebase.driveForward(-2.5, 0.5, null);

        telemetry.addData("Status", "Finished");

    }
}
