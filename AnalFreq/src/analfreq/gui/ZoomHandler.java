package analfreq.gui;

import analfreq.config.Config;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.event.EventHandler;
import javafx.geometry.Point2D;
import javafx.scene.chart.Axis;
import javafx.scene.chart.BubbleChart;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.Region;

/**
 * Provides zoom capability for the Frequency(Hz) axis of the BubbleChart. Use
 * the mouse scroll wheel to zoom in/out.
 */
public class ZoomHandler {

    private static NumberAxis xAxis;
    private static NumberAxis yAxis;
    private static Region plotArea;
    private static final DoubleProperty lastMouseX = new SimpleDoubleProperty();
    private static final DoubleProperty lastMouseY = new SimpleDoubleProperty();

    public static void handleZoom(BubbleChart bubbleChart) {

        xAxis = (NumberAxis) bubbleChart.getXAxis();
        yAxis = (NumberAxis) bubbleChart.getYAxis();
        plotArea = (Region) bubbleChart.lookup(".chart-plot-background");

        bubbleChart.setOnMousePressed((MouseEvent event) -> {
            final double x = event.getX();
            final double y = event.getY();
            if (plotArea.getBoundsInParent().contains(new Point2D(x, y))) {
                lastMouseX.set(x);
                lastMouseY.set(y);
            }
        });

        bubbleChart.setOnMouseDragged((MouseEvent event) -> {
            final double x = event.getX();
            final double y = event.getY();
            if (plotArea.getBoundsInParent().contains(new Point2D(x, y))) {
                moveAxis(xAxis, x, lastMouseX);
                moveAxis(yAxis, y, lastMouseY);
            }
        });
    }

    private static void moveAxis(NumberAxis axis, double mouseLocation,
            DoubleProperty lastMouseLocation) {
        double scale = axis.getScale();
        double delta = (mouseLocation - lastMouseLocation.get()) / scale;
        axis.setLowerBound(axis.getLowerBound() - delta);
        axis.setUpperBound(axis.getUpperBound() - delta);
        lastMouseLocation.set(mouseLocation);
    }


    /*
     OLD ZOOM CODE: KEEP FOR NOW AS COMMENT
     private static BubbleChart bubbleChart;
     private static int scrollPos = 0;
     private static int currMaxY = Config.MAX_FREQ;

     // first step in zoom.. not finished yet
     public static void handleZoom(BubbleChart bc) {
     bubbleChart = bc;
     bubbleChart.setOnScroll((ScrollEvent event) -> {
     if (event.getDeltaY() > 0) {
     zoomIn();
     } else {
     zoomOut();
     }
     });
     }

     private static void zoomIn() {
     int newMaxY = (int) (currMaxY * .5);
     if (newMaxY <= 20) {
     newMaxY = 20;
     }
     currMaxY = newMaxY;
     NumberAxis yAxis = (NumberAxis) bubbleChart.getYAxis();
     yAxis.setAnimated(true);
     yAxis.setUpperBound(newMaxY);
     }

     private static void zoomOut() {
     int newMaxY = (int) (currMaxY * 1.5);
     if (newMaxY >= 20000) {
     newMaxY = 20000;
     }
     currMaxY = newMaxY;
     NumberAxis yAxis = (NumberAxis) bubbleChart.getYAxis();
     yAxis.setAnimated(true);
     yAxis.setUpperBound(newMaxY);
     }
     */
}
