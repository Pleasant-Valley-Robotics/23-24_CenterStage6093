//import everything in the defaultAutos folder/make it avaliable for this file to use.
package org.firstinspires.ftc.teamcode.opmodes.DefaultAutos;

//Import the needed opmodes for the file to run/use.
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

//Import needed files to drive so their variables, methods, etc can be used in this file.
//Import driving methods and variables needed.
import org.firstinspires.ftc.teamcode.hardware.Drivebase;
//import PixelDropper file and it's contents (variables and methods) to use in this file.
import org.firstinspires.ftc.teamcode.hardware.PixelDropper;
//import VisionCamera file and its contents (variables and methods) to use the mounted camera in this file.
import org.firstinspires.ftc.teamcode.hardware.VisionCamera;
//import CubeSide file and its contents to tell what side of the cube is detected and use that info in this file.
import org.firstinspires.ftc.teamcode.utility.CubeSide;
//import the FeildSide file to use the info of what side of the feild the robot's on in this file.
import org.firstinspires.ftc.teamcode.utility.FieldSide;

//Name what the autonomous will show up as in driver hub.
@Autonomous(name = "RedCloseDefault")
//Declare access, what it is (a class) and its parents class that it inherits methods and variables directly from.
public class RedCloseAuto extends LinearOpMode {
    @Override
    // Declare a method so there's something to run. If there's a exception throw it in the driver hub and stop the program.
    public void runOpMode() throws InterruptedException {
        telemetry.addData("Status", "Initializing");
        telemetry.update();

        //Create a variable and relate it to the physical pixel scoring mechanism.
        PixelDropper pixelDropper = new PixelDropper(hardwareMap);
        //Create a variable and relate it to the drivebase in code.
        Drivebase drivebase = new Drivebase(hardwareMap, this::opModeIsActive);
        //Create a variable and relate it to the physical camera.
        VisionCamera camera = new VisionCamera(hardwareMap, FieldSide.RedClose);
        //Turn on the pipeline so it starts scanning for cubes in front of it.
        camera.enableCubePipeline();

        telemetry.addData("Status", "Initialized");
        telemetry.update();
        waitForStart();

        //Store what side the pipeline is detecting the cube on in this variable.
        CubeSide side = camera.getStableCubePrediction(15);
        //Turn the cameras ability to detect the cube off.
        camera.disableDetection();
        //Drive the robot forward 26 inches in total.
        drivebase.driveForward(20.0, 0.3, telemetry);
        drivebase.driveForward(6.0, 0.2, telemetry);

        switch (side) {
            //If the cube is detected on the right side turn left 65 degrees at 60% max speed then exit this switch
            //case structure.
            case Right:
                drivebase.relativeTurn(-65, 0.6, telemetry);
                break;
            //If the cube is found on the left side turn 65 degrees at 60% max speed then exit this switch case structure.
            case Left:
                drivebase.relativeTurn(65, 0.6, telemetry);
                break;
            case Middle:
                break;
        }

        drivebase.driveForward(6, 0.4, telemetry);
        drivebase.driveForward(-6, 0.4, telemetry);

        drivebase.absoluteTurn(0, 0.5, telemetry);
        drivebase.driveForward(-12, 0.5, telemetry);
        drivebase.relativeTurn(-90, 0.5, telemetry);

        drivebase.driveForward(30, 0.4, telemetry);
        drivebase.driveSideways(-20, 0.3, telemetry);
        camera.enableAprilTags();

        switch (side) {
            case Middle:
                drivebase.centerToAprilTag(10, camera.getTagById(5));
                break;
            case Left:
                //Swap Left and Right tag id's if using another field, 4 is left normally, 6 right
                drivebase.centerToAprilTag(10, camera.getTagById(6));
                drivebase.driveSideways(-8, 0.3, telemetry);
                break;
            case Right:
                //Swap Left and Right tag id's if using another field, 4 is left normally, 6 right
                drivebase.centerToAprilTag(10, camera.getTagById(4));
                drivebase.driveSideways(1, 0.3, telemetry);
                break;
        }

        drivebase.driveForward(7, 0.5, telemetry);
        pixelDropper.dropPixel();
        drivebase.driveForward(-2.5, 0.5, null);

        telemetry.addData("Status", "Finished");

    }
}
