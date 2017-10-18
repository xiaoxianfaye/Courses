package fayelab.ddd.layout.position;

import java.awt.Container;

import fayelab.ddd.layout.component.Component;

public class Above implements Component
{
    private Component up;
    private Component down;
    private float ratio;

    public Above(Component up, Component down, float ratio)
    {
        this.up = up;
        this.down = down;
        this.ratio = ratio;
    }

    @Override
    public Component at(int x, int y, int width, int height)
    {
        up.at(x, y, width, (int)(height * ratio));
        down.at(x, y + (int)(height * ratio), width, (int)(height * (1 - ratio)));
        return this;
    }

    @Override
    public Component in(Container container)
    {
        up.in(container);
        down.in(container);
        return this;
    }
}
