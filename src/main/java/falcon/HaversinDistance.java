package falcon;

public class HaversinDistance {

    public static final double radiusOfEarthInKM = 6371;
    public static double calculateDistance(double lat1, double long1, double lat2, double long2) {
        double diffLat = Math.toRadians(lat2 - lat1);
        double diffLong = Math.toRadians(long2 - long1);
        lat1 = Math.toRadians(lat1);
        lat2 = Math.toRadians(lat2);

        // Apply formula
        // a = sqrt((sin^2(diffLat)/2) + cosLat1cosLat2sin(diffLong)/2)
        // d = 2r*sin-1(a);
        double a = Math.pow(Math.sin(diffLat/2), 2) +
                Math.pow(Math.sin(diffLong/2), 2) * Math.cos(lat1) * Math.cos(lat2);
        double c = 2*Math.asin(Math.sqrt(a));
        return radiusOfEarthInKM*c;
    }
}
