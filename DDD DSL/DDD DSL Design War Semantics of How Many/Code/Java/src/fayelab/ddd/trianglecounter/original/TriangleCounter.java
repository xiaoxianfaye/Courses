package fayelab.ddd.trianglecounter.original;

import java.util.List;
import java.util.stream.Stream;

import static java.util.Arrays.asList;
import static java.util.stream.Collectors.toList;
import static fayelab.ddd.trianglecounter.original.SetOp.subset;
import static fayelab.ddd.trianglecounter.original.Combinator.combinate;

public class TriangleCounter
{
    public static long count(String pointsDesc, String...lineDescs)
    {
        return count(parsePoints(pointsDesc), parseLines(lineDescs));
    }
    
    static List<Character> parsePoints(String pointsDesc)
    {
        return pointsDesc.chars().mapToObj(p -> (char)p).collect(toList());
    }
    
    static List<List<Character>> parseLines(String...lineDescs)
    {
        return Stream.of(lineDescs).map(TriangleCounter::parsePoints).collect(toList());
    }
    
    static boolean belong(List<List<Character>> lines, Character...points)
    {
        return lines.stream().anyMatch(line -> subset(asList(points), line));
    }
    
    static boolean connected(char p1, char p2, List<List<Character>> lines)
    {
        return belong(lines, p1, p2);
    }
    
    static boolean onALine(char p1, char p2, char p3, List<List<Character>> lines)
    {
        return belong(lines, p1, p2, p3);
    }
    
    static boolean triangle(char p1, char p2, char p3, List<List<Character>> lines)
    {
        return connected(p1, p2, lines) 
            && connected(p1, p3, lines)
            && connected(p2, p3, lines)
            && (!onALine(p1, p2, p3, lines));
    }
    
    static long count(List<Character> points, List<List<Character>> lines)
    {
        return combinate(points, 3).stream()
                                   .filter(triplePoints -> triangle(triplePoints.get(0), triplePoints.get(1),
                                                                    triplePoints.get(2), lines))
                                   .count();
    }
}
