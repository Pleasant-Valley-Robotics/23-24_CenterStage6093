package org.firstinspires.ftc.teamcode.opmodes;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.hardware.Drivebase;
import org.firstinspires.ftc.teamcode.hardware.VisionCamera;
import org.firstinspires.ftc.teamcode.utility.CubeSide;
import org.firstinspires.ftc.teamcode.utility.FieldSide;

//Names the autonomous on the control hub dropdown.
@Autonomous(name = "pixelMarksAuto")
public class RedCloseAuto extends LinearOpMode {
    @Override
    //This method is needed for teleOp and auto to run at all.
    public void runOpMode() throws InterruptedException {
        //Runs after the robot has been initialized.
        //Assign vars here and create instances of needed classes.

        telemetry.setAutoClear(true);
        telemetry.addData("Status", "initializing");
        telemetry.update();


        Drivebase driveBase = new Drivebase(hardwareMap, this::opModeIsActive);
        VisionCamera visionCamera = new VisionCamera(hardwareMap, FieldSide.RedClose);

        waitForStart();

        //Note to self: Will had it as 10 earlier.
        CubeSide side = visionCamera.getStableCubePrediction(15);

        driveBase.driveForward(24.0, 0.3, telemetry);

        switch (side) {
            case Left:
                driveBase.relativeTurn(65.0, 0.5, telemetry);
            case Right:
                driveBase.relativeTurn(-65.0, 0.5, telemetry);
            case Middle:
                // we are already facing the right direction
        }

        // place pixel and go back to facing straight
        driveBase.driveForward(8.0, 0.3, telemetry);
        driveBase.driveForward(-8.0, 0.3, telemetry);
        driveBase.absoluteTurn(0.0, 0.5, telemetry);

        // drive back, turn, and strafe to face the backboard
        driveBase.driveForward(-20.0, 0.5, telemetry);
        driveBase.driveForward(-4.0, 0.2, telemetry);
        driveBase.driveForward(4.0, 0.2, telemetry);
        driveBase.absoluteTurn(-90.0, 0.5, telemetry);
        driveBase.driveForward(24.0, 0.5, telemetry);
        driveBase.driveSideways(-30.0, 0.5, telemetry);

        //Score the yellow pixel.
        //Store value of apriltag that robot's currently alligned with.
        switch ()
        {
            //If it's left, score with left apriltag.
            case Left:
            //Strafe left or right depending on what apriltag it's at right now.

            //If it's right, score with right apriltag.
            case Right:

            //If it's middle, score with middle apriltag.
            case Middle:

        }
    }
}
