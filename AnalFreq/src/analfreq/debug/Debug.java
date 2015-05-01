package analfreq.debug;

import static analfreq.config.Config.DEBUG;

public class Debug {

    public static void debug(String s) {
        if (DEBUG) {
            System.out.println(s);
        }
    }
}
