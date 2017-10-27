package fayelab.ddd.fbwreloaded.complete.interpreter;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static java.util.Arrays.asList;
import static fayelab.ddd.fbwreloaded.interpreter.Interpreter.interpret;
import static fayelab.ddd.fbwreloaded.interpreter.SpecTool.spec;

public class FizzBuzzWhizz
{
    public void run()
    {
//        output(runSpec(spec()));
    }
    
    public void run(String progFileName)
    {
        runSpec(Parser.parse(progFileName));
    }
    
    private static void runSpec(Rule spec)
    {
        IntStream.rangeClosed(1, 100)
        .mapToObj(n -> asList(String.valueOf(n), (String)interpret(spec, n).get()));
        
//        asList(String.valueOf(n), interpret(spec, n).get())

        
//        .map(pair -> String.join(" -> ", pair.get(0), pair.get(1)))
//        .collect(Collectors.joining("\n"));
//        System.out.println(result);
    }
    
    private static Stream<List<String>> apply(Rule spec, IntStream numbers)
    {
        return numbers.mapToObj(number -> asList(String.valueOf(number), Interpreter.interpret(spec, number).get()));
    }
    
    
//    private List<String> runSpec(Rule spec)
//    {
//        return IntStream.rangeClosed(1, 100)
//                        .mapToObj(n -> interpret(spec, n).get())
//                        .collect(Collectors.toList());
//    }
    
    private void output(List<String> results)
    {
        results.stream().forEach(System.out::println);
    }

    public static void main(String[] args)
    {
        new FizzBuzzWhizz().run();
    }
}
