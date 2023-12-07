package org.firstinspires.ftc.teamcode.opmodes;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.hardware.Drivebase;
import org.firstinspires.ftc.teamcode.hardware.VisionCamera;
import org.firstinspires.ftc.teamcode.utility.CubeSide;
import org.firstinspires.ftc.teamcode.utility.FieldSide;
import org.jetbrains.annotations.NotNull;

//Names the autonomous on the control hub dropdown.
@Autonomous(name = "pixelMarksAuto")
public class PixelMarksAuto extends LinearOpMode {
    @Override
    //This method is needed for teleOp and auto to run at all.
    public void runOpMode() {
        //Runs after the robot has been initialized.
        //Assign vars here and create instances of needed classes.

        telemetry.setAutoClear(false);
        telemetry.addData("Status", "initializing");
        telemetry.update();


        //Assign drivebase to a instance.
        Drivebase driveBase = new Drivebase(hardwareMap, this::opModeIsActive);
        VisionCamera visionCamera = new VisionCamera(hardwareMap, FieldSide.RedClose);

        //Needed for both types of opModes.
        waitForStart();
        //Runs while the opmode is running.
        //This is needed to make a auto or teleOp run.
        CubeSide side;
        while ((side = visionCamera.getCubePrediction()) == null) sleep(20);

        driveToSpike(driveBase, side);

        //Runs after the stop button has been hit on the control hub.
    }

    /**
     * Turns robot to the correct angle and then drives to the center of the spike tape the white pixel's detected on.
     * That could be the left, middle, or right pixel depending on what the pipeline sees.
     */
    public void driveToSpike(Drivebase driveBase, CubeSide side) {
        //if side is...
        switch (side) {
            //if the left side is seen by the pipeline
            case Left:
                driveBase.turnAngle(5.0, 0.5, telemetry);
                driveBase.driveForward(31.5, 0.5, telemetry);
            case Right:
                driveBase.turnAngle(20.0, 0.5, telemetry);
                driveBase.driveForward(29.625, 0.5, telemetry);
            case Middle:
                driveBase.turnAngle(10.0, 0.5, telemetry);
                driveBase.driveForward(26.875, 0.5, telemetry);
        }
    }
}
