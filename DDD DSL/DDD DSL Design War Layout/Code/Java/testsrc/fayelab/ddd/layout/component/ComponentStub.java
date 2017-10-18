package fayelab.ddd.layout.component;

import java.awt.Container;

public class ComponentStub implements Component
{
    private int[] rect;
    private Container container;

    @Override
    public Component at(int x, int y, int width, int height)
    {
        this.rect = new int[]{x, y, width, height};
        return this;
    }

    @Override
    public Component in(Container container)
    {
        this.container = container;
        return this;
    }

    public int[] getRect()
    {
        return rect;
    }

    public Container getContainer()
    {
        return container;
    }
}
