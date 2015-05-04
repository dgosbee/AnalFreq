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
import javafx.scene.chart.NumberAxis;

public class DragZoomBubbleChartManager {

    public static DragZoomBubbleChart createBubbleChart() {
        final NumberAxis xAxis = new NumberAxis(Config.MIN_SEC, Config.END_TIME, Config.STEP);
        final NumberAxis yAxis = new NumberAxis(Config.MIN_FREQ,Config.MAX_FREQ, Config.STEP_FREQ);
        xAxis.setLabel(Config.X_AXIS_LABEL);
        yAxis.setLabel(Config.Y_AXIS_LABEL);
        final DragZoomBubbleChart dzBubbleChart = new DragZoomBubbleChart(xAxis, yAxis);
        dzBubbleChart.setTitle(Config.BUBBLE_CHART_TITLE);
        dzBubbleChart.setAnimated(false);
        return dzBubbleChart;
    }
}
