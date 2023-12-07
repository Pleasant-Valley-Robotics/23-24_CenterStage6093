package org.firstinspires.ftc.teamcode.hardware;

import android.util.Size;

import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.hardware.camera.Camera;
import org.firstinspires.ftc.teamcode.CubeSide;
import org.firstinspires.ftc.teamcode.FieldSide;
import org.firstinspires.ftc.vision.VisionPortal;
import org.firstinspires.ftc.vision.VisionProcessor;

public class VisionCamera {
    private final Camera camera;
    private final VisionPortal portal;
    private final CubePipeline pipeline;

    public VisionCamera(HardwareMap hardwareMap, FieldSide fieldSide) {
        this.camera = hardwareMap.get(Camera.class, "Webcam 1");
        this.pipeline = new CubePipeline(fieldSide);
        this.portal = new VisionPortal.Builder()
                .setCamera(camera.getCameraName())
                .setCameraResolution(new Size(955, 537))
                .enableLiveView(true)
                .addProcessor(pipeline)
                .build();
    }

    public CubeSide getCubePrediction() {
        return pipeline.getCubeSide();
    }
}
