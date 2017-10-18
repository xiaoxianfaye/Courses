package fayelab.ddd.layout.component;

import java.awt.Container;

public interface Component
{
    Component at(int x, int y, int width, int height);

    Component in(Container container);
}
