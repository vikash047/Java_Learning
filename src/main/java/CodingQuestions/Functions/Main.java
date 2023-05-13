package CodingQuestions.Functions;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class Main {
    public static void main(String[] args) {
        FunctionMatch functionMatch = new FunctionMatchImpl();
        Set<FunctionCustom> functionCustomSet = new HashSet<>();
        functionCustomSet.add(new FunctionCustom("FuncA", Arrays.asList("String", "Integer", "Integer"), false));
        functionCustomSet.add(new FunctionCustom("FuncB", Arrays.asList("String", "Integer"), true));
        functionCustomSet.add(new FunctionCustom("FuncC", Arrays.asList("Integer"), true));
        functionCustomSet.add(new FunctionCustom("FuncD", Arrays.asList("Integer", "Integer"), true));
        functionCustomSet.add(new FunctionCustom("FuncE", Arrays.asList("Integer", "Integer", "Integer"), false));
        functionCustomSet.add(new FunctionCustom("FuncF", Arrays.asList("String"), false));
        functionCustomSet.add(new FunctionCustom("FuncG", Arrays.asList("Integer"), false));
        functionCustomSet.add(new FunctionCustom("FuncH", Arrays.asList("Integer", "Integer", "Integer"), true));
        functionMatch.register(functionCustomSet);
        var res = functionMatch.findMatches(Arrays.asList("Integer"));
        for(var n : res) {
            System.out.println(n);
        }
    }
}
