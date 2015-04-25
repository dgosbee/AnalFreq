package analfreq.config;

public class Config {

    //This stays here
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
    public static final int MIN_X_AXIS_LABEL = 1;
    public static final int MAX_X_AXIS_LABEL = 25;
    public static final int STEP_X_AXIS = 5;
    public static final int MIN_Y_AXIS_LABEL = 20;
    public static final int MAX_Y_AXIS_LABEL = 500;
    public static final int STEP_Y_AXIS = 20;
}