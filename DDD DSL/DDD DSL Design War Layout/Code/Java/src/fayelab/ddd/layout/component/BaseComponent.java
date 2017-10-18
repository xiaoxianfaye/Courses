package fayelab.ddd.layout.component;

import java.awt.Container;

public class BaseComponent implements Component
{
    protected java.awt.Component awtComponent;

    @Override
    public Component at(int x, int y, int width, int height)
    {
        awtComponent.setBounds(x, y, width, height);
        return this;
    }

    @Override
    public Component in(Container container)
    {
        container.add(awtComponent);
        return this;
    }
}
