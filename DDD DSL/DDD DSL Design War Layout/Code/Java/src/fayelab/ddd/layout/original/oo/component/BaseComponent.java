package fayelab.ddd.layout.original.oo.component;

import java.awt.Container;

public class BaseComponent implements Component
{
    protected java.awt.Component cmp;

    @Override
    public Component at(int left, int top, int width, int height)
    {
        cmp.setBounds(left, top, width, height);
        return this;
    }

    @Override
    public Component in(Container container)
    {
        container.add(cmp);
        return this;
    }
}
