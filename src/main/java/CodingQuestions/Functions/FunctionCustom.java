package CodingQuestions.Functions;

import java.util.List;

public class FunctionCustom {
    String name;
    List<String> argType;

    boolean isRep;

    public FunctionCustom(String name, List<String> argType, boolean isRep) {
        this.name = name;
        this.argType = argType;
        this.isRep = isRep;
    }
}
