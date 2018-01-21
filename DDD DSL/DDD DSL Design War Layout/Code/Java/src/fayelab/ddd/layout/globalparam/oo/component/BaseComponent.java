package fayelab.ddd.layout.globalparam.oo.component;

import java.awt.Container;

public class BaseComponent implements Component
{
    protected java.awt.Component awtComponent;

    @Override
    public Component at(int left, int top, int width, int height)
    {
        awtComponent.setBounds(left, top, width, height);
        return this;
    }

    @Override
    public Component in(Container container)
    {
        container.add(awtComponent);
        return this;
    }
}
