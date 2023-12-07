package org.firstinspires.ftc.teamcode.hardware;

import android.util.Size;

import androidx.annotation.Nullable;

import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.robotcore.external.hardware.camera.Camera;
import org.firstinspires.ftc.teamcode.utility.Api;
import org.firstinspires.ftc.teamcode.utility.CubeSide;
import org.firstinspires.ftc.teamcode.utility.FieldSide;
import org.firstinspires.ftc.vision.VisionPortal;

public class VisionCamera {
    private final VisionPortal portal;
    private final CubePipeline pipeline;

    public VisionCamera(HardwareMap hardwareMap, FieldSide fieldSide) {
        Camera camera = hardwareMap.get(Camera.class, "Webcam 1");
        this.pipeline = new CubePipeline(fieldSide);
        this.portal = new VisionPortal.Builder()
                .setCamera(camera.getCameraName())
                .setCameraResolution(new Size(955, 537))
                .enableLiveView(true)
                .addProcessor(pipeline)
                .build();
    }

    /**
     * Adds this camera's data to telemetry.
     * @param telemetry The telemetry object to add to.
     */
    public @Api void addTelemetry(Telemetry telemetry) {
        telemetry.addData("Fps", portal.getFps());
    }

    /**
     * For use in auto to find which side our custom game object is on.
     * The custom game object must be a cube with the color of the side that the auto is being run in.
     * @return the current position prediction, or null if it can't see one.
     */
    @Nullable
    public @Api CubeSide getCubePrediction() {
        return pipeline.getCubeSide();
    }
}
