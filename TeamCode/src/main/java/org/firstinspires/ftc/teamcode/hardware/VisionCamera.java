package org.firstinspires.ftc.teamcode.hardware;

import android.util.Size;

import androidx.annotation.Nullable;

import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.teamcode.utility.Api;
import org.firstinspires.ftc.teamcode.utility.CubeSide;
import org.firstinspires.ftc.teamcode.utility.FieldSide;
import org.firstinspires.ftc.vision.VisionPortal;

/**
 * Contains all the vision logic we use on the robot.
 */
public class VisionCamera {
    private final VisionPortal portal;
    private final CubePipeline pipeline;

    /**
     * @param hardwareMap The hardwareMap to initialize the camera with.
     * @param fieldSide The side of the field this auto is running on.
     */
    public VisionCamera(HardwareMap hardwareMap, FieldSide fieldSide) {
        WebcamName camera = hardwareMap.get(WebcamName.class, "Webcam 1");
        this.pipeline = new CubePipeline(fieldSide);
        this.portal = new VisionPortal.Builder()
                .setCamera(camera)
                .setCameraResolution(new Size(960, 720))
                .enableLiveView(true)
                .addProcessor(pipeline)
                .build();
        this.portal.setProcessorEnabled(this.pipeline, true);
        this.portal.resumeStreaming();
        this.portal.resumeLiveView();
    }

    /**
     * Adds this camera's data to telemetry.
     * @param telemetry The telemetry object to add to.
     */
    public @Api void addTelemetry(Telemetry telemetry) {
        telemetry.addData("Fps", portal.getFps());
        telemetry.addData("Cube Side", pipeline.getCubeSide());
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
