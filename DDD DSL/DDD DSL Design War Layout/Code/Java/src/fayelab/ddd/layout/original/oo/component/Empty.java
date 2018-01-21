package fayelab.ddd.layout.original.oo.component;

import java.awt.Container;

public class Empty implements Component
{
    @Override
    public Component at(int left, int top, int width, int height)
    {
        return this;
    }

    @Override
    public Component in(Container container)
    {
        return this;
    }
}
