package CodingQuestions.Functions;

import java.util.List;
import java.util.Set;

public interface FunctionMatch {
     void register(Set<FunctionCustom> functionSet);

    List<String> findMatches(List<String> argumentTypes);
}
