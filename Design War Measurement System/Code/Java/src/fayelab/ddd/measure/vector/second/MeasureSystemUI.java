package fayelab.ddd.measure.vector.second;

import static java.util.stream.Collectors.toList;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class MeasureSystemUI
{
    @SuppressWarnings("unused")
    private String sysName;
    private MeasureSystem ms;
    private List<String> sysUnits;

    public MeasureSystemUI(String sysName, String unitConversionDesc)
    {
        this.sysName = sysName;
        UnitConversion unitConversion = parseUnitConversionDesc(unitConversionDesc);
        this.sysUnits = unitConversion.getSysUnits();
        this.ms = new MeasureSystem(unitConversion.getStepFactors());
    }

    private UnitConversion parseUnitConversionDesc(String desc)
    {
        validateConversionDesc(desc);
                
        String[] tokens = desc.trim().split(" +");
        List<String> parsedSysUnits = parseSysUnits(tokens);
        List<Integer> parsedStepFactors = parseStepFactors(tokens);

        return new UnitConversion(parsedStepFactors, parsedSysUnits);
    }

    private void validateConversionDesc(String desc)
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

    private List<String> parseSysUnits(String[] tokens)
    {
        List<String> result = new ArrayList<>();
        result.add(tokens[0]);
        for(int i = 1; i < tokens.length; i += 2)
        {
            result.add(tokens[i + 1]);
        }
        return result;
    }

    private List<Integer> parseStepFactors(String[] tokens)
    {
        List<Integer> result = new ArrayList<>();
        result.add(Integer.MAX_VALUE);
        for(int i = 1; i < tokens.length; i += 2)
        {
            result.add(Integer.parseInt(tokens[i]));
        }
        return result;
    }

    List<Integer> parseQuantity(String quantity)
    {
        validateQuantity(quantity);
        
        String[] tokens = quantity.trim().split(" +");
        List<Integer> values = parseQuantityValues(tokens);
        List<String> units = parseQuantityUnits(tokens);
        return makeQuantityVecFrom(values, units);
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

    private List<Integer> parseQuantityValues(String[] tokens)
    {
        List<Integer> result = new ArrayList<>();
        for(int i = 0; i < tokens.length; i += 2)
        {
            result.add(Integer.parseInt(tokens[i]));
        }
        return result;
    }

    private List<String> parseQuantityUnits(String[] tokens)
    {
        List<String> result = new ArrayList<>();
        for(int i = 1; i < tokens.length; i += 2)
        {
            if(isIllegalUnit(tokens[i]))
            {
                throw new IllegalParametersException("The unit of the quantity is illegal.");
            }
            result.add(tokens[i]);
        }
        return result;
    }

    private boolean isIllegalUnit(String unit)
    {
        return !sysUnits.contains(unit);
    }

    private List<Integer> makeQuantityVecFrom(List<Integer> values, List<String> units)
    {
        return sysUnits.stream()
                       .map(sysUnit -> units.contains(sysUnit) ? 
                                           values.get(units.indexOf(sysUnit)) : 0)
                       .collect(toList());
    }

    public boolean equal(String quantity1, String quantity2)
    {
        return ms.equal(parseQuantity(quantity1), parseQuantity(quantity2));
    }

    public String format(List<Integer> quantityVec)
    {
        String result = "";
        for(int i = 0; i < quantityVec.size(); i++)
        {
            int value = quantityVec.get(i);
            if(value != 0 || i == quantityVec.size() - 1)
            {
                String valueAndUnit = String.join(" ", String.valueOf(value), sysUnits.get(i));
                result = String.join(" ", result, valueAndUnit);
            }
        }
       
        return result.substring(1, result.length());
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

class UnitConversion
{
    private List<Integer> stepFactors;
    private List<String> sysUnits;
    
    public UnitConversion(List<Integer> stepFactors, List<String> sysUnits)
    {
        this.stepFactors = stepFactors;
        this.sysUnits = sysUnits;
    }
    public List<Integer> getStepFactors()
    {
        return stepFactors;
    }

    public List<String> getSysUnits()
    {
        return sysUnits;
    }
}