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
package analfreq.datamanager;

import analfreq.freqevent.FreqEvent;
import analfreq.freqevent.FreqEventType;
import analfreq.gui.UIManager;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.StringTokenizer;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Tooltip;

/**
 * This class forms the communication mechanism between the input GUI and the
 * underlying data that gets created. When a user enters data into the GUI
 * (property editor or new instrument creation wizard) and presses submit, the
 * collected data will be passed to this class for processing.
 */
public class DataManager {

    private static final Set<FreqEvent> allFreqEvents = new HashSet<>(); // no duplicates allowed!
    private static FreqEvent currFreqEvent;
    private static String currSeriesName;
    private static XYChart.Data currData;
    private static double currScaleX;
    private static double currScaleY;
    private static Node currNode;
    private static final Map<Integer, XYChart.Data> freqEventDataMap = new HashMap<>();
    private static FreqEvent toReplace = null;

    
    /**
     * This method was written very quickly. It seems to work, but should be compared
     * against the code in plotFreqEvent. Common code should be factored out to their
     * own methods. There might be unused variables here too... this 
     * really hasn't been tested very much.
     */
    public static void updateFreqEvent(String name, FreqEventType type, String minFreq, String maxFreq,
            String startTime, String endTime, String description, int oldID) {

        // CREATE THE CURRENT FREQ EVENT FROM INCOMING TEXT DATA
        currFreqEvent = new FreqEvent(name, type, Integer.parseInt(minFreq), Integer.parseInt(maxFreq),
                Integer.parseInt(startTime), Integer.parseInt(endTime));
        currFreqEvent.setDescription(description);

        // REPLACE PREVIOUSLY STORED FREQ EVENT WITH NEWLY CREATED FREQ EVENT
        allFreqEvents.stream().forEach((oldFreqEvent) -> {
            if (oldFreqEvent.getID() == oldID) {
                toReplace = oldFreqEvent;
            }
        });
        if (toReplace != null) {
            allFreqEvents.remove(toReplace);
            allFreqEvents.add(currFreqEvent);
        }

        // GET PREVIOUSLY STORED DATA
        currData = freqEventDataMap.get(Integer.valueOf(oldID));

        // ADJUST THE DATA
        currData.setXValue(currFreqEvent.getMidTime());
        currData.setYValue(currFreqEvent.getCenterFreq());

        // SCALE THE DATA
        currScaleX = currFreqEvent.getMidTime() - currFreqEvent.getStartTime();
        currScaleY = currFreqEvent.getCenterFreq() - currFreqEvent.getMinFreq();
        scaleNode();
        
        // SET REMAINING ITEMS
        currNode.setId(Integer.toString(currFreqEvent.getID()));
        installTooltip();
        freqEventDataMap.put(currFreqEvent.getID(), currData);
        addMouseClickSupport(UIManager.getChart().getData());
    }

    public static void plotFreqEvent(String name, FreqEventType type, String minFreq, String maxFreq,
            String startTime, String endTime, String description) {

        // CREATE THE CURRENT FREQ EVENT FROM INCOMING TEXT DATA
        currFreqEvent = new FreqEvent(name, type, Integer.parseInt(minFreq), Integer.parseInt(maxFreq),
                Integer.parseInt(startTime), Integer.parseInt(endTime));
        currFreqEvent.setDescription(description);

        // ADD TO TOTAL SET
        allFreqEvents.add(currFreqEvent);

        // DETERMINE SERIES
        StringTokenizer tokenizer = new StringTokenizer(currFreqEvent.getName());
        currSeriesName = tokenizer.nextToken();

        // CREATE DATA TO PLACE INSIDE THE BUBBLE CHART. ALSO CALCULATE BUBBLE SCALE.
        currData = new XYChart.Data(currFreqEvent.getMidTime(), currFreqEvent.getCenterFreq());
        currScaleX = currFreqEvent.getMidTime() - currFreqEvent.getStartTime();
        currScaleY = currFreqEvent.getCenterFreq() - currFreqEvent.getMinFreq();

        // ADD DATA TO AN APPROPRIATE SERIES
        ObservableList<XYChart.Series> seriesList = UIManager.getChart().getData();
        if (seriesList.isEmpty()) {
            createNewSeries();
        } else {
            doIfSeriesExists();
        }
        currNode.setId(Integer.toString(currFreqEvent.getID()));
        addMouseClickSupport(seriesList);

        // Store for later in case we need it
        freqEventDataMap.put(currFreqEvent.getID(), currData);

    }

    private static void addMouseClickSupport(ObservableList<XYChart.Series> seriesList) {
        // ADD CLICK SUPPORT
        seriesList.stream().forEach((series) -> {
          
            ObservableList<XYChart.Data> dataList = series.getData();
            dataList.stream().forEach((d) -> {
                Node n = d.getNode();
                n.setOnMouseClicked((c) -> {
                    allFreqEvents.stream().forEach((fe) -> {
                        if (Integer.toString(fe.getID()).equals(n.getId())) {
                            UIManager.updateForm(n, fe);
                        }
                    });
                });
            });
        });
    }

    private static void doIfSeriesExists() {
        boolean matchesExistingSeries = false;
        ObservableList<XYChart.Series> seriesList = UIManager.getChart().getData();
        for (XYChart.Series series : seriesList) {
            if (currSeriesName.equals(series.getName())) {
                matchesExistingSeries = true;
                series.getData().add(currData);
                scaleNode();
                installTooltip();
            }
        }
        if (!matchesExistingSeries) {
            createNewSeries();
        }
    }

    private static void createNewSeries() {
        XYChart.Series series = new XYChart.Series();
        series.setName(currSeriesName);
        series.getData().add(currData);
        UIManager.plotObject(series); // node is null until this call!
        scaleNode();
        installTooltip();
    }

    private static void installTooltip() {
        Tooltip.install(currNode, new Tooltip(currFreqEvent.toString()));
    }

    private static void scaleNode() {
        currNode = currData.getNode();
        currNode.setScaleX(currScaleX);
        currNode.setScaleY(currScaleY);
    }

    public static Set<FreqEvent> getFreqEvents() {
        return allFreqEvents;
    }
}
