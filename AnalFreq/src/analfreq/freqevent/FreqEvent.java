package analfreq.freqevent;

/**
 * A FreqEvent represents the underlying data of the object to be plotted.
 * It is not the actual "bubble" on screen, but rather, the data represented
 * by that bubble.
 */
public final class FreqEvent {

    // Required Fields
    private int centerFreq;
    private String name;
    
    // Optional Fields
    private int minFreq;  
    private int maxFreq;
    private String description;

    /**
     * Created a new FreqEvent with the specified instrument name 
     * and center frequency. These are the only two fields required
     * for instantiation.
     * 
     * @param name The name of the instrument
     * @param centerFreq The center frequency
     */
    public FreqEvent(String name, int minFreq, int maxFreq) {
        this.name = name;
        this.minFreq = minFreq;
        this.maxFreq = maxFreq;
        this.centerFreq = (minFreq+maxFreq)/2;
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
        result = result + "Instrument: " + name
                + "\nDescription: " + description
                + "\nMinimum Frequency: " + minFreq
                + "\nCenter Frequency: " + centerFreq
                + "\nMaximum Frequency: " + maxFreq;
        return result;
    }
}