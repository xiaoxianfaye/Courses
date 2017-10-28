package fayelab.ddd.fbwreloaded.complete.compiler.oo;

import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static java.util.Arrays.asList;
import static fayelab.ddd.fbwreloaded.complete.compiler.oo.SpecTool.spec;
import static fayelab.ddd.fbwreloaded.complete.compiler.oo.Compiler.compile;

public class FizzBuzzWhizz
{
    public void run()
    {
        output(runSpec(spec()));
    }
    
    public void run(String progFileName)
    {
        output(runSpec(Parser.parse(progFileName)));
    }
    
    private String runSpec(Rule spec)
    {
        return IntStream.rangeClosed(1, 100)
                        .mapToObj(n -> asList(String.valueOf(n), compile(spec).apply(n).get()))
                        .map(pair -> String.join(" -> ", pair.get(0), pair.get(1)))
                        .collect(Collectors.joining("\n"));
    }
    
    private void output(String result)
    {
        System.out.println(result);
    }

    public static void main(String[] args)
    {
        new FizzBuzzWhizz().run("prog_1.fbw");
    }
}
