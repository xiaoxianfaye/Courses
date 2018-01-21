package fayelab.ddd.layout.globalparam.oo.position;

import java.awt.Container;

import fayelab.ddd.layout.globalparam.oo.component.Component;

public class Beside implements Component
{
    private Component leftCmp;
    private Component rightCmp;
    private float ratio;

    public Beside(Component leftCmp, Component rightCmp, float ratio)
    {
        this.leftCmp = leftCmp;
        this.rightCmp = rightCmp;
        this.ratio = ratio;
    }

    @Override
    public Component at(int left, int top, int width, int height)
    {
        leftCmp.at(left, top, (int)(width * ratio), height);
        rightCmp.at(left + (int)(width * ratio), top, (int)(width * (1 - ratio)), height);
        return this;
    }

    @Override
    public Component in(Container container)
    {
        leftCmp.in(container);
        rightCmp.in(container);
        return this;
    }
}
