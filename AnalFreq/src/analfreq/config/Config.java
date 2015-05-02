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