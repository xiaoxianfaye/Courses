package fayelab.ddd.fbw.eight.straightforward;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import fayelab.ddd.fbw.eight.straightforward.rule.Result;
import fayelab.ddd.fbw.eight.straightforward.rule.Rule;

import static fayelab.ddd.fbw.eight.straightforward.SpecTool.spec;

public class FizzBuzzWhizz
{
    public void run()
    {
        Rule spec = spec();
        List<Result> results = IntStream.rangeClosed(1, 100)
                                        .mapToObj(spec::apply)
                                        .collect(Collectors.toList());
        output(results);
    }
    
    private void output(List<Result> results)
    {
        results.stream().map(Result::getStr).forEach(System.out::println);
    }

    public static void main(String[] args)
    {
        new FizzBuzzWhizz().run();
    }
}