package Interview.IO.EventBus.Util;

import Interview.IO.EventBus.Models.TimeStamp;
import com.google.inject.Singleton;

@Singleton
public class Timer {
    public static TimeStamp getTimeStamp() {return new TimeStamp(System.nanoTime());}
}
