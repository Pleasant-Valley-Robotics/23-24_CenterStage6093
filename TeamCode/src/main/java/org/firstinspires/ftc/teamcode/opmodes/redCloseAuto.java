package org.firstinspires.ftc.teamcode.opmodes;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.hardware.Drivebase;
import org.firstinspires.ftc.teamcode.hardware.VisionCamera;
import org.firstinspires.ftc.teamcode.utility.CubeSide;
import org.firstinspires.ftc.teamcode.utility.FieldSide;

//Names the autonomous on the control hub dropdown.
@Autonomous(name = "pixelMarksAuto")
public class redCloseAuto extends LinearOpMode {
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
        //Assign the camera to a instance of the object.
        VisionCamera visionCamera = new VisionCamera(hardwareMap, FieldSide.RedClose);

        //Needed for both types of opModes.
        waitForStart();
        //Runs while the opmode is running.
        //This is needed to make a auto or teleOp run.
        CubeSide side;
        while ((side = visionCamera.getCubePrediction()) == null) sleep(20);

        //The CubeSide.Left is only for testing. Once done testing change it back to CubeSide.
        driveToSpike(driveBase, CubeSide.Left);

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
                driveBase.turnAngle(5.0, 0.5, null);
                driveBase.driveForward(31.5, 0.5, null);
            case Right:
                driveBase.turnAngle(20.0, 0.5, null);
                driveBase.driveForward(29.625, 0.5, null);
            case Middle:
                driveBase.turnAngle(10.0, 0.5, null);
                driveBase.driveForward(26.875, 0.5, null);
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
        //if side is...
        switch (side){
            //if the left side is seen by the pipeline then the robot starts facing the 5 degree offset that it was before so...
            case Left:
            driveBase.turnAngle(85, 0.5, null);
            driveBase.driveForward(57, 0.5, null);

            //if the Right side is seen by the pipeline then the robot starts facing the 20 degree offset it was before so...
            case Right:
            driveBase.turnAngle(95, 0.5, null);
            driveBase.driveForward(37, 0.5, null);
            //Do the same as commented above.


            //if the middle is seen by the pipeline then the robot starts facing the 10 degree offset it was before so...
            case Middle:
            driveBase.turnAngle(95, 0.5,null);
            driveBase.driveForward(47, 0.5, null);
            //Do the same as commented above.

        }
    }

    public void park(Drivebase driveBase, CubeSide side)
    {
        switch (side)
        {
            case Left:
                //Turn robot to face the way it's going to drive.

                //Drive robot a little bit out of the way of the backboard.

                //Make the robot turn gain to be pointing staright at the back of the feild.

            case Right:
                //Same thing, with right side measurments.

            case Middle:
                //Same thing, with middle measurments.
        }

    }
}
