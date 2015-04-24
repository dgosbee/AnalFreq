package analfreq.config;

public class Config {

    //This stays here
    public static final String stageTitle = "Frequency Masking";
    public static final String xAxisLabel = "Time";
    public static final String yAxisLabel = "Frequency";
    public static final String blcTitle = "Events";

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
