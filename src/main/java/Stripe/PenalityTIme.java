package Stripe;

public class PenalityTIme {

    private final Character wc = 'Y';
    private final Character nc = 'N';
    private int penality(String log, int startIndex, int endIndex, Character customer) {
        int p = 0;
        for(int i = startIndex; i < endIndex; i++) {
            if(log.charAt(i) == customer) {
                p++;
            }
        }
        return p;
    }
    public int computePenality(String log, int closeTime) {
        if(log == null || log.length() == 0) {
            return 0;
        }
        if(closeTime > 0) {
            closeTime = 2*closeTime - 1;
        }
        return penality(log, 0, closeTime, nc) + penality(log, closeTime, log.length(), wc);
    }

    public int bestClosingTime(String log) {
        int len = log.length();
        int tc = (len/2) + 1;
        int mn = Integer.MAX_VALUE;
        int ans = -1;
        for(int i = 0; i <= tc; i++) {
            int p = computePenality(log, i);
            if(p < mn) {
                ans = i;
                mn = p;
            }
        }
        return ans;
    }
}
