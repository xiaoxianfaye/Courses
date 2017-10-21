package fayelab.ddd.trianglecounter.original;

import java.util.List;
import java.util.stream.Stream;

import static java.util.Arrays.asList;
import static java.util.stream.Collectors.toList;
import static fayelab.ddd.trianglecounter.original.SetOp.*;
import static fayelab.ddd.trianglecounter.original.Combinator.combinate;

public class TriangleCounter
{
    public static long count(String pointsDesc, String...lineDescs)
    {
        return count(parsePoints(pointsDesc), parseLines(lineDescs));
    }
    
    static List<Character> parsePoints(String pointsDesc)
    {
        return pointsDesc.chars().mapToObj(element -> new Character((char)element)).collect(toList());
    }
    
    static List<List<Character>> parseLines(String...lineDescs)
    {
        return Stream.of(lineDescs).map(lineDesc -> parsePoints(lineDesc)).collect(toList());
    }
    
    static boolean connected(char p1, char p2, List<List<Character>> lines)
    {
        return belong(asList(p1, p2), lines);
    }
    
    static boolean onALine(char p1, char p2, char p3, List<List<Character>> lines)
    {
        return belong(asList(p1, p2, p3), lines);
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
        List<List<Character>> allTriplePoints = combinate(points, 3);
        return allTriplePoints.stream()
                              .filter(triplePoints -> triangle(triplePoints.get(0), triplePoints.get(1),
                                                               triplePoints.get(2), lines))
                              .count();
    }
}
