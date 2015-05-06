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


package analfreq.freqevent;

/**
 * A FreqEvent represents the underlying data of the object to be plotted.
 * It is not the actual "bubble" on screen, but rather, the data represented
 * by that bubble.
 */
public final class FreqEvent {

    private String name,description;
    private FreqEventType type;
    private int minFreq, maxFreq, centerFreq, 
            startTime, midTime, endTime,ID;
   
    public FreqEvent(String name, FreqEventType type, int minFreq, int maxFreq,
            int startTime, int endTime) {
        this.name = name;
        this.type = type;
        this.minFreq = minFreq;
        this.maxFreq = maxFreq;
        this.centerFreq = (minFreq+maxFreq)/2;
        this.startTime = startTime;
        this.midTime = (startTime+endTime)/2;
        this.endTime = endTime;
        this.ID = this.hashCode();
    }

    public int getID(){
        return this.ID;
    }
    
    public FreqEventType getType(){
        return type;
    }
    
    public void setFreqEventType(FreqEventType type){
        this.type = type;
    }
    
    public int getMinFreq() {
        return minFreq;
    }

    public void setMinFreq(int startFreq) {
        this.minFreq = startFreq;
    }

    /**
     * The center frequency is automatically calculated based on the provided
     * minimum and maximum frequencies.
     * @return the centerFreq
     */
    public int getCenterFreq() {
        return centerFreq;
    }

    public int getMaxFreq() {
        return maxFreq;
    }

    public void setMaxFreq(int endFreq) {
        this.maxFreq = endFreq;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String toString() {
        String result = "";
        result = result + "Event: " + name
                + "\nEvent Type: " + type.toString()
                + "\nMaximum Frequency: " + maxFreq
                + "\nCenter Frequency: " + centerFreq
                + "\nMinimum Frequency: " + minFreq
                + "\nStart Time: " + startTime
                + "\nEnd Time: " + endTime 
                + "\nDescription: "+ description;
        return result;
    }

    public int getStartTime() {
        return startTime;
    }

    public void setStartTime(int startTime) {
        this.startTime = startTime;
    }

    public int getEndTime() {
        return endTime;
    }
    
    public void setEndTime(int endTime) {
        this.endTime = endTime;
    }
    
    /**
     * The mid time is automatically calculated based on the provided
     * start and end times.
     * @return the mid time
     */
    public int getMidTime() {
        return midTime;
    }
}