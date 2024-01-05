package org.firstinspires.ftc.teamcode.opmodes;


import android.media.SoundPool;

import com.qualcomm.ftccommon.SoundPlayer;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.robotcore.external.android.AndroidSoundPool;
import org.firstinspires.ftc.robotcore.internal.android.SoundPoolIntf;
import org.firstinspires.ftc.teamcode.hardware.Drivebase;
import org.firstinspires.ftc.teamcode.hardware.DroneLauncher;
import org.firstinspires.ftc.teamcode.hardware.PixelDropper;
import org.firstinspires.ftc.teamcode.hardware.Suspension;

@TeleOp(name = "TestingTeleOp")
public class TestingTeleOp extends LinearOpMode {
    @Override
    public void runOpMode() {
        // ALWAYS SET TELEMETRY AUTOCLEAR to TRUE!!!
        // otherwise, it overflows the log.
        telemetry.setAutoClear(true);

        telemetry.addData("Status", "Initializing");
        telemetry.update();

        Drivebase drivebase = new Drivebase(hardwareMap, this::opModeIsActive);
        DroneLauncher launcher = new DroneLauncher(hardwareMap, telemetry);
        Suspension suspension = new Suspension(hardwareMap);
        AndroidSoundPool soundPool = new AndroidSoundPool();
        soundPool.initialize(SoundPlayer.getInstance());
        soundPool.setVolume(1f);

        telemetry.addData("Status", "Initialized");
        telemetry.update();

        drivebase.clearEncoder(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        waitForStart();

        soundPool.play("Carefree(chosic.com).mp3");

        while (opModeIsActive()) {
            if (gamepad1.right_bumper) launcher.LaunchDathOe();
            else launcher.UnLaunchDathOe();

            double yInput = -gamepad1.left_stick_y;
            double xInput = gamepad1.left_stick_x;
            double turnInput = gamepad1.right_stick_x;

            drivebase.mecanumDrive(yInput, xInput, turnInput);
            drivebase.addTelemetry(telemetry);

            telemetry.addData("Status", "Running");
            telemetry.update();

            suspension.spoolDaSpool(gamepad2, telemetry);
        }

        telemetry.clearAll();

        telemetry.addData("Status", "Finished");
        telemetry.update();
    }
}
