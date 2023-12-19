package org.firstinspires.ftc.teamcode.hardware;

import androidx.annotation.Nullable;

import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.utility.Api;

public class DroneLauncher {

    public Servo daPewpew;

    public DroneLauncher(HardwareMap hardwareMap, @Nullable Telemetry telemetry) {
        daPewpew = hardwareMap.get(Servo.class, "drone");
        daPewpew.setPosition(0);
        daPewpew.setDirection(Servo.Direction.FORWARD);

        telemetry.addData("DaPewPew Position", daPewpew.getPosition());
        telemetry.addData("DaPew Direction", daPewpew.getDirection());
        telemetry.update();
    }

    public @Api void LaunchDathOe() {
        daPewpew.setPosition(0.38);
    }

    public @Api void UnLaunchDathOe() {
        daPewpew.setPosition(0);
    }


}
