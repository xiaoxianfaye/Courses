package fayelab.ddd.layout.original.oo.component;

import javax.swing.JButton;

public class Button extends BaseComponent
{
    public Button()
    {
        this.awtComponent = new JButton();
    }
    
    public Component title(String text)
    {
        ((JButton)awtComponent).setText(text);
        return this;
    }
}
