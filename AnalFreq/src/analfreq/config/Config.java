package analfreq.config;

public class Config {

    //This stays here
    public static final String stageTitle = "Frequency Masking";
    public static final String xAxisLabel = "Time";
    public static final String yAxisLabel = "Frequency";
    public static final String blcTitle = "Events";
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
     * We are going to replace these later. Dynamic values will be used for
     * mouse zooming capabilities
     */
    public static int minXAxisLabel = 1;
    public static int maxXAxisLabel = 25;
    public static int stepXAxis = 5;
    public static int minYAxisLabel = 20;
    public static int maxYAxisLabel = 100;
    public static int stepYAxis = 20;

}
