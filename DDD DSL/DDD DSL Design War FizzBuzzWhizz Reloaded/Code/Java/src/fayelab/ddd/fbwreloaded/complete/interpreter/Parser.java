package fayelab.ddd.fbwreloaded.complete.interpreter;

import static java.util.Arrays.asList;

import java.util.List;

public class Parser
{
    private static final String DELIMETER = " ";

    static boolean isNotBlank(String line)
    {
        return !line.trim().isEmpty();
    }
    
    static String normalize(String line)
    {
        return line.trim().replaceAll(" +", DELIMETER);
    }
    
    static List<String> tokenize(String normalizedLine)
    {        
        return asList(normalizedLine.split(DELIMETER));
    }
    
//    static void parseRuleTokens(List<String> ruleTokens, Map<String, Rule> nameMappingRule)
//    {
//        
//    }
}
