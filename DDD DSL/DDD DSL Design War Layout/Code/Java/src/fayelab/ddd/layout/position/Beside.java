package fayelab.ddd.layout.position;

import java.awt.Container;

import fayelab.ddd.layout.component.Component;

public class Beside implements Component
{
    private Component left;
    private Component right;
    private float ratio;

    public Beside(Component left, Component right, float ratio)
    {
        this.left = left;
        this.right = right;
        this.ratio = ratio;
    }

    @Override
    public Component at(int x, int y, int width, int height)
    {
        left.at(x, y, (int)(width * ratio), height);
        right.at(x + (int)(width * ratio), y, (int)(width * (1 - ratio)), height);
        return this;
    }

    @Override
    public Component in(Container container)
    {
        left.in(container);
        right.in(container);
        return this;
    }
}
