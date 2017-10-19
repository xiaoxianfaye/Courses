package fayelab.ddd.layout.globalparamconf.component;

import javax.swing.JLabel;

public class Label extends BaseComponent
{
    public Label()
    {
        this.awtComponent = new JLabel();
    }
    
    public Component title(String text)
    {
        ((JLabel)awtComponent).setText(text);
        return this;
    }
}
