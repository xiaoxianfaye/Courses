package fayelab.ddd.measure.vector.scale;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static java.util.Arrays.asList;

public class MeasureSystemUI
{
    @SuppressWarnings("unused")
    private String sysName;

    private MeasureSystem ms;
    private List<String> sysUnits;

    @SuppressWarnings("unchecked")
    public MeasureSystemUI(String sysName, String unitConversionDesc)
    {
        this.sysName = sysName;
        List<List<?>> unitConversion = parseUnitConversionDesc(unitConversionDesc);
        this.ms = new MeasureSystem((List<Integer>)unitConversion.get(0));
        this.sysUnits = (List<String>)unitConversion.get(1);
    }

    static List<List<?>> parseUnitConversionDesc(String desc)
    {
        Map<Boolean, List<String>> partitioned = partitioningByIndexParity(tokenize(desc));

        List<Integer> stepFactors = toIntegers(partitioned.get(Boolean.FALSE));
        stepFactors.add(0, Integer.MAX_VALUE);
        List<String> units = partitioned.get(Boolean.TRUE);

        return asList(stepFactors, units);
    }

    @SuppressWarnings("unchecked")
    static List<List<?>> parseUnitConversionDesc2(String desc)
    {
        List<String> tokens = tokenize(desc);

        return tokens.stream()
                     .collect(() -> asList(new ArrayList<Integer>(asList(Integer.MAX_VALUE)), new ArrayList<String>()),
                              (acc, token) -> {
                                  if(isEvenIndex(token, tokens))
                                  {
                                      ((List<String>)acc.get(1)).add(token);
                                  }
                                  else
                                  {
                                      ((List<Integer>)acc.get(0)).add(Integer.parseInt(token));
                                  }
                              },
                              (acc1, acc2) -> {
                                  ((List<Integer>)acc1.get(0)).addAll((List<Integer>)acc2.get(0));
                                  ((List<String>)acc1.get(1)).addAll((List<String>)acc2.get(1));
                              });
    }

    List<Integer> parseQuantityDesc(String desc)
    {
        Map<Boolean, List<String>> partitioned = partitioningByIndexParity(tokenize(desc));

        List<Integer> values = toIntegers(partitioned.get(Boolean.TRUE));
        List<String> units = partitioned.get(Boolean.FALSE);

        return sysUnits.stream()
                       .map(sysUnit -> units.contains(sysUnit) ? values.get(units.indexOf(sysUnit)) : 0)
                       .collect(Collectors.toList());
    }

    public boolean equal(String quantityDesc1, String quantityDesc2)
    {
        return ms.equal(parseQuantityDesc(quantityDesc1), parseQuantityDesc(quantityDesc2));
    }

    public String format(List<Integer> quantityVec)
    {
        return IntStream.range(0, quantityVec.size())
                        .filter(i -> quantityVec.get(i) != 0 || i == quantityVec.size() - 1)
                        .mapToObj(i -> String.format("%d %s", quantityVec.get(i), sysUnits.get(i)))
                        .reduce((acc, valueAndUnit) -> String.join(" ", acc, valueAndUnit))
                        .orElse("");
    }

    public String add(String quantityDesc1, String quantityDesc2)
    {
        return format(ms.add(parseQuantityDesc(quantityDesc1), parseQuantityDesc(quantityDesc2)));
    }

    public String baseFormat(List<Integer> quantityVec)
    {
        return String.format("%d %s", ms.base(quantityVec), sysUnits.get(sysUnits.size() - 1));
    }

    public String scale(String c, String quantityDesc)
    {
        return format(ms.scale(Integer.parseInt(c), parseQuantityDesc(quantityDesc)));
    }

    private static List<String> tokenize(String desc)
    {
        return asList(desc.trim().split(" +"));
    }

    private static Map<Boolean, List<String>> partitioningByIndexParity(List<String> tokens)
    {
        return tokens.stream().collect(Collectors.partitioningBy(token -> isEvenIndex(token, tokens)));
    }

    private static boolean isEvenIndex(String token, List<String> tokens)
    {
        return tokens.indexOf(token) % 2 == 0;
    }

    private static List<Integer> toIntegers(List<String> strs)
    {
        return strs.stream().map(Integer::parseInt).collect(Collectors.toList());
    }

    public static void main(String[] args)
    {
        MeasureSystemUI impLen = new MeasureSystemUI("Imperial Length", "Mile 1760 Yard 3 Feet 12 Inch");
        System.out.println("1765 Yard 40 Inch == 1 Mile 6 Yard 4 Inch ? " + impLen.equal("1765 Yard 40 Inch", "1 Mile 6 Yard 4 Inch"));
        System.out.println("13 Inch + 11 Inch = " + impLen.add("13 Inch", "11 Inch"));

        MeasureSystemUI impVol = new MeasureSystemUI("Imperial Volume", "OZ 2 TBSP 3 TSP");
        System.out.println("1 OZ 10 TSP == 2 OZ 1 TBSP 1 TSP ? " + impVol.equal("1 OZ 10 TSP", "2 OZ 1 TBSP 1 TSP"));
        System.out.println("1 OZ + 3 TBSP 3 TSP = " + impVol.add("1 OZ", "3 TBSP 3 TSP"));

        MeasureSystemUI time = new MeasureSystemUI("Time", "Day 24 Hour 60 Minute 60 Second");
        System.out.println("7 Hour 59 Minute 60 Second == 8 Hour ? " + time.equal("7 Hour 59 Minute 60 Second", "8 Hour"));
        System.out.println("1 Day 23 Hour 59 Minute 59 Second + 1 Second = " + time.add("1 Day 23 Hour 59 Minute 59 Second", "1 Second"));

        MeasureSystemUI impLen2 = new MeasureSystemUI("Imperial Length 2", "Mile 4 Miya 440 Yard 3 Feet 12 Inch");
        System.out.println("4 Miya 446 Yard 40 Inch == 1 Mile 1 Miya 7 Yard 4 Inch ? " + impLen2.equal("4 Miya 446 Yard 40 Inch", "1 Mile 1 Miya 7 Yard 4 Inch"));
        System.out.println("3 Miya 13 Inch + 445 Yard 11 Inch = " + impLen2.add("3 Miya 13 Inch", "445 Yard 11 Inch"));

        System.out.println("2 * 2 Yard 3 Feet = " + impLen.scale("2", "2 Yard 3 Feet"));
        System.out.println("2 * 2 Yard 3 Feet + 13 Inch = " + impLen.add(impLen.scale("2", "2 Yard 3 Feet"), "13 Inch"));

        System.out.println("3 * 1 OZ 10 TSP = " + impVol.scale("3", "1 OZ 10 TSP"));
        System.out.println("3 * 1 OZ 10 TSP + 3 TBSP 1 TSP = " + impVol.add(impVol.scale("3", "1 OZ 10 TSP"), "3 TBSP 1 TSP"));
    }
}
