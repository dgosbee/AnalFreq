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
    private int startFreq;  
    private int endFreq;
    private String description;

    /**
     * Created a new FreqEvent with the specified instrument name 
     * and center frequency. These are the only two fields required
     * for instantiation.
     * 
     * @param name The name of the instrument
     * @param centerFreq The center frequency
     */
    public FreqEvent(String name, int centerFreq) {
        this.name = name;
        this.centerFreq = centerFreq;
    }

    /**
     * @return the startFreq
     */
    public int getStartFreq() {
        return startFreq;
    }

    /**
     * @param startFreq the startFreq to set
     */
    public void setStartFreq(int startFreq) {
        this.startFreq = startFreq;
    }

    /**
     * @return the centerFreq
     */
    public int getCenterFreq() {
        return centerFreq;
    }

    /**
     * @param centerFreq the centerFreq to set
     */
    public void setCenterFreq(int centerFreq) {
        this.centerFreq = centerFreq;
    }

    /**
     * @return the endFreq
     */
    public int getEndFreq() {
        return endFreq;
    }

    /**
     * @param endFreq the endFreq to set
     */
    public void setEndFreq(int endFreq) {
        this.endFreq = endFreq;
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
                + "\nStarting Frequency: " + startFreq
                + "\nCenter Frequency: " + centerFreq
                + "\nEnding Frequency: " + endFreq;
        return result;
    }
}