package fayelab.ddd.layout.globalparam.oo.position;

import java.awt.Container;

import fayelab.ddd.layout.globalparam.oo.component.Component;

public class Above implements Component
{
    private Component upCmp;
    private Component downCmp;
    private float ratio;

    public Above(Component upCmp, Component downCmp, float ratio)
    {
        this.upCmp = upCmp;
        this.downCmp = downCmp;
        this.ratio = ratio;
    }

    @Override
    public Component at(int left, int top, int width, int height)
    {
        upCmp.at(left, top, width, (int)(height * ratio));
        downCmp.at(left, top + (int)(height * ratio), width, (int)(height * (1 - ratio)));
        return this;
    }

    @Override
    public Component in(Container container)
    {
        upCmp.in(container);
        downCmp.in(container);
        return this;
    }
}
