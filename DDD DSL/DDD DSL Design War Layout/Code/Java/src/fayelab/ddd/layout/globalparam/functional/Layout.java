package fayelab.ddd.layout.globalparam.functional;

import java.awt.Container;
import java.awt.Rectangle;
import java.util.function.Consumer;
import java.util.function.Function;

import javax.swing.JButton;
import javax.swing.JTextField;

public class Layout
{
    public static Rectangle rectangle(int left, int top, int width, int height)
    {
        return new Rectangle(left, top, width, height);
    }
    
    public static Function<Rectangle, Consumer<Container>> button(String text)
    {
        JButton btn = new JButton(text);
        return rectangle -> {
            btn.setBounds(rectangle);
            return container -> container.add(btn);
        };
    }
    
    public static Function<Rectangle, Consumer<Container>> textField()
    {
        JTextField textField = new JTextField();
        return rectangle -> {
            textField.setBounds(rectangle);
            return container -> container.add(textField);
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
}
