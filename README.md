# CenterStage 6093 code

## TeleOp

TeleOp programming is generally rather simple. This is an example of a teleop:

```java

@TeleOp(name = "Example Teleop")
public class ExampleTeleOp extends LinearOpMode {
    @Override
    public void runOpMode() {
        telemetry.setAutoClear(false);

        telemetry.addData("Status", "Initializing");
        telemetry.update();

        Drivebase drivebase = new Drivebase(hardwareMap, null);

        telemetry.addData("Status", "Initialized");
        telemetry.update();

        waitForStart();

        while (opModeIsActive()) {
            double yInput = -gamepad1.left_stick_y;
            double xInput = gamepad1.left_stick_x;
            double turnInput = gamepad1.right_stick_x;

            drivebase.mecanumDrive(yInput, xInput, turnInput);
            drivebase.addTelemetry(telemetry);

            telemetry.addData("Status", "Running");
            telemetry.update();
        }

        telemetry.clearAll();

        telemetry.addData("Status", "Finished");
        telemetry.update();
    }
}  
```  

Going through this, there are some important parts:

* `@TeleOp(name = "Example TeleOp")`  

  The `name` property here is what shows up on the drive station when the driver is selecting a
  teleop to run.

* `Drivebase drivebase = new Drivebase(hardwareMap, null);`  

  We use the `Drivebase` class to handle initialization of the motors and driving, which reduces
  code duplication.

* `double yInput = -gamepad1.left_stick_y;`

  The y input is inverted because in ftc controllers have their y axis inverted, so we are undoing
  that here.

* `waitForStart();`

  Without this, the robot would start immediately when the driver presses init, instead of when they
  press play.

## Autonomous

Autos are harder to program, but unless you are changing how the robot drives, they only require
careful measurement. Here is an example of an auto:

```java

@Autonomous(name = "Example Auto")
public class ExampleAuto extends LinearOpMode {
    @Override
    public void runOpMode() {
        telemetry.setAutoClear(false);

        telemetry.addData("Status", "Initializing");
        telemetry.update();

        Drivebase drivebase = new Drivebase(hardwareMap, this::opModeIsActive);

        telemetry.addData("Status", "Initialized");
        telemetry.update();

        waitForStart();

        telemetry.addData("Status", "Running");
        telemetry.update();

        drivebase.driveForward(10.0, 1.0, telemetry);
        drivebase.driveSideways(10.0, 1.0, telemetry);
        drivebase.turnAngle(90.0, 1.0, telemetry);

        telemetry.clearAll();

        telemetry.addData("Status", "Finished");
        telemetry.update();
    }
}
```

Most of this should be fairly intuitive:

* `telemetry.setAutoClear(false);`  

  This prevents unused keys from getting deleted when we call `telemetry.update()`, otherwise our
  status messages would get wiped by `drivebase` updating telemetry.

* `Drivebase drivebase = new Drivebase(hardwareMap, this::opModeIsActive);`

  The reason we pass `this::opModeIsActive` is so that it knows to stop when the
  driver presses the stop button.

* `drivebase.driveForward(10.0, 1.0, telemetry)`

  This tells the drivebase to drive forward 10 inches, at full power, while updating telemetry. For
  the other auto driving methods, refer to the documentation on how to use them.
