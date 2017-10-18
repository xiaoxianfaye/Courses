package fayelab.ddd.layout.component;

import java.awt.Container;

public class Empty implements Component
{
    @Override
    public Component at(int x, int y, int width, int height)
    {
        return this;
    }

    @Override
    public Component in(Container container)
    {
        return this;
    }
}
