package fayelab.ddd.layout.globalparam.oo.component;

import java.awt.Container;

import fayelab.ddd.layout.globalparam.oo.component.Component;

public class ComponentStub implements Component
{
    private int[] rectangle;
    private Container container;

    @Override
    public Component at(int left, int top, int width, int height)
    {
        this.rectangle = new int[]{left, top, width, height};
        return this;
    }

    @Override
    public Component in(Container container)
    {
        this.container = container;
        return this;
    }

    public int[] getRectangle()
    {
        return rectangle;
    }

    public Container getContainer()
    {
        return container;
    }
}
