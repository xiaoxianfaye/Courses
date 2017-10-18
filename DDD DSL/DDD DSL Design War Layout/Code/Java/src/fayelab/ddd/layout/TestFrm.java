package fayelab.ddd.layout;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;
import javax.swing.JPanel;

import fayelab.ddd.layout.component.Button;
import fayelab.ddd.layout.component.Component;
import fayelab.ddd.layout.component.TextField;

import static fayelab.ddd.layout.LayoutTool.*;

public class TestFrm extends JFrame
{
    private static final long serialVersionUID = 1L;
    
    private Container container;

    public TestFrm()
    {
        this.setTitle("Layout Test");
        this.setSize(600, 400);
        initWorkspace();
    }
    
    private void initWorkspace()
    {
        Container pnRoot = this.getContentPane();
        
        JPanel pnNorth = new JPanel();
        pnNorth.setPreferredSize(new Dimension(0, 20));
        pnNorth.setMaximumSize(new Dimension(0, 20));
        pnNorth.setMinimumSize(new Dimension(0, 20));
        pnRoot.add(pnNorth, BorderLayout.NORTH);
        
        JPanel pnSouth = new JPanel();
        pnSouth.setPreferredSize(new Dimension(0, 20));
        pnSouth.setMaximumSize(new Dimension(0, 20));
        pnSouth.setMinimumSize(new Dimension(0, 20));
        pnRoot.add(pnSouth, BorderLayout.SOUTH);
        
        JPanel pnWest = new JPanel();
        pnWest.setPreferredSize(new Dimension(20, 0));
        pnWest.setMaximumSize(new Dimension(20, 0));
        pnWest.setMinimumSize(new Dimension(20, 0));
        pnRoot.add(pnWest, BorderLayout.WEST);
        
        JPanel pnEast = new JPanel();
        pnEast.setPreferredSize(new Dimension(20, 0));
        pnEast.setMaximumSize(new Dimension(20, 0));
        pnEast.setMinimumSize(new Dimension(20, 0));
        pnRoot.add(pnEast, BorderLayout.EAST);
        
        this.container = new JPanel();
        this.container.setBackground(Color.LIGHT_GRAY);
        this.container.setLayout(null);
        pnRoot.add(this.container, BorderLayout.CENTER);
    }

    protected void processWindowEvent(WindowEvent e)
    {
        if(e.getID() == WindowEvent.WINDOW_CLOSING)
        {
            System.exit(0);
        }
    }
    
    private void centerShow()
    {
        int frmWidth = this.getWidth();
        int frmHeight = this.getHeight();
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        this.setLocation((screenSize.width - frmWidth) / 2, (screenSize.height - frmHeight) / 2);
        this.setVisible(true);
    }
    
    private void test_component()
    {
        button().title("Button").at(0, 0, 200, 60).in(container);
    }
    
    private void test_beside()
    {
        beside(textField(), button().title("Btn"), 0.8f).at(0, 0, 300, 60).in(container);
    }
    
    private void test_above()
    {
        above(textField(), button().title("Button"), 0.5f).at(0, 0, 300, 60).in(container);
    }
    
    private void test_beside_above()
    {
        above(beside(textField(), button().title("Btn1"), 0.8f), 
              button().title("Btn2"), 0.5f).at(0, 0, 300, 60).in(container);        
    }
    
    private void test_empty()
    {        
        beside(empty(), button().title("Button"), 0.5f).at(0, 0, 300, 60).in(container);
    }

    public static void main(String[] args)
    {
        TestFrm frm = new TestFrm();
        
        //Test Cases
//        frm.test_component();
//        frm.test_beside();
//        frm.test_above();
//        frm.test_beside_above();
        frm.test_empty();
        
        frm.centerShow();
    }
}
