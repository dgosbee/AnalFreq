package analfreq.freqevent;

/**
 * A FreqEvent represents the underlying data of the object to be plotted.
 * It is not the actual "bubble" on screen, but rather, the data represented
 * by that bubble.
 */
public final class FreqEvent {

    private String name;
    private int minFreq;  
    private int maxFreq;
    private int centerFreq;
    private int startTime;
    private int midTime;
    private int endTime;
    private String description;

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

    /**
     * @return the minFreq
     */
    public int getMinFreq() {
        return minFreq;
    }

    /**
     * @param startFreq the minFreq to set
     */
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

    /**
     * @return the maxFreq
     */
    public int getMaxFreq() {
        return maxFreq;
    }

    /**
     * @param endFreq the maxFreq to set
     */
    public void setMaxFreq(int endFreq) {
        this.maxFreq = endFreq;
    }

    /**
     * @return the name
     */
    public String getInstrument() {
        return name;
    }

    /**
     * @param instrument the name to set
     */
    public void setInstrument(String instrument) {
        this.name = instrument;
    }

    /**
     * @return the description
     */
    public String getDescription() {
        return description;
    }

    /**
     * @param description the description to set
     */
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

    /**
     * @return the midTime
     */
    public int getMidTime() {
        return midTime;
    }
}