package analfreq.gui;

import javafx.scene.input.ScrollEvent;

public class MouseWheelHandler {

    private MouseWheelDirection direction;

    // Reports if the mouse wheel is moving up or down
    public MouseWheelDirection getDirection(ScrollEvent event) {
        if (System.getProperty("os.name").equals("Linux")) {
            handleLinux(event);
        } else {
            handleMacWin(event);
        }
        return direction;
    }

    /*
     * On Linux, getDeltaY() reports a change when turning the mouse wheel up
     * or down, regardless of whether a modifier key like SHIFT has been pressed.  
     */
    private void handleLinux(ScrollEvent event) {
        
        if (event.getDeltaY() > 0) {
            direction = MouseWheelDirection.UP;
        } else if (event.getDeltaY() < 0) {
            direction = MouseWheelDirection.DOWN;
        }
    }

    /*
     * On Mac/Win, getDeltaY() changes only if shift is not pressed. If it is 
     * pressed, getDeltaX() will report the change, and getDeltaY() will report 
     * 0.0.
     */
    private void handleMacWin(ScrollEvent event) {
        
        if (event.getDeltaX() > 0) {
            direction = MouseWheelDirection.UP;
        } else if (event.getDeltaX() < 0) {
            direction = MouseWheelDirection.DOWN;
        } else if (event.getDeltaY() > 0) {
            direction = MouseWheelDirection.UP;
        } else if (event.getDeltaY() < 0) {
            direction = MouseWheelDirection.DOWN;
        }
    }
}
