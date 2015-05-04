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
            
            /*
            NOTE: This code is temporary. There MUST be some better
            way to determine which direction the mouse wheel is turning.
            But so far we can only figure out how to do it this way. We do 
            a check for the operating system and run one kind of if statement 
            for Linux, and another for Mac/Win.
            */
            
            if (System.getProperty("os.name").equals("Linux")) {
                doLinuxZoom(event);
            } else {
                doMacWinZoom(event);
            }
        });
    }

    private void doLinuxZoom(ScrollEvent event) {

        if (event.getDeltaY() > 0 && (!event.isShiftDown())) {
            zoomInFreq();
        }

        if (event.getDeltaY() < 0 && (!event.isShiftDown())) {
            zoomOutFreq();
        }

        if (event.getDeltaY() > 0 && (event.isShiftDown())) {
            zoomInTime();
        }

        if (event.getDeltaY() < 0 && (event.isShiftDown())) {
            zoomOutTime();
        }
    }

    private void doMacWinZoom(ScrollEvent event) {

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
