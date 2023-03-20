package LowleveldesignGrokking.CricInfo;

public class Coach {
    private Person person;
    private Team team;
    public boolean assignTeam(Team team) {
        this.team = team;
        return true;
    }
}
