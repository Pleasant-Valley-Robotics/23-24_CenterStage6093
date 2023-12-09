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
    public void runOpMode() {
        //Runs after the robot has been initialized.
        //Assign vars here and create instances of needed classes.

        telemetry.setAutoClear(true);
        telemetry.addData("Status", "initializing");
        telemetry.update();


        //Assign drivebase to a instance.
        Drivebase driveBase = new Drivebase(hardwareMap, this::opModeIsActive);
        //Assign the camera to a instance of the object.
        VisionCamera visionCamera = new VisionCamera(hardwareMap, FieldSide.RedClose);

        //Needed for both types of opModes.
        waitForStart();
        //Runs while the opmode is running.
        //This is needed to make a auto or teleOp run.
        CubeSide side;
        //while there's no side detected (there's no object in view) sleep.
        while ((side = visionCamera.getCubePrediction()) == null) {
            sleep(20);
        }

        //Calling driveToBackBoard will call the driveToSpike method first.
        //The CubeSide.Left is only for testing. Once done testing change it back to CubeSide.
        driveToBackBoard(driveBase, CubeSide.Left);

        //Runs after the stop button has been hit on the control hub.
    }

    /**
     * Turns robot to the correct angle and then drives to the center of the spike tape the white pixel's detected on.
     * That could be the left, middle, or right pixel depending on what the pipeline sees.
     */
    public void driveToSpike(Drivebase driveBase, CubeSide side) {
        //if side is left driveForward 5 inches, turn left to a certain angle, drive forward again a certain distance. Right does the same thing but with different measurements.
        //middle doesn't turn at all because it's a straight shot, it just drives a certain distance.
        switch (side) {
            //if the left side is seen by the pipeline
            case Left:
                //Include the driveForward 5 inches to drive a little bit away from the wall before turning so it doesn't get hit.
                //Include telemetry so we can make sure the heading and distance line up with expectations.
                driveBase.driveForward(5, 0.2, telemetry);
                //This is left so it's negative.
                driveBase.turnAngle(-10.0, 0.2, telemetry);
                driveBase.driveForward(20.5, 0.2, telemetry);
            case Right:
                driveBase.driveForward(5, 0, telemetry);
                driveBase.turnAngle(10.0, 0.2, telemetry);
                driveBase.driveForward(24.625, 0.2, telemetry);
            case Middle:
                driveBase.driveForward(26.875, 0.2, telemetry);
        }
    }

    /**
     * Drive robot to the backboard using a path dependent on where the robot already is and the direction it's already
     * facing then park facing the back glass of the feild.
     * @param driveBase
     * @param side
     */
    public void driveToBackBoard(Drivebase driveBase, CubeSide side)
    {
        //Call the driveToSpike method to drive to whatever spike the cube's detected on.
        driveToSpike(driveBase, side);
        //Drive backwards 10 inches to get away from pixel so it doesn't travel with the robot.
        driveBase.driveForward(-10, 0.5, null);
        //if side is...
        switch (side){
            //if the left side is seen by the pipeline then the robot starts facing the 5 degree offset that it was before.
            case Left:
                driveBase.turnAngle(80, 0.5, null);
                driveBase.driveForward(56, 0.5, null);

            //if the Right side is seen by the pipeline then the robot starts facing the 20 degree offset it was before.
            case Right:
                //Use turnAngle because that resets the current angle.
                driveBase.turnAngle(100, 0.5, null);
                driveBase.driveForward(35, 0.5, null);

            //if the middle is seen by the pipeline then the robot starts facing the 10 degree offset it was before.
            case Middle:
                driveBase.turnAngle(90, 0.5,null);
                driveBase.driveForward(46, 0.5, null);
        }
    }

    /**
     * Calls driveToBackboard method that calls driveToSpike.
     * Turns to a certain angle, drives and then stops, starting from where the robot is after going to the backboard.
     * @param driveBase
     * @param side
     */

    public void park(Drivebase driveBase, CubeSide side)
    {
        //Call the driveToBackBoard method that calls driveToSpike.
        driveToBackBoard(driveBase, side);

        switch (side)
        {
            case Left:
                //Back robot up 5 inches to not run into the board when turning.

                //Turn robot to where we want it to go and stay.

                //Drive to where we want it to stop.

                //Make the robot turn gain to be pointing staright at the back of the feild.

            case Right:
                //Same thing, with right side measurments.

            case Middle:
                //Same thing, with middle measurments.
        }
    }
}
