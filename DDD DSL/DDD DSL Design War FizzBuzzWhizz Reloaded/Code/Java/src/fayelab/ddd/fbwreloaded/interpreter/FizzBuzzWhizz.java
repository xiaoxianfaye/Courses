package fayelab.ddd.fbwreloaded.interpreter;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static fayelab.ddd.fbwreloaded.interpreter.Interpreter.interpret;
import static fayelab.ddd.fbwreloaded.interpreter.SpecTool.spec;

public class FizzBuzzWhizz
{
    public void run()
    {
        List<Optional<String>> results = IntStream.rangeClosed(1, 100)
                                        .mapToObj(n -> interpret(spec(), n))
                                        .collect(Collectors.toList());
        output(results);
    }
    
    private void output(List<Optional<String>> results)
    {
        results.stream().map(Optional::get).forEach(System.out::println);
    }

    public static void main(String[] args)
    {
        new FizzBuzzWhizz().run();
    }
}
