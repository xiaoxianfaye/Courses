package fayelab.ddd.layout;

import fayelab.ddd.layout.component.Button;
import fayelab.ddd.layout.component.Component;
import fayelab.ddd.layout.component.TextField;
import fayelab.ddd.layout.position.Beside;
import fayelab.ddd.layout.position.Above;

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
}
