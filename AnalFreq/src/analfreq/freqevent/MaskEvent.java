package analfreq.freqevent;

/**
 * This class represents two frequency events that mask one another. 
 */
public class MaskEvent {
 
    private final FreqEvent[] freqEvents = new FreqEvent[2];
    
    public MaskEvent(FreqEvent event1, FreqEvent event2){
        freqEvents[0] = event1;
        freqEvents[1] = event2;
    }
    
    public FreqEvent[] getFreqEvents(){
        return freqEvents;
    }
    
    public String toString(){
        return "MASKING: "+freqEvents[0].getName()
                +" "+freqEvents[1].getName();
    }
}
