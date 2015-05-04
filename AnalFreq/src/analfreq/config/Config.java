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


package analfreq.config;

public class Config {
    
    public static final String XML_PATH = "src/analfreq/xml/project-data.xml";
    public static final String STAGE_TITLE = "Frequency Masking";
    public static final String X_AXIS_LABEL = "Time (Seconds)";
    public static final String Y_AXIS_LABEL = "Frequency (Hz)";
    public static final String BUBBLE_CHART_TITLE = "Frequency Events";
    public static final boolean DEBUG = true;
   
    public static final int MIN_FREQ = 0;
    public static final int MIN_SEC = 0;
    public static final int STEP = 2;
    public static int MAX_FREQ = 250;
    public static int STEP_FREQ = 20;
    public static int END_TIME = 30;
}