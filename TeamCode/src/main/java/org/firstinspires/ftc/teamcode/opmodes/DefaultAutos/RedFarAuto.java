package org.firstinspires.ftc.teamcode.opmodes.DefaultAutos;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.hardware.Drivebase;
import org.firstinspires.ftc.teamcode.hardware.PixelDropper;
import org.firstinspires.ftc.teamcode.hardware.VisionCamera;
import org.firstinspires.ftc.teamcode.utility.CubeSide;
import org.firstinspires.ftc.teamcode.utility.FieldSide;

@Autonomous
public class RedFarAuto extends LinearOpMode {

    public void runOpMode() throws InterruptedException {
        telemetry.addData("Status", "Initializing");
        telemetry.update();
        Drivebase drivebase = new Drivebase(hardwareMap, this::opModeIsActive);
        PixelDropper dropper = new PixelDropper(hardwareMap);
        VisionCamera camera = new VisionCamera(hardwareMap, FieldSide.RedFar);
        telemetry.addData("Status", "Initialized");
        telemetry.update();
        waitForStart();
        CubeSide side = camera.getStableCubePrediction(15);

        drivebase.driveForward(6.0, 0.2, null);
        drivebase.driveForward(20.0, 0.3, null);
        switch (side) {
            case Right:
                drivebase.relativeTurn(-65, 0.6, null);
                break;
            case Left:
                drivebase.relativeTurn(65, 0.6, null);
                break;
            case Middle:
                break;
        }
        // Score Pixel
        drivebase.driveForward(5.0, 0.4, null);
        drivebase.driveForward(-5.0, 0.4, null);

        // Get Out of Spike Mark Area
        drivebase.absoluteTurn(0, 0.5, null);
        drivebase.driveForward(-18, 0.4, null);
        drivebase.absoluteTurn(-90, 0.5, null);
        drivebase.driveForward(20, 0.5, null);

        // Go Around Spike Mark Area
        drivebase.relativeTurn(90, 0.5, null);
        drivebase.driveForward(50, 0.5, null);
        drivebase.absoluteTurn(90, 0.5, null);

        //Go through Truss and Get to Backdrop
        drivebase.driveForward(92, 0.4, null);
        drivebase.driveSideways(72, 0.5, null);


        switch(side) {
            case Right:
                    drivebase.driveSideways(2,0.5,null);
                    drivebase.centerToAprilTag(10, camera.getTagById(1));

                break;
            case Left:

                drivebase.centerToAprilTag(10, camera.getTagById(1));
                break;
            case Middle:
                drivebase.centerToAprilTag(10, camera.getTagById(1));
                break;
        }

        drivebase.driveForward(5, 0.5, null);
        dropper.dropPixel();
        drivebase.driveForward(-3,0.4, null);
    }
}
