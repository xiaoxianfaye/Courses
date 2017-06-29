package fayelab.ddd.gcdlcm.interpreter;

import java.util.stream.IntStream;

public class MathOp
{
    public static boolean isPrime(int n)
    {
        return IntStream.range(2, n).noneMatch(i -> n % i == 0);
    }
        
    public static int pow(int n, int power)
    {
        return IntStream.range(0, power).map(i -> n).reduce((x, y) -> x * y).orElse(1);
    }
}
