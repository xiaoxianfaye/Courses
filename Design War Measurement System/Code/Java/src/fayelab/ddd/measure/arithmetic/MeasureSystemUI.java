package fayelab.ddd.measure.arithmetic;

import static java.util.stream.Collectors.toList;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;
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
        this.sysUnits = (List<String>)unitConversion.get(1);
        this.ms = new MeasureSystem((List<Integer>)unitConversion.get(0));
    }

    private List<List<?>> parseUnitConversionDesc(String desc)
    {
        validateUnitConversionDesc(desc);

        String[] tokens = desc.trim().split(" +");
        List<Integer> stepFactors = new ArrayList<>();
        stepFactors.add(Integer.MAX_VALUE);
        List<String> sysUnits = new ArrayList<>();
        sysUnits.add(tokens[0]);
        
        for(int i = 1; i < tokens.length; i+=2)
        {
            stepFactors.add(Integer.parseInt(tokens[i]));
            sysUnits.add(tokens[i + 1]);
        }
        
        return asList(stepFactors, sysUnits);
    }

    private void validateUnitConversionDesc(String desc)
    {
        if(desc == null)
        {
            throw new IllegalParametersException("The unit conversion description is null.");
        }
        
        if(!Pattern.matches("\\s*([a-zA-Z]+\\s+[0-9]+\\s+)+[a-zA-Z]+\\s*", desc))
        {
            throw new IllegalParametersException("The unit conversion description is illegal.");
        }
    }

    @SuppressWarnings("unchecked")
    List<Integer> parseQuantity(String quantity)
    {
        validateQuantity(quantity);
        
        List<List<?>> valuesAndUnits = parseValuesAndUnits(quantity);

        List<Integer> values = (List<Integer>)valuesAndUnits.get(0);
        List<Integer> units = (List<Integer>)valuesAndUnits.get(1);
        
        return sysUnits.stream()
                       .map(sysUnit -> units.contains(sysUnit) ? 
                               values.get(units.indexOf(sysUnit)) : 0)
                       .collect(toList());
    }

    void validateQuantity(String quantity)
    {
        if(quantity == null)
        {
            throw new IllegalParametersException("The quantity is null.");
        }
        
        if(!Pattern.matches("\\s*[0-9]+\\s+([a-zA-Z]+\\s+[0-9]+\\s+)*[a-zA-Z]+\\s*", quantity))
        {
            throw new IllegalParametersException("The quantity is illegal.");
        }
    }

    private List<List<?>> parseValuesAndUnits(String quantity)
    {
        String[] tokens = quantity.trim().split(" +");
        List<Integer> values = new ArrayList<>();
        List<String> units = new ArrayList<>();
        
        for(int i = 0; i < tokens.length; i += 2)
        {
            values.add(Integer.parseInt(tokens[i]));
            if(isIllegalUnit(tokens[i + 1]))
            {
                throw new IllegalParametersException("The unit of the quantity is illegal.");
            }
            units.add(tokens[i + 1]);
        }
        
        return asList(values, units);
    }

    private boolean isIllegalUnit(String unit)
    {
        return !sysUnits.contains(unit);
    }

    public boolean equal(String quantity1, String quantity2)
    {
        return ms.equal(parseQuantity(quantity1), parseQuantity(quantity2));
    }

    public String format(List<Integer> quantityVec)
    {
        final String[] result = new String[]{""};
        IntStream.range(0, quantityVec.size())
                 .collect(() -> result,
                          (acc, idx) -> {
                              int value = quantityVec.get(idx);
                              if(value != 0 || idx == quantityVec.size() - 1)
                              {
                                  String valueAndUnit = String.join(" ", String.valueOf(value), sysUnits.get(idx));
                                  acc[0] = String.join(" ", acc[0], valueAndUnit);
                              }
                          },
                          (acc1, acc2) -> String.join(" ", acc1[0], acc2[0])
                         );
        
        return result[0].substring(1);
    }

    public String add(String quantity1, String quantity2)
    {
        return format(ms.add(parseQuantity(quantity1), parseQuantity(quantity2)));
    }

    public String baseFormat(List<Integer> quantityVec)
    {
        int basedValue = ms.base(quantityVec);
        return String.join(" ", String.valueOf(basedValue), sysUnits.get(sysUnits.size() - 1));
    }

    public String scale(String c, String quantity)
    {
        return format(ms.scale(Integer.parseInt(c), parseQuantity(quantity)));
    }
    
    List<Integer> getStepFactors()
    {
        return this.ms.getStepFactors();
    }
    
    List<String> getSysUnits()
    {
        return this.sysUnits;
    }
    
    public static void main(String[] args)
    {
        MeasureSystemUI impLen = new MeasureSystemUI("Imperial Length", "Mile 1760 Yard 3 Feet 12 Inch");
        System.out.println("1765 Yard 40 Inch == 1 Mile 6 Yard 4 Inch ? " + impLen.equal("1765 Yard 40 Inch", "1 Mile 6 Yard 4 Inch"));
        System.out.println("13 Inch + 11 Inch = " + impLen.add("13 Inch", "11 Inch"));
        System.out.println("2 * 2 Yard 3 Feet = " + impLen.scale("2", "2 Yard 3 Feet"));
        System.out.println("2 * 2 Yard 3 Feet + 13 Inch = " + impLen.add(impLen.scale("2", "2 Yard 3 Feet"), "13 Inch"));
        
        MeasureSystemUI impVol = new MeasureSystemUI("Imperial Volume", "OZ 2 TBSP 3 TSP");
        System.out.println("1 OZ 10 TSP == 2 OZ 1 TBSP 1 TSP ? " + impVol.equal("1 OZ 10 TSP", "2 OZ 1 TBSP 1 TSP"));
        System.out.println("1 OZ + 3 TBSP 3 TSP = " + impVol.add("1 OZ", "3 TBSP 3 TSP"));
        System.out.println("3 * 1 OZ 10 TSP = " + impVol.scale("3", "1 OZ 10 TSP"));
        System.out.println("3 * 1 OZ 10 TSP + 3 TBSP 1 TSP = " + impVol.add(impVol.scale("3", "1 OZ 10 TSP"), "3 TBSP 1 TSP"));
        
        MeasureSystemUI time = new MeasureSystemUI("Time", "Day 24 Hour 60 Minute 60 Second");
        System.out.println("7 Hour 59 Minute 60 Second == 8 Hour ? " + time.equal("7 Hour 59 Minute 60 Second", "8 Hour"));
        System.out.println("1 Day 23 Hour 59 Minute 59 Second + 1 Second = " + time.add("1 Day 23 Hour 59 Minute 59 Second", "1 Second"));
        System.out.println("2 * 11 Hour 22 Minute 33 Second = " + time.scale("2", "11 Hour 22 Minute 33 Second"));
        System.out.println("2 * 11 Hour 22 Minute 33 Second + 60 Second = " + time.add(time.scale("2", "11 Hour 22 Minute 33 Second"), "60 Second"));
        
        MeasureSystemUI impLen2 = new MeasureSystemUI("Imperial Length 2", "Mile 4 Miya 440 Yard 3 Feet 12 Inch");
        System.out.println("4 Miya 446 Yard 40 Inch == 1 Mile 1 Miya 7 Yard 4 Inch ? " + impLen2.equal("4 Miya 446 Yard 40 Inch", "1 Mile 1 Miya 7 Yard 4 Inch"));
        System.out.println("3 Miya 13 Inch + 445 Yard 11 Inch = " + impLen2.add("3 Miya 13 Inch", "445 Yard 11 Inch"));
        System.out.println("2 * 3 Miya 13 Inch + 445 Yard 11 Inch = " + impLen2.add(impLen2.scale("2", "3 Miya 13 Inch"), "445 Yard 11 Inch"));
    }
}
