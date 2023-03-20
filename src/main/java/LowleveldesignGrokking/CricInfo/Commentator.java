package LowleveldesignGrokking.CricInfo;

public class Commentator {
    private Person person;
    private Match match;
    public boolean  assignMatch(Match match) {
        this.match = match;
        return true;
    }
}
