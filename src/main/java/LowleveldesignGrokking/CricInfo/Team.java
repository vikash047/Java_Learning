package LowleveldesignGrokking.CricInfo;

import java.util.List;

public class Team {
    private String name;
    private List<Player> playerList;
    private List<News>  newsList;
    private Coach coach;

    public Team(String name, List<Player> playerList, List<News> newsList, Coach coach) {
        this.name = name;
        this.playerList = playerList;
        this.newsList = newsList;
        this.coach = coach;
    }
    public void  addTouranamentSquad(TournamentSquad tournamentSquad) {

    }
    public void addPlayer(Player player) {

    }
    public void addNews(News news) {

    }
}
