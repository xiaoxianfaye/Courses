package fayelab.ddd.layout.globalparamconf;

import java.util.Collection;
import java.util.List;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import fayelab.ddd.layout.globalparamconf.component.Label;
import fayelab.ddd.layout.globalparamconf.component.Button;
import fayelab.ddd.layout.globalparamconf.component.Component;
import fayelab.ddd.layout.globalparamconf.component.Empty;
import fayelab.ddd.layout.globalparamconf.component.TextField;
import fayelab.ddd.layout.globalparamconf.position.Above;
import fayelab.ddd.layout.globalparamconf.position.Beside;

import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.mapping;
import static java.util.Arrays.asList;

public class LayoutTool
{
    public static Button button()
    {
        return new Button();
    }

    public static TextField textField()
    {
        return new TextField();
    }
    
    public static Label label()
    {
        return new Label();
    }
    
    public static Component beside(Component left, Component right, float ratio)
    {
        return new Beside(left, right, ratio);
    }

    public static Component above(Component up, Component down, float ratio)
    {
        return new Above(up, down, ratio);
    }
    
    public static Component empty()
    {
        return new Empty();
    }
    
    public static Component center(Component cmp, float hRatio, float vRatio)
    {
        return beside(empty(), 
                      beside(above(empty(), 
                                   above(cmp,
                                         empty(),
                                         (1 - 2 * vRatio) / (1 - vRatio)), 
                                   vRatio),
                             empty(),
                             (1 - 2 * hRatio) / (1 - hRatio)), 
                      hRatio);
    }
    
    public static Component hSeq(Component...cmps)
    {
        return seq((cmp1, cmp2, ratio) -> beside(cmp1, cmp2, ratio), cmps);
    }
    
    public static Component vSeq(Component...cmps)
    {
        return seq((cmp1, cmp2, ratio) -> above(cmp1, cmp2, ratio), cmps);
    }
    
    private static Component seq(PositionLayout positionLayout, Component...cmps)
    {
        List<Component> cmpList = list(cmps);
        
        if(cmpList.size() == 1)
        {
            return cmpList.get(0);
        }
        
        float ratio = 1.0f / cmpList.size();
        Component firstCmp = cmpList.remove(0);
        return positionLayout.apply(firstCmp, seq(positionLayout, array(cmpList)), ratio);
    }
    
    @FunctionalInterface
    interface PositionLayout
    {
        Component apply(Component cmp1, Component cmp2, float ratio);
    }
    
    public static Component block(Component[] cmps, int rowNum, int colNum)
    {
        Collection<List<Component>> normalizedCmpList = normalize(rowNum, colNum, cmps);
        List<Component> rows = normalizedCmpList.stream().map(rowCmps -> hSeq(array(rowCmps))).collect(toList());
        return vSeq(array(rows));
    }
    
    public static Component blockWithMargin(Component[] cmps, int rowNum, int colNum, float hRatio, float vRatio)
    {
        List<Component> cmpList = list(cmps);
        
        List<Component> centeredCmpList = cmpList.stream().map(cmp -> center(cmp, hRatio, vRatio)).collect(toList());
        
        return block(array(centeredCmpList), rowNum, colNum);
    }

    private static Collection<List<Component>> normalize(int rowNum, int colNum, Component[] cmps)
    {
        List<Component> cmpList = padding(rowNum, colNum, cmps);
      
        return IntStream.range(0, cmpList.size())
                       .mapToObj(idx -> asList(idx, cmpList.get(idx)))
                       .collect(groupingBy(idxAndCmp -> (Integer)idxAndCmp.get(0) / colNum, 
                                mapping(idxAndCmp -> (Component)idxAndCmp.get(1), toList())))
                       .values();
    }
  
    private static List<Component> padding(int rowNum, int colNum, Component[] cmps)
    {
        List<Component> cmpList = list(cmps);
      
        int paddingNum = rowNum * colNum - cmpList.size();
        IntStream.range(0, paddingNum).forEach(i -> cmpList.add(empty()));
        return cmpList;
    }
    
    private static List<Component> list(Component[] cmps)
    {
        return Stream.of(cmps).collect(toList());
    }
    
    private static Component[] array(List<Component> cmps)
    {
        return cmps.toArray(new Component[]{});
    }
}
