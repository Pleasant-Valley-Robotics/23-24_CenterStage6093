package org.firstinspires.ftc.teamcode.hardware;

import static java.util.Collections.emptyList;

import android.graphics.Canvas;
import android.graphics.Paint;

import androidx.annotation.Nullable;

import org.firstinspires.ftc.robotcore.internal.camera.calibration.CameraCalibration;
import org.firstinspires.ftc.teamcode.utility.Api;
import org.firstinspires.ftc.teamcode.utility.CubeSide;
import org.firstinspires.ftc.teamcode.utility.FieldSide;
import org.firstinspires.ftc.teamcode.utility.Pair;
import org.firstinspires.ftc.vision.VisionProcessor;
import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.MatOfPoint;
import org.opencv.core.Point;
import org.opencv.core.Scalar;
import org.opencv.core.Size;
import org.opencv.imgproc.Imgproc;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.NoSuchElementException;

public class CubePipeline implements VisionProcessor {
    private static final Scalar blueMins = new Scalar(0, 0, 140);
    private static final Scalar blueMaxs = new Scalar(255, 255, 255);

    private static final Scalar redMins = new Scalar(0, 160, 0);
    private static final Scalar redMaxs = new Scalar(255, 255, 255);

    private static final double crop_x = 90;
    private static final double crop_y = 130;

    private static final int cutoff_left = 349;
    private static final int cutoff_right = 606;

    private final List<Mat> yrb = new ArrayList<>();
    private final Mat buf = new Mat();
    private final Mat mask = new Mat();

    private final List<MatOfPoint> contours = new ArrayList<>();
    private final Mat hierarchy = new Mat();

    private final FieldSide fieldSide;
    private CubeSide cubeSide = null;

    public CubePipeline(FieldSide fieldSide) {
        this.fieldSide = fieldSide;
    }

    @Override
    public void init(int width, int height, CameraCalibration calibration) {
        yrb.add(new Mat(width, height, CvType.CV_8UC1));
        yrb.add(new Mat(width, height, CvType.CV_8UC1));
        yrb.add(new Mat(width, height, CvType.CV_8UC1));
    }

    private void filterRange(Mat src, Mat mask, double start, double end) {
        Imgproc.threshold(src, buf, start, 255, Imgproc.THRESH_BINARY);
        mask.copyTo(buf, buf);
        Imgproc.threshold(src, mask, end, 255, Imgproc.THRESH_BINARY_INV);
        buf.copyTo(mask, mask);
    }

    @Nullable
    public @Api CubeSide getCubeSide() {
        return cubeSide;
    }

    /**
     * @noinspection OptionalGetWithoutIsPresent
     */
    @Override
    public Object processFrame(Mat frame, long captureTimeNanos) {
        Size size = frame.size();
        frame = frame.submat((int) crop_y, (int) size.height - (int) crop_y, (int) crop_x, (int) size.width - (int) crop_x);

        Imgproc.cvtColor(frame, frame, Imgproc.COLOR_RGBA2RGB);
        Imgproc.cvtColor(frame, frame, Imgproc.COLOR_RGB2YCrCb);
        Core.split(frame, yrb);

        frame.copyTo(mask);
        Imgproc.cvtColor(frame, frame, Imgproc.COLOR_YCrCb2RGB);

        if (fieldSide.isBlue()) {
            filterRange(yrb.get(0), mask, blueMins.val[0], blueMaxs.val[0]);
            filterRange(yrb.get(1), mask, blueMins.val[1], blueMaxs.val[1]);
            filterRange(yrb.get(2), mask, blueMins.val[2], blueMaxs.val[2]);
        } else if (fieldSide.isRed()) {
            filterRange(yrb.get(0), mask, redMins.val[0], redMaxs.val[0]);
            filterRange(yrb.get(1), mask, redMins.val[1], redMaxs.val[1]);
            filterRange(yrb.get(2), mask, redMins.val[2], redMaxs.val[2]);
        }

        frame.copyTo(buf, mask);
        Imgproc.medianBlur(buf, frame, 7);
        Imgproc.cvtColor(frame, frame, Imgproc.COLOR_RGB2GRAY);
        Imgproc.findContours(frame, contours, hierarchy, Imgproc.RETR_TREE, Imgproc.CHAIN_APPROX_SIMPLE);

        MatOfPoint maxContour;
        Point maxPoint;

        try {
            maxContour = contours.stream().max(Comparator.comparingDouble(Imgproc::contourArea)).get();
            Point temp = maxContour.toList().stream().reduce((a, b) -> new Point(a.x + b.x, a.y + b.y)).get();
            maxPoint = new Point(temp.x / maxContour.size(0), temp.y / maxContour.size(0));
        } catch (NoSuchElementException e) {
            return new Pair<>(emptyList(), new Point(0, 0));
        }

        if (maxPoint.x < cutoff_left) {
            cubeSide = CubeSide.Left;
        } else if (maxPoint.x > cutoff_right) {
            cubeSide = CubeSide.Right;
        } else {
            cubeSide = CubeSide.Middle;
        }

        contours.clear();

        return new Pair<>(maxContour.toList(), maxPoint);
    }

    @Override
    public void onDrawFrame(Canvas canvas, int onscreenWidth, int onscreenHeight, float scaleBmpPxToCanvasPx, float scaleCanvasDensity, Object userContext) {
        // noinspection unchecked
        final Pair<List<Point>, Point> listPointPair = (Pair<List<Point>, Point>) userContext;
        final List<Point> points = listPointPair.first;
        final Point point = listPointPair.second;

        final Paint pointPaint = new Paint();
        pointPaint.setARGB(255, 255, 255, 0);
        pointPaint.setStyle(Paint.Style.FILL);


        canvas.drawCircle(
                (float) (point.x + crop_x) * scaleBmpPxToCanvasPx,
                (float) (point.y + crop_y) * scaleBmpPxToCanvasPx,
                20, pointPaint);


        final Paint linePaint = new Paint();
        linePaint.setARGB(255, 0, 255, 255);
        linePaint.setStyle(Paint.Style.STROKE);
        linePaint.setStrokeWidth(5);

        for (int i = 0; i < points.size(); i++) {
            Point startPoint = points.get(i);
            Point endPoint = points.get((i + 1) % points.size());
            canvas.drawLine(
                    (float) (startPoint.x + crop_x) * scaleBmpPxToCanvasPx,
                    (float) (startPoint.y + crop_y) * scaleBmpPxToCanvasPx,
                    (float) (endPoint.x + crop_x) * scaleBmpPxToCanvasPx,
                    (float) (endPoint.y + crop_y) * scaleBmpPxToCanvasPx,
                    linePaint);
        }
    }
}
