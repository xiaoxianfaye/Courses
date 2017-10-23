package fayelab.ddd.fbwreloaded.functional;

import java.util.function.Predicate;

public class FizzBuzzWhizz
{
    public static Predicate<Integer> times(int base)
    {
        return n -> n % base == 0;
    }
}
