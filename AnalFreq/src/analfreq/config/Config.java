package analfreq.config;

public class Config {
    
    public static final String STAGE_TITLE = "Frequency Masking";
    public static final String X_AXIS_LABEL = "Time (Seconds)";
    public static final String Y_AXIS_LABEL = "Frequency (Hz)";
    public static final String BUBBLE_CHART_TITLE = "Frequency Events";
    public static final boolean DEBUG = true;
    
    /**
     * Convenience method for printing out debug info. Code throughout the
     * system can use this by calling Config.debug("Some Message Here"); and
     * the message will only print out if debugging is enabled. To enable 
     * debugging, set DEBUG to true.
     * @param s the String to print
     */
    public static void debug(String s){
        if(DEBUG){
            System.out.println(s);
        }
    }
    
    /**
     * To be replaced later with dynamic values
     */
    public static final int MIN_SEC = 1;
    public static final int MAX_SEC = 120;
    public static final int STEP_SEC = 5;
    public static final int MIN_FREQ = 0;
    
    // zoom in = smaller MAX_FREQ
    public static int MAX_FREQ = 500;
    public static int STEP_FREQ = 20;
}