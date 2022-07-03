package LowleveldesignGrokking.CricInfo;

public class Umpire {
    private Person person;
    private UmpireType umpireType;
    private Match match;
    public Umpire(Person person, UmpireType umpireType) {
        this.person = person;
        this.umpireType = umpireType;
    }
    public boolean assignMatch(Match match) {
        this.match = match;
        return  true;
    }
}
