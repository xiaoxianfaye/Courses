package fayelab.ddd.layout.globalparam.functional;

import java.awt.Component;
import java.awt.Container;
import java.awt.Rectangle;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTextField;

import static java.util.Arrays.asList;
import static java.util.stream.Collectors.*;

public class Layout
{
    public static Rectangle rectangle(int left, int top, int width, int height)
    {
        return new Rectangle(left, top, width, height);
    }
    
    public static Function<Rectangle, Consumer<Container>> button(String text)
    {
        return atIn(new JButton(text));
    }
    
    public static Function<Rectangle, Consumer<Container>> textField()
    {
        return atIn(new JTextField());
    }
    
    public static Function<Rectangle, Consumer<Container>> label(String text)
    {
        return atIn(new JLabel(text));
    }
    
    private static Function<Rectangle, Consumer<Container>> atIn(Component cmp)
    {
        return rectangle -> {
            cmp.setBounds(rectangle);
            return container -> container.add(cmp);
        };
    }
    
    public static Function<Rectangle, Consumer<Container>> beside(Function<Rectangle, Consumer<Container>> leftCmp, 
            Function<Rectangle, Consumer<Container>> rightCmp, float ratio)
    {
        return outerRectangle -> container -> {
            leftCmp.apply(rectangle((int)outerRectangle.getX(), 
                                    (int)outerRectangle.getY(),
                                    (int)(outerRectangle.getWidth() * ratio), 
                                    (int)outerRectangle.getHeight())).accept(container);;
            rightCmp.apply(rectangle((int)(outerRectangle.getX() + outerRectangle.getWidth() * ratio), 
                                     (int)outerRectangle.getY(),
                                     (int)(outerRectangle.getWidth() * (1 - ratio)), 
                                     (int)outerRectangle.getHeight())).accept(container);
        };
    }
    
    public static Function<Rectangle, Consumer<Container>> above(Function<Rectangle, Consumer<Container>> upCmp, 
            Function<Rectangle, Consumer<Container>> downCmp, float ratio)
    {
        return outerRectangle -> container -> {
            upCmp.apply(rectangle((int)outerRectangle.getX(), 
                                  (int)outerRectangle.getY(),
                                  (int)outerRectangle.getWidth(), 
                                  (int)(outerRectangle.getHeight() * ratio))).accept(container);
            downCmp.apply(rectangle((int)outerRectangle.getX(), 
                                    (int)(outerRectangle.getY() + outerRectangle.getHeight() * ratio),
                                    (int)outerRectangle.getWidth(), 
                                    (int)(outerRectangle.getHeight() * (1 - ratio)))).accept(container);
        };
    }
    
    public static Function<Rectangle, Consumer<Container>> empty()
    {
        return outerRectangle -> container -> {};
    }
    
    @FunctionalInterface
    private interface PosLayout
    {
        Function<Rectangle, Consumer<Container>> apply(Function<Rectangle, Consumer<Container>> cmp1,
                Function<Rectangle, Consumer<Container>> cmp2, float ratio);
    }
    
    public static Function<Rectangle, Consumer<Container>> hCenter(
            Function<Rectangle, Consumer<Container>> cmp, float ratio)
    {
        return xCenter(Layout::beside, cmp, ratio); 
    }
    
    public static Function<Rectangle, Consumer<Container>> vCenter(
            Function<Rectangle, Consumer<Container>> cmp, float ratio)
    {
        return xCenter(Layout::above, cmp, ratio); 
    }
    
    private static Function<Rectangle, Consumer<Container>> xCenter(PosLayout posLayout,
            Function<Rectangle, Consumer<Container>> cmp, float ratio)
    {
        return posLayout.apply(empty(), posLayout.apply(cmp, empty(), (1 - 2 * ratio) / (1 - ratio)), ratio);
    }
    
    public static Function<Rectangle, Consumer<Container>> center(
            Function<Rectangle, Consumer<Container>> cmp, float hRatio, float vRatio)
    {
        return vCenter(hCenter(cmp, hRatio), vRatio);
    }
    
    public static Function<Rectangle, Consumer<Container>> hSeq(List<Function<Rectangle, Consumer<Container>>> cmps)
    {
        return seq(Layout::beside, cmps);
    }
    
    public static Function<Rectangle, Consumer<Container>> vSeq(List<Function<Rectangle, Consumer<Container>>> cmps)
    {
        return seq(Layout::above, cmps);
    }
    
    private static Function<Rectangle, Consumer<Container>> seq(PosLayout posLayout, 
            List<Function<Rectangle, Consumer<Container>>> cmps)
    {
        if(cmps.size() == 1)
        {
            return cmps.get(0);
        }
        
        return posLayout.apply(cmps.get(0), seq(posLayout, cmps.subList(1, cmps.size())), 1.0f / cmps.size());
    }
    
    public static Function<Rectangle, Consumer<Container>> block(
            List<Function<Rectangle, Consumer<Container>>> cmps, int rowNum, int colNum)
    {
        return vSeq(normalize(cmps, rowNum, colNum).stream().map(rowCmps -> hSeq(rowCmps)).collect(toList()));
    }
    
    @SuppressWarnings("unchecked")
    private static Collection<List<Function<Rectangle, Consumer<Container>>>> normalize(
            List<Function<Rectangle, Consumer<Container>>> cmps, int rowNum, int colNum)
    {
        List<Function<Rectangle, Consumer<Container>>> paddedCmps = padding(cmps, rowNum * colNum - cmps.size());
      
        return IntStream.range(0, paddedCmps.size())
                        .mapToObj(idx -> asList(idx, paddedCmps.get(idx)))
                        .collect(groupingBy(idxAndCmp -> (Integer)idxAndCmp.get(0) / colNum, 
                                            mapping(idxAndCmp -> (Function<Rectangle, Consumer<Container>>)idxAndCmp.get(1), toList())))
                        .values();
    }

    private static List<Function<Rectangle, Consumer<Container>>> padding(
            List<Function<Rectangle, Consumer<Container>>> cmps, int num)
    {
        return Stream.concat(cmps.stream(), Collections.nCopies(num, empty()).stream()).collect(toList());
    }
    
    public static Function<Rectangle, Consumer<Container>> blockm(
            List<Function<Rectangle, Consumer<Container>>> cmps, int rowNum, int colNum, float hRatio, float vRatio)
    {
        return block(cmps.stream().map(cmp -> center(cmp, hRatio, vRatio)).collect(toList()), rowNum, colNum);
    }
}
