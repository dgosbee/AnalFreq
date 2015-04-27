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

    private int currMaxY = Config.MAX_FREQ;
    private Region plotArea;
    private final DoubleProperty lastMouseX = new SimpleDoubleProperty();
    private final DoubleProperty lastMouseY = new SimpleDoubleProperty();

    public DragZoomBubbleChart(NumberAxis xAxis, NumberAxis yAxis) {

        super(xAxis, yAxis);
        plotArea = (Region) lookup(".chart-plot-background");

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
            if (event.getDeltaY() > 0) {
                zoomIn();
            } else {
                zoomOut();
            }
        });

    }

    private void zoomIn() {
        int newMaxY = (int) (currMaxY * .5);
        if (newMaxY <= 20) {
            newMaxY = 20;
        }
        currMaxY = newMaxY;
        NumberAxis na = (NumberAxis) this.getYAxis();
        if (newMaxY <= na.getLowerBound()) {
            newMaxY = (int) na.getLowerBound() + Config.STEP_FREQ;
        }
        na.setUpperBound(newMaxY);
    }

    private void zoomOut() {
        int newMaxY = (int) (currMaxY * 1.5);
        if (newMaxY >= 20000) {
            newMaxY = 20000;
        }
        currMaxY = newMaxY;
        NumberAxis na = (NumberAxis) this.getYAxis();
        if (newMaxY <= na.getLowerBound()) {
            newMaxY = (int) na.getLowerBound() + Config.STEP_FREQ;
        }
        na.setUpperBound(newMaxY);
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
