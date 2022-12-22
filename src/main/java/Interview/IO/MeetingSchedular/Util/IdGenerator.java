package Interview.IO.MeetingSchedular.Util;

import java.util.UUID;

public class IdGenerator {
    public static String getId() {
        return UUID.randomUUID().toString();
    }
}
