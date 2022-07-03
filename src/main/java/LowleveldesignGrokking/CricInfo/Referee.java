package LowleveldesignGrokking.CricInfo;

public class Referee {
    private Person person;
    private Match match;
    public boolean addMatch(Match match) {
        this.match = match;
        return true;
    }
}
