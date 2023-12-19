package org.firstinspires.ftc.teamcode.hardware;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.utility.Api;

public class Suspension {
    private final DcMotor left;
    private final DcMotor right;

    public Suspension(HardwareMap hardwareMap) {
        left = hardwareMap.get(DcMotor.class, "Left Arm");
        right = hardwareMap.get(DcMotor.class, "Right Arm");
    }

    /**
     * Sets both arm powers.
     * @param power The power to set. Positive is up. [-1, 1].
     */
    public @Api void setArmPower(double power) {
        left.setPower(power);
        right.setPower(power);
    }
}
