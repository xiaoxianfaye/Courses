package fayelab.ddd.fbwreloaded.functional;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class FizzBuzzWhizz
{
    static Predicate<Integer> times(int base)
    {
        return n -> n % base == 0;
    }
    
    static Predicate<Integer> contains(int digit)
    {
        return n -> {
            int p1 = n % 10;
            int p2 = (n / 10) % 10;
            int p3 = (n / 10 / 10) % 10;
            return p1 == digit || p2 == digit || p3 == digit;
        };
    }
    
    static Predicate<Integer> alwaysTrue()
    {
        return n -> true;
    }
    
    static Function<Integer, String> toFizz()
    {
        return n -> "Fizz";
    }
    
    static Function<Integer, String> toBuzz()
    {
        return n -> "Buzz";
    }

    static Function<Integer, String> toWhizz()
    {
        return n -> "Whizz";
    }
    
    static Function<Integer, String> toStr()
    {
        return n -> String.valueOf(n);
    }
    
    static Function<Integer, Optional<String>> atom(
            Predicate<Integer> predication, Function<Integer, String> action)
    {
        return n -> {
            if(predication.test(n))
            {
                return Optional.of(action.apply(n));
            }
            
            return Optional.empty();
        };
    }
    
    static  Function<Integer, Optional<String>> or(
            Function<Integer, Optional<String>> rule1, Function<Integer, Optional<String>> rule2)
    {
        return n -> {
            Optional<String> result1 = rule1.apply(n);
            if(result1.isPresent())
            {
                return result1;
            }
            
            return rule2.apply(n);
        };
    }

    static  Function<Integer, Optional<String>> or3(Function<Integer, Optional<String>> rule1,
            Function<Integer, Optional<String>> rule2, Function<Integer, Optional<String>> rule3)
    {
        return or(rule1, or(rule2, rule3));
    }
    
    static Function<Integer, Optional<String>> or4(Function<Integer, Optional<String>> rule1,
            Function<Integer, Optional<String>> rule2, Function<Integer, Optional<String>> rule3,
            Function<Integer, Optional<String>> rule4)
    {
        return or(rule1, or3(rule2, rule3, rule4));
    }
    
    static Function<Integer, Optional<String>> and(
            Function<Integer, Optional<String>> rule1, Function<Integer, Optional<String>> rule2)
    {
        return n -> {
            Optional<String> result1 = rule1.apply(n);
            if(!result1.isPresent())
            {
                return Optional.empty();
            }
            
            Optional<String> result2 = rule2.apply(n);
            if(!result2.isPresent())
            {
                return Optional.empty();
            }
            
            return Optional.of(result1.get() + result2.get());
        };
    }
    
    static  Function<Integer, Optional<String>> and3(Function<Integer, Optional<String>> rule1,
            Function<Integer, Optional<String>> rule2, Function<Integer, Optional<String>> rule3)
    {
        return and(rule1, and(rule2, rule3));
    }
    
    static Function<Integer, Optional<String>> spec()
    {
        Function<Integer, Optional<String>> r1_3 = atom(times(3), toFizz());
        Function<Integer, Optional<String>> r1_5 = atom(times(5), toBuzz());
        Function<Integer, Optional<String>> r1_7 = atom(times(7), toWhizz());
        
        Function<Integer, Optional<String>> rd = atom(alwaysTrue(), toStr());
    
        Function<Integer, Optional<String>> r1 = or3(r1_3, r1_5, r1_7);
        Function<Integer, Optional<String>> r2 = or4(and3(r1_3, r1_5, r1_7),
                                                     and(r1_3, r1_5),
                                                     and(r1_3, r1_7),
                                                     and(r1_5, r1_7));
        Function<Integer, Optional<String>> r3 = atom(contains(3), toFizz());
        
        return or4(r3, r2, r1, rd);
    }
    
    public void run()
    {
        Function<Integer, Optional<String>> spec = spec();   
        List<Optional<String>> results = IntStream.rangeClosed(1, 100)
                                                  .mapToObj(spec::apply)
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
