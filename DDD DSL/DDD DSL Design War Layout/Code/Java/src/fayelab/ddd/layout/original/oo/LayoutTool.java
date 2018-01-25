package fayelab.ddd.layout.original.oo;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import fayelab.ddd.layout.original.oo.component.Button;
import fayelab.ddd.layout.original.oo.component.Component;
import fayelab.ddd.layout.original.oo.component.Empty;
import fayelab.ddd.layout.original.oo.component.TextField;
import fayelab.ddd.layout.original.oo.position.Above;
import fayelab.ddd.layout.original.oo.position.Beside;

import static java.util.stream.Collectors.*;
import static java.util.Arrays.asList;

public class LayoutTool
{
    public static Button button(String text)
    {
        return new Button(text);
    }

    public static TextField textField()
    {
        return new TextField();
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
    
    public static Component hSeq(List<Component> cmps)
    {
        return seq(LayoutTool::beside, cmps);
    }
    
    public static Component vSeq(List<Component> cmps)
    {
        return seq(LayoutTool::above, cmps);
    }
    
    private static Component seq(PosLayout posLayout, List<Component> cmps)
    {
        if(cmps.size() == 1)
        {
            return cmps.get(0);
        }
        
        return posLayout.apply(cmps.get(0), seq(posLayout, cmps.subList(1, cmps.size())), 1.0f / cmps.size());
    }
    
    public static Component block(List<Component> cmps, int rowNum, int colNum)
    {
        return vSeq(normalize(cmps, rowNum, colNum).stream().map(rowCmps -> hSeq(rowCmps)).collect(toList()));
    }

    private static Collection<List<Component>> normalize(List<Component> cmps, int rowNum, int colNum)
    {
        List<Component> paddedCmps = padding(cmps, rowNum * colNum - cmps.size());
      
        return IntStream.range(0, paddedCmps.size())
                        .mapToObj(idx -> asList(idx, paddedCmps.get(idx)))
                        .collect(groupingBy(idxAndCmp -> (Integer)idxAndCmp.get(0) / colNum, 
                                            mapping(idxAndCmp -> (Component)idxAndCmp.get(1), toList())))
                        .values();
    }
  
    private static List<Component> padding(List<Component> cmps, int num)
    {
        return Stream.concat(cmps.stream(), Collections.nCopies(num, empty()).stream()).collect(toList());
    }
    
    public static Component blockm(List<Component> cmps, int rowNum, int colNum, float hRatio, float vRatio)
    {
        return block(cmps.stream().map(cmp -> center(cmp, hRatio, vRatio)).collect(toList()), rowNum, colNum);
    }
}
