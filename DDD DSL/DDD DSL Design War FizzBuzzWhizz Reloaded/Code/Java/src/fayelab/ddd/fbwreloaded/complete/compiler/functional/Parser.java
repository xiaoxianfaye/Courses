package fayelab.ddd.fbwreloaded.complete.compiler.functional;

import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.BiFunction;
import java.util.function.Supplier;

import static fayelab.ddd.fbwreloaded.complete.compiler.functional.SpecTool.*;
import static java.util.Arrays.asList;
import static java.util.stream.Collectors.toList;

public class Parser
{
    private static final String DELIMETER = " ";
    
    private static Map<String, BiFunction<String, String, Predication>> predicationTypeAndFuncMap = null;
    
    static
    {
        predicationTypeAndFuncMap = new HashMap<>();
        predicationTypeAndFuncMap.put("times", (type, param) -> times(Integer.parseInt(param)));
        predicationTypeAndFuncMap.put("contains", (type, param) -> contains(Integer.parseInt(param)));
        predicationTypeAndFuncMap.put("always_true", (type, param) -> alwaysTrue());
    }
    
    private static Map<String, Supplier<Action>> actionTypeAndFuncMap = null;
    
    static
    {
        actionTypeAndFuncMap = new HashMap<>();
        actionTypeAndFuncMap.put("to_fizz", () -> toFizz());
        actionTypeAndFuncMap.put("to_buzz", () -> toBuzz());
        actionTypeAndFuncMap.put("to_whizz", () -> toWhizz());
        actionTypeAndFuncMap.put("to_str", () -> toStr());
    }
    
    private static Map<String, BiFunction<List<String>, Map<String, Rule>, Rule>> ruleTypeAndFuncMap = null;
    
    static
    {
        ruleTypeAndFuncMap = new HashMap<>();
        ruleTypeAndFuncMap.put("atom", (ruleTokens, ruleNameAndRuleMap) -> parseAtom(ruleTokens));
        ruleTypeAndFuncMap.put("or", (ruleTokens, ruleNameAndRuleMap) -> parseOr(ruleTokens, ruleNameAndRuleMap));
        ruleTypeAndFuncMap.put("and", (ruleTokens, ruleNameAndRuleMap) -> parseAnd(ruleTokens, ruleNameAndRuleMap));
    }
    
    public static Rule parse(String progFileName)
    {
        Rule rule = null;

        try
        {
            Path progFilePath = FileSystems.getDefault().getPath("scripts", progFileName);
            rule = parse(Files.readAllLines(progFilePath));
        }
        catch(IOException e)
        {
            e.printStackTrace();
        }

        return rule;
    }
    
    static Rule parse(List<String> ruleDescs)
    {
        return parseTokens(tokenize(ruleDescs));
    }
    
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
        Map<String, Rule> ruleNameAndRules = new HashMap<>();
        tokens.stream().forEach(ruleTokens -> parseRuleTokens(ruleTokens, ruleNameAndRules));
        return ruleNameAndRules.get("spec");
    }

    static Rule parseAtom(List<String> ruleTokens)
    {
        String predicationType = extractPredicationType(ruleTokens);
        String predicationParam = extractPredicationParam(ruleTokens, predicationType);
        String actionType = extractActionType(ruleTokens, predicationType);

        return atom(parsePredication(predicationType, predicationParam), parseAction(actionType));
    }
    
    private static String extractPredicationType(List<String> ruleTokens)
    {
        return ruleTokens.get(2);
    }

    private static String extractPredicationParam(List<String> ruleTokens, String predicationType)
    {
        return "always_true".equals(predicationType) ? null : ruleTokens.get(3);
    }

    private static String extractActionType(List<String> ruleTokens, String predicationType)
    {
        return "always_true".equals(predicationType) ? ruleTokens.get(3) : ruleTokens.get(4);
    }
    
    private static Predication parsePredication(String predicationType, String predicationParam)
    {
        return predicationTypeAndFuncMap.get(predicationType).apply(predicationType, predicationParam);
    }

    private static Action parseAction(String actionType)
    {
        return actionTypeAndFuncMap.get(actionType).get();
    }

    static Rule parseOr(List<String> ruleTokens, Map<String, Rule> ruleNameAndRuleMap)
    {
        return or(parseRefRules(ruleTokens, ruleNameAndRuleMap));
    }
    
    static Rule parseAnd(List<String> ruleTokens, Map<String, Rule> ruleNameAndRuleMap)
    {
        return and(parseRefRules(ruleTokens, ruleNameAndRuleMap));
    }

    private static List<Rule> parseRefRules(List<String> ruleTokens, Map<String, Rule> ruleNameAndRuleMap)
    {
        List<Rule> refRules = ruleTokens.subList(2, ruleTokens.size())
                                        .stream()
                                        .map(name -> ruleNameAndRuleMap.get(name)).collect(toList());
        return refRules;
    }
    
    static void parseRuleTokens(List<String> ruleTokens, Map<String, Rule> ruleNameAndRuleMap)
    {
        String ruleName = extractRuleName(ruleTokens);
        String ruleType = extractRuleType(ruleTokens);
        
        Rule rule = ruleTypeAndFuncMap.get(ruleType).apply(ruleTokens, ruleNameAndRuleMap);
        ruleNameAndRuleMap.putIfAbsent(ruleName, rule);
    }

    private static String extractRuleName(List<String> ruleTokens)
    {
        return ruleTokens.get(0);
    }

    private static String extractRuleType(List<String> ruleTokens)
    {
        return ruleTokens.get(1);
    }
    
    public static void main(String[] args)
    {
        String expected = "{or, {atom, contains_3, to_fizz}, "
                             + "{or, {or, {and, {atom, times_3, to_fizz}, "
                                             + "{and, {atom, times_5, to_buzz}, "
                                                   + "{atom, times_7, to_whizz}}}, "
                                       + "{or, {and, {atom, times_3, to_fizz}, "
                                                  + "{atom, times_5, to_buzz}}, "
                                            + "{or, {and, {atom, times_3, to_fizz}, "
                                                       + "{atom, times_7, to_whizz}}, "
                                                 + "{and, {atom, times_5, to_buzz}, "
                                                       + "{atom, times_7, to_whizz}}}}}, "
                                  + "{or, {or, {atom, times_3, to_fizz}, "
                                            + "{or, {atom, times_5, to_buzz}, "
                                                 + "{atom, times_7, to_whizz}}}, "
                                       + "{atom, always_true, to_str}}}}";
        String actual = RuleDescTool.desc(parse("prog_1.fbw"));
        
        System.out.println(expected.equals(actual));
        System.out.println(actual);
    }
}
