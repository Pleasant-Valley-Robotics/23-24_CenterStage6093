package org.firstinspires.ftc.teamcode.opmodes;


import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.hardware.VisionCamera;
import org.firstinspires.ftc.teamcode.utility.FieldSide;

@TeleOp(name = "CameraTest")
public class CameraTest extends LinearOpMode {
    @Override
    public void runOpMode() {
        // ALWAYS SET TELEMETRY AUTOCLEAR to TRUE!!!
        telemetry.setAutoClear(true);

        telemetry.addData("Status", "Initializing");
        telemetry.update();

        VisionCamera camera = new VisionCamera(hardwareMap, FieldSide.RedClose);
        waitForStart();

        camera.enableAprilTags();

        while (opModeIsActive()) {
            camera.addTelemetry(telemetry);
            telemetry.update();
        }

        telemetry.clearAll();

    }
}
