package org.firstinspires.ftc.teamcode.hardware;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.utility.Api;

public class Mouth {
    private final DcMotorEx teeth;
    private final Servo lips;

    /**
     * @param hardwareMap The hardware map to initialize the robot with.
     */
    public Mouth(HardwareMap hardwareMap) {
        teeth = hardwareMap.get(DcMotorEx.class, "teeth");
        lips = hardwareMap.get(Servo.class, "LIPS");

        lips.setPosition(0);

        teeth.setDirection(DcMotorSimple.Direction.FORWARD);

        teeth.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        teeth.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
    }

    /**
     * Take in pixel.
     */
    public @Api void consumption() {
        //Make motor spin forward to propel rubber teeth forward.
        teeth.setPower(1);
    }

    /**
     * Spit out pixel.
     */
    public @Api void barfing() {
        //Make motor resume to get the pixel out.
        teeth.setPower(-1);
    }

    /**
     * Closes the servo.
     */
    public @Api void bite() {
        lips.setPosition(0);
    }

    /**
     * Opens the servo.
     */
    public @Api void open() {
        lips.setPosition(1);
    }
}


