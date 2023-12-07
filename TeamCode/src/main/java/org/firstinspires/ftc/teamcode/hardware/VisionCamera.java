package org.firstinspires.ftc.teamcode.hardware;

import org.firstinspires.ftc.robotcore.external.hardware.camera.Camera;
import org.firstinspires.ftc.teamcode.FieldSide;
import org.firstinspires.ftc.vision.VisionPortal;
import org.firstinspires.ftc.vision.VisionProcessor;

public class VisionCamera {
    private final Camera camera;
    private final VisionPortal portal = new VisionPortal.Builder().addProcessor(new CubePipeline(FieldSide.BlueClose)).build();
    public VisionCamera(Camera camera) {
        this.camera = camera;
    }

    public void setup() {
    }
}
