package fayelab.ddd.fbwreloaded.complete.interpreter;

import java.util.List;
import java.util.Map;

import static java.util.Arrays.asList;
import static java.util.stream.Collectors.toList;

import java.util.HashMap;

public class Parser
{
    private static final String DELIMETER = " ";
    
    static List<List<String>> tokenize(List<String> ruleDescs)
    {        
        return ruleDescs.stream().filter(line -> isNotBlank(line))
                             .map(line -> asList(normalize(line).split(DELIMETER)))
                             .collect(toList());
    }

    static boolean isNotBlank(String line)
    {
        return !line.trim().isEmpty();
    }
    
    static String normalize(String line)
    {
        return line.trim().replaceAll(" +", DELIMETER);
    }
    
    static Rule parseTokens(List<List<String>> tokens)
    {
        Map<String, Rule> nameAndRules = new HashMap<>();
        tokens.stream().forEach(ruleTokens -> parseRuleTokens(ruleTokens, nameAndRules));
        return nameAndRules.get("spec");
    }

    static void parseRuleTokens(List<String> ruleTokens, Map<String, Rule> nameAndRules)
    {
        // TODO Auto-generated method stub
        return;
    }
}
