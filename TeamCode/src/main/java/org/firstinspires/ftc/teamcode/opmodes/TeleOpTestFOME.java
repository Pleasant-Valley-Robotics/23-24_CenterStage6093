package org.firstinspires.ftc.teamcode.opmodes;


import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.Servo;

@TeleOp(name = "Jaren's TestTele")
public class TeleOpTestFOME extends LinearOpMode {
    @Override
    public void runOpMode() {
        Servo servo;

        telemetry.addData("Status", "Initialized");
        telemetry.update();

        servo = hardwareMap.get(Servo.class, "Servo");

        waitForStart();
        while (opModeIsActive()) {
            // FTC controllers have inverted joystick y values

            servo.setPosition(gamepad1.right_stick_y);

            double servopos = servo.getPosition();
            telemetry.addData("ServoPos", servopos);


            telemetry.addData("Status", "Running");
            telemetry.update();
        }

        telemetry.clearAll();

        telemetry.addData("Status", "Finished");
        telemetry.update();
    }
}
