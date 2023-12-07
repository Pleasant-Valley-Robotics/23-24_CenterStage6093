package org.firstinspires.ftc.teamcode.opmodes;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.hardware.Drivebase;

//Names the autonomous on the control hub dropdown.
@Autonomous(name = "pixelMarksAuto")
public class pixelMarksAuto extends LinearOpMode {
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

        //Needed for both types of opModes.
        waitForStart();
        //Runs while the opmode is running.
        //This is needed to make a auto or teleOp run.
        while (opModeIsActive()) {

            //Look for a non-nul instance of a pixel pipeline object.
            //If there's something detected call the driveToSpike method.
            

        }
        //Runs after the stop button has been hit on the control hub.
    }

    /**
     *  Turns robot to the correct angle and then drives to the center of the spike tape the white pixel's detected on.
     *  That could be the left, middle, or right pixel depending on what the pipeline sees.
     */
    public void driveToSpike(Drivebase driveBase)
    {
        //If the white pixel has been deteced to be on the middle of the left spike tape...
        driveBase.turnAngle(10.0, 0.5, telemetry);
        driveBase.driveForward(26.875, 0.5, telemetry);

        //If the white pixel has been detected to be on the middle of the middle spike tape...
        driveBase.turnAngle(5.0, 0.5, telemetry);
        driveBase.driveForward(31.5, 0.5, telemetry);

        //If the white pixel has been detected to be on the middle of the right spike tape...
        driveBase.turnAngle(20.0, 0.5, telemetry);
        driveBase.driveForward(29.625, 0.5, telemetry);
    }
}
