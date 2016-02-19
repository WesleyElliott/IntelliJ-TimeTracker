package com.wesleyelliott.timetracker.util;

import com.sun.jna.Library;
import com.sun.jna.Native;

/**
 * Created by Wesley on 2016/02/18.
 */
public class OSxIdleTime  {
    public interface ApplicationServices extends Library {

        ApplicationServices INSTANCE = (ApplicationServices) Native.loadLibrary("ApplicationServices", ApplicationServices.class);

        int kCGAnyInputEventType = ~0;
        int kCGEventSourceStatePrivate = -1;
        int kCGEventSourceStateCombinedSessionState = 0;
        int kCGEventSourceStateHIDSystemState = 1;

        double CGEventSourceSecondsSinceLastEventType(int sourceStateId, int eventType);
    }

    public static long getIdleTimeMillis() {
        double idleTimeSeconds = ApplicationServices.INSTANCE.CGEventSourceSecondsSinceLastEventType(ApplicationServices.kCGEventSourceStateCombinedSessionState, ApplicationServices.kCGAnyInputEventType);
        return (long) (idleTimeSeconds * 1000);
    }
}