package analfreq.freqevent;

/**
 * "FreqEvent" == actual underlying data. != bubblechart representation. So not
 * the "bubbles" on screen
 *
 * @author dain
 */
public final class FreqEvent {

    private int startFreq; // Optional(for now)
    private int centerFreq; // Required
    private int endFreq; // Optional(for now)
    private String instrument; // Required
    private String description; // Optional

    public FreqEvent(int centerFreq, String instrument) {
        this.centerFreq = centerFreq;
        this.instrument = instrument;
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
     * @return the instrument
     */
    public String getInstrument() {
        return instrument;
    }

    /**
     * @param instrument the instrument to set
     */
    public void setInstrument(String instrument) {
        this.instrument = instrument;
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

    //We can improve toString later
    public String toString() {

        String result = "";
        result = result + "Instrument: " + instrument
                + "\nDescription: " + description
                + "\nStarting Frequency: " + startFreq
                + "\nCenter Frequency: " + centerFreq
                + "\nEnding Frequency: " + endFreq;
        return result;
    }

}
