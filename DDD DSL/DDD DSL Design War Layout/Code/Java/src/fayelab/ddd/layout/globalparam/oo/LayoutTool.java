package fayelab.ddd.layout.globalparam.oo;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import fayelab.ddd.layout.globalparam.oo.component.Button;
import fayelab.ddd.layout.globalparam.oo.component.Component;
import fayelab.ddd.layout.globalparam.oo.component.Empty;
import fayelab.ddd.layout.globalparam.oo.component.Label;
import fayelab.ddd.layout.globalparam.oo.component.TextField;
import fayelab.ddd.layout.globalparam.oo.position.Above;
import fayelab.ddd.layout.globalparam.oo.position.Beside;

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
    
    public static Label label()
    {
        return new Label();
    }
    
    public static Component beside(Component leftCmp, Component rightCmp, float ratio)
    {
        return new Beside(leftCmp, rightCmp, ratio);
    }

    public static Component above(Component upCmp, Component downCmp, float ratio)
    {
        return new Above(upCmp, downCmp, ratio);
    }
    
    public static Component empty()
    {
        return new Empty();
    }
    
    @FunctionalInterface
    private interface PosLayout
    {
        Component apply(Component cmp1, Component cmp2, float ratio);
    }
    
    public static Component hCenter(Component cmp, float ratio)
    {
        return xCenter(LayoutTool::beside, cmp, ratio);
    }
    
    public static Component vCenter(Component cmp, float ratio)
    {
        return xCenter(LayoutTool::above, cmp, ratio);
    }
    
    private static Component xCenter(PosLayout posLayout, Component cmp, float ratio)
    {
        return posLayout.apply(empty(), posLayout.apply(cmp, empty(), (1 - 2 * ratio) / (1 - ratio)), ratio);
    }
    
    public static Component center(Component cmp, float hRatio, float vRatio)
    {
        return vCenter(hCenter(cmp, hRatio), vRatio);
    }
    
    public static Component hSeq(Component...cmps)
    {
        return seq(LayoutTool::beside, cmps);
    }
    
    public static Component vSeq(Component...cmps)
    {
        return seq(LayoutTool::above, cmps);
    }
    
    private static Component seq(PosLayout posLayout, Component...cmps)
    {
        if(cmps.length == 1)
        {
            return cmps[0];
        }
        
        return posLayout.apply(cmps[0], 
                seq(posLayout, Arrays.copyOfRange(cmps, 1, cmps.length)), 1.0f / cmps.length);
    }
    
    public static Component block(Component[] cmps, int rowNum, int colNum)
    {
        Collection<List<Component>> normalizedCmpList = normalize(rowNum, colNum, cmps);
        Component[] rows = normalizedCmpList.stream()
                                            .map(rowCmps -> hSeq(rowCmps.toArray(new Component[]{})))
                                            .toArray(Component[]::new);
        return vSeq(rows);
    }
    
    public static Component blockm(Component[] cmps, int rowNum, int colNum, 
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
