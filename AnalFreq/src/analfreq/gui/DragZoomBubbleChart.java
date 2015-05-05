/*
 * Copyright (C) 2015 Black River Audio Works (www.blackriveraudioworks.com)
 *
 * This file is part of AnalFreq.
 *
 * Main is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Main is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with AnalFreq.  If not, see <http://www.gnu.org/licenses/>.
 */

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

    private final MouseWheelHandler mouseWheelHandler = new MouseWheelHandler();
    private final NumberAxis xAxis, yAxis;
    private final Region plotArea;
    private int currMaxFreq = Config.MAX_FREQ;
    private int currEndTime = Config.END_TIME;
    private final DoubleProperty lastMouseX = new SimpleDoubleProperty();
    private final DoubleProperty lastMouseY = new SimpleDoubleProperty();
   
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
           MouseWheelDirection direction = mouseWheelHandler.getDirection(event);
           if(direction.equals(MouseWheelDirection.UP) && !event.isShiftDown()){
               zoomInFreq();
           } else if(direction.equals(MouseWheelDirection.DOWN) && !event.isShiftDown()){
               zoomOutFreq();
           } else if(direction.equals(MouseWheelDirection.UP) && event.isShiftDown()){
               zoomInTime();
           }else if(direction.equals(MouseWheelDirection.DOWN) && event.isShiftDown()){
               zoomOutTime();
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
