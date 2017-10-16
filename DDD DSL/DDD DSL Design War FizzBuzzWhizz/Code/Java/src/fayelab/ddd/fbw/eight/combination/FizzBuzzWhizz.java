package fayelab.ddd.fbw.eight.combination;

import static fayelab.ddd.fbw.eight.combination.SpecTool.spec;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import fayelab.ddd.fbw.eight.combination.rule.Result;

public class FizzBuzzWhizz
{
    public void run()
    {
        List<Result> results = IntStream.rangeClosed(1, 100)
                                       .mapToObj(n -> spec().apply(n))
                                       .collect(Collectors.toList());
        output(results);
    }
    
    private void output(List<Result> results)
    {
        results.stream().map(result -> result.getStr()).forEach(System.out::println);
    }

    public static void main(String[] args)
    {
        new FizzBuzzWhizz().run();
    }
}