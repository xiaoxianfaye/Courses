package fayelab.ddd.fbwreloaded.complete.interpreter;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static fayelab.ddd.fbwreloaded.interpreter.Interpreter.interpret;
import static fayelab.ddd.fbwreloaded.interpreter.SpecTool.spec;

public class FizzBuzzWhizz
{
    public void run()
    {
        List<String> results = IntStream.rangeClosed(1, 100)
                                        .mapToObj(n -> interpret(spec(), n).get())
                                        .collect(Collectors.toList());
        output(results);
    }
    
    private void output(List<String> results)
    {
        results.stream().forEach(System.out::println);
    }

    public static void main(String[] args)
    {
        new FizzBuzzWhizz().run();
    }
}
