package fayelab.ddd.layout.original.oo.component;

import java.awt.Container;

public interface Component
{
    Component at(int left, int top, int width, int height);

    Component in(Container container);
}
