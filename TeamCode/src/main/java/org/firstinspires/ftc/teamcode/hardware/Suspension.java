package org.firstinspires.ftc.teamcode.hardware;

import androidx.annotation.Nullable;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.utility.Api;

public class Suspension {

    public final DcMotor spoolMotor;

    public final DcMotor armMotor;

    /**
     * This class is created in order to simplify the programming of the Suspension mechanism
     *
     * @param hardwareMap mapping Physical objects to Robot Config.
     */


    public Suspension(HardwareMap hardwareMap) {
        spoolMotor = hardwareMap.get(DcMotor.class, "Spool");
        armMotor = hardwareMap.get(DcMotor.class, "Arm");

        spoolMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        armMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
    }

    public @Api void spoolDaSpool(Gamepad gamepad, @Nullable Telemetry telemetry) {
        spoolMotor.setPower(gamepad.left_stick_y);
        armMotor.setPower(gamepad.right_stick_y * 0.5);

        if (telemetry == null) return;

        telemetry.addData("Spool Position", spoolMotor.getCurrentPosition());
        telemetry.addData("Arm Position", armMotor.getCurrentPosition());
        telemetry.addData("Spool Speed", spoolMotor.getPower());
        telemetry.addData("Arm Speed", armMotor.getPower());
    }


}

