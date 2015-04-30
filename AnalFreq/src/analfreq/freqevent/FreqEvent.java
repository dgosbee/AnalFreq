package analfreq.freqevent;

/**
 * A FreqEvent represents the underlying data of the object to be plotted.
 * It is not the actual "bubble" on screen, but rather, the data represented
 * by that bubble.
 */
public final class FreqEvent {

    private String name,description;
    private int minFreq, maxFreq, centerFreq, startTime, midTime, endTime;
 
    public FreqEvent(String name, int minFreq, int maxFreq,
            int startTime, int endTime) {
        this.name = name;
        this.minFreq = minFreq;
        this.maxFreq = maxFreq;
        this.centerFreq = (minFreq+maxFreq)/2;
        this.startTime = startTime;
        this.midTime = (startTime+endTime)/2;
        this.endTime = endTime;
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

    public String getInstrument() {
        return name;
    }

    public void setInstrument(String instrument) {
        this.name = instrument;
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

    public int getMidTime() {
        return midTime;
    }
}