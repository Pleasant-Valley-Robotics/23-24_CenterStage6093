package org.firstinspires.ftc.teamcode.hardware;

import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.utility.Api;

public class PixelDropper {
    private final Servo servo;

    public PixelDropper(HardwareMap hardwareMap) {
        servo = hardwareMap.get(Servo.class, "Servo");
        servo.setPosition(-1);
    }

    public @Api void dropPixel() {
        try {
            servo.setPosition(1);
            // wait 2s for servo to get there
            Thread.sleep(2000);
            servo.setPosition(-1);
        } catch (InterruptedException ignored) {
        }
    }
}
