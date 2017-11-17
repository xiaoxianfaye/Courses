package fayelab.ddd.layout.original;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import fayelab.ddd.layout.original.component.Button;
import fayelab.ddd.layout.original.component.Component;
import fayelab.ddd.layout.original.component.Empty;
import fayelab.ddd.layout.original.component.TextField;
import fayelab.ddd.layout.original.position.Above;
import fayelab.ddd.layout.original.position.Beside;

import static java.util.stream.Collectors.toList;

import java.util.Arrays;

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
        return seq(LayoutTool::beside, cmps);
    }
    
    public static Component vSeq(Component...cmps)
    {
        return seq(LayoutTool::above, cmps);
    }
    
    private static Component seq(PositionLayout positionLayout, Component...cmps)
    {
        if(cmps.length == 1)
        {
            return cmps[0];
        }
        
        return positionLayout.apply(cmps[0], 
                seq(positionLayout, Arrays.copyOfRange(cmps, 1, cmps.length)), 1.0f / cmps.length);
    }
    
    @FunctionalInterface
    interface PositionLayout
    {
        Component apply(Component cmp1, Component cmp2, float ratio);
    }
    
    public static Component block(Component[] cmps, int rowNum, int colNum)
    {
        Collection<List<Component>> normalizedCmpList = normalize(rowNum, colNum, cmps);
        Component[] rows = normalizedCmpList.stream()
                                            .map(rowCmps -> hSeq(rowCmps.toArray(new Component[]{})))
                                            .toArray(Component[]::new);
        return vSeq(rows);
    }
    
    public static Component blockWithMargin(Component[] cmps, int rowNum, int colNum, 
            float hRatio, float vRatio)
    {
        return block(Stream.of(cmps)
                           .map(cmp -> center(cmp, hRatio, vRatio))
                           .toArray(Component[]::new), 
                     rowNum, 
                     colNum);
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
        int paddingNum = rowNum * colNum - cmps.length;
        return Stream.concat(Stream.of(cmps), Collections.nCopies(paddingNum, empty()).stream())
                     .collect(toList());
    }
}
