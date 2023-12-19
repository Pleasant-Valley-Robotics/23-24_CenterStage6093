package org.firstinspires.ftc.teamcode.hardware;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.utility.Api;

public class Suspension {
    private final DcMotor leftScrew;
    private final DcMotor rightScrew;

    public Suspension(HardwareMap hardwareMap) {
        leftScrew = hardwareMap.get(DcMotor.class, "Left Screw");
        rightScrew = hardwareMap.get(DcMotor.class, "Right Screw");

        leftScrew.setDirection(DcMotorSimple.Direction.FORWARD);
        rightScrew.setDirection(DcMotorSimple.Direction.FORWARD);
    }

    /**
     * Sets both arm powers.
     * @param power The power to set. Positive is up. [-1, 1].
     */
    public @Api void screwDaScrew(double power) {
        leftScrew.setPower(power);
        rightScrew.setPower(power);
    }
}
