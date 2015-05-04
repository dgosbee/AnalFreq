/*
 * Copyright (C) 2015 Black River Audio Works (www.blackriveraudioworks.com)
 *
 * This file is part of AnalFreq.
 *
 * Main is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Main is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with AnalFreq.  If not, see <http://www.gnu.org/licenses/>.
 */

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
