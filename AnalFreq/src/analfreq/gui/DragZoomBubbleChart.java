package analfreq.gui;

import analfreq.config.Config;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.geometry.Point2D;
import javafx.scene.chart.BubbleChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.Region;

public class DragZoomBubbleChart extends BubbleChart {

    private final NumberAxis xAxis, yAxis;
    private final Region plotArea;
    private int currMaxFreq = Config.MAX_FREQ;
    private int currEndTime = Config.END_TIME;
    private final DoubleProperty lastMouseX = new SimpleDoubleProperty();
    private final DoubleProperty lastMouseY = new SimpleDoubleProperty();
    private boolean shiftPressed = false;

    public void setShiftPressed(boolean b) {
        shiftPressed = b;
    }

    public DragZoomBubbleChart(NumberAxis xAxis, NumberAxis yAxis) {
        super(xAxis, yAxis);
        this.xAxis = xAxis;
        this.yAxis = yAxis;
        plotArea = (Region) lookup(".chart-plot-background");
        initMouseHandlers();
    }

    private void initMouseHandlers() {
        
        setOnMousePressed((MouseEvent event) -> {
            final double x = event.getX();
            final double y = event.getY();
            if (plotArea.getBoundsInParent().contains(new Point2D(x, y))) {
                lastMouseX.set(x);
                lastMouseY.set(y);
            }
        });

        setOnMouseDragged((MouseEvent event) -> {
            final double x = event.getX();
            final double y = event.getY();
            if (plotArea.getBoundsInParent().contains(new Point2D(x, y))) {
                moveAxis((NumberAxis) xAxis, x, lastMouseX);
                moveAxis((NumberAxis) yAxis, y, lastMouseY);
            }
        });

        setOnScroll((ScrollEvent event) -> {

            if (event.getDeltaX() > 0) {
                zoomInTime();
            } else if (event.getDeltaX() < 0) {
                zoomOutTime();
            }

            if (event.getDeltaY() > 0) {
                zoomInFreq();
            } else if (event.getDeltaY() < 0) {
                zoomOutFreq();
            }
        });
    }

    private void zoomInFreq() {
        int newMaxFreq = (int) (currMaxFreq * .5);
        if (newMaxFreq <= 20) {
            newMaxFreq = 20;
        }
        currMaxFreq = newMaxFreq;
        NumberAxis na = (NumberAxis) this.getYAxis();
        if (newMaxFreq <= na.getLowerBound()) {
            newMaxFreq = (int) na.getLowerBound() + Config.STEP_FREQ;
        }
        na.setUpperBound(newMaxFreq);
    }

    private void zoomOutFreq() {
        int newMaxFreq = (int) (currMaxFreq * 1.5);
        if (newMaxFreq >= 20000) {
            newMaxFreq = 20000;
        }
        currMaxFreq = newMaxFreq;
        NumberAxis na = (NumberAxis) this.getYAxis();
        if (newMaxFreq <= na.getLowerBound()) {
            newMaxFreq = (int) na.getLowerBound() + Config.STEP_FREQ;
        }
        na.setUpperBound(newMaxFreq);
    }

    private void zoomInTime() {
        int newEndTime = (int) (currEndTime * .5);
        if (newEndTime <= 2) {
            newEndTime = 2;
        }
        currEndTime = newEndTime;
        NumberAxis na = (NumberAxis) this.getXAxis();
        if (newEndTime <= na.getLowerBound()) {
            newEndTime = (int) na.getLowerBound() + Config.STEP_FREQ;
        }
        na.setUpperBound(newEndTime);
    }

    private void zoomOutTime() {
        int newEndTime = (int) (currEndTime * 1.5);
        if (newEndTime >= 500) {
            newEndTime = 500;
        }
        currEndTime = newEndTime;
        NumberAxis na = (NumberAxis) this.getXAxis();
        if (newEndTime <= na.getLowerBound()) {
            newEndTime = (int) na.getLowerBound() + Config.STEP_FREQ;
        }
        na.setUpperBound(newEndTime);
    }

    private void moveAxis(NumberAxis axis, double mouseLocation,
            DoubleProperty lastMouseLocation) {
        double scale = axis.getScale();
        double delta = (mouseLocation - lastMouseLocation.get()) / scale;
        double newLow = (axis.getLowerBound() - delta);
        double newHi = (axis.getUpperBound() - delta);
        int low = (int) Math.round(newLow);
        int hi = (int) Math.round(newHi);
        axis.setLowerBound(low);
        axis.setUpperBound(hi);
        lastMouseLocation.set(mouseLocation);
    }
}