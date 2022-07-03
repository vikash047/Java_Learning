package LowleveldesignGrokking.CricInfo;

import java.util.List;

public class Player {
    private Person person;
    private List<PlayerContract> contracts;
    public void addContracts(PlayerContract contract) {
        this.contracts.add(contract);
    }
}
