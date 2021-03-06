package fayelab.ddd.layout.globalparam.oo;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.WindowEvent;
import java.util.List;
import java.util.stream.IntStream;

import javax.swing.JFrame;
import javax.swing.JPanel;

import fayelab.ddd.layout.globalparam.oo.component.Component;

import static java.util.Arrays.asList;
import static java.util.stream.Collectors.toList;
import static fayelab.ddd.layout.globalparam.oo.LayoutTool.*;

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
        button("Button").at(0, 0, 200, 60).in(container);
    }
    
    private void test_beside()
    {
        beside(textField(), button("Btn"), 0.8f).at(0, 0, 300, 60).in(container);
    }
    
    private void test_above()
    {
        above(textField(), button("Button"), 0.5f).at(0, 0, 300, 60).in(container);
    }
    
    private void test_beside_above()
    {
        above(beside(textField(), button("Btn1"), 0.8f), 
              button("Btn2"), 0.5f).at(0, 0, 300, 60).in(container);        
    }
    
    private void test_empty()
    {        
        beside(empty(), button("Button"), 0.5f).at(0, 0, 300, 60).in(container);
    }
    
    private void test_hCenter()
    {
        hCenter(button("Button"), 0.1f).at(0, 0, 545, 325).in(container);
    }
    
    private void test_vCenter()
    {
        vCenter(button("Button"), 0.1f).at(0, 0, 545, 325).in(container);
    }
    
    private void test_center()
    {
        center(button("Center"), 0.2f, 0.1f).at(0, 0, 545, 325).in(container);
    }
    
    private void test_hSeq()
    {
        hSeq(asList(button("1"), button("2"), button("3"))).at(0, 0, 300, 60).in(container);
    }
    
    private void test_vSeq()
    {
        vSeq(asList(button("1"), button("2"), button("3"))).at(0, 0, 150, 200).in(container);
    }
    
    private void test_block()
    {
        List<Component> cmps = IntStream.rangeClosed(1, 11)
                                        .mapToObj(i -> button(String.valueOf(i)))
                                        .collect(toList());

        block(cmps, 4, 3).at(0, 0, 545, 325).in(container);
    }
    
    private void test_blockm()
    {
        List<Component> cmps = IntStream.rangeClosed(1, 11)
                                        .mapToObj(i -> button(String.valueOf(i)))
                                        .collect(toList());
                
        blockm(cmps, 4, 3, 0.1f, 0.1f).at(0, 0, 545, 325).in(container);
    }

    private void test_minicalc()
    {
        List<String> texts = asList("0", "1", "2", "+",
                                    "3", "4", "5", "-",
                                    "6", "7", "8", "*",
                                    "9", "=", "%", "/");
        List<Component> btns = texts.stream().map(text -> button(text)).collect(toList());

        above(above(textField(), beside(button("Backspace"), button("C"), 0.5f), 0.5f), 
              block(btns, 4, 4), 0.3f).at(0, 0, 545, 325).in(container);
    }
    
    private void test_minicalc_margin()
    {
        List<String> texts = asList("0", "1", "2", "+",
                                    "3", "4", "5", "-",
                                    "6", "7", "8", "*",
                                    "9", "=", "%", "/");
        List<Component> btns = texts.stream().map(text -> button(text)).collect(toList());

        above(above(textField(), beside(button("Backspace"), button("C"), 0.5f), 0.5f), 
              blockm(btns, 4, 4, 0.02f, 0.02f), 0.3f).at(0, 0, 545, 325).in(container);
    }
    
    private void test_globalparam()
    {
        Component params = vSeq(asList(
                param("Parameter 1", textField()),
                param("Parameter 2", textField()),
                param("Parameter 3", textField())));

        Component btns = beside(empty(), 
                                center(beside(button("Set"), 
                                              beside(empty(), button("Close"), 0.1f), 0.5f), 0.06f, 0.2f), 0.2f);

        above(params, btns, 0.8f).at(0, 0, 545, 320).in(container);
    }
    
    private Component param(String labelText, Component cmp)
    {
        return center(beside(label(labelText), beside(empty(), cmp, 0.1f), 0.3f), 0.05f, 0.3f);
    }
    
    public static void main(String[] args)
    {
        TestFrm frm = new TestFrm();
        
        //Test Cases
//        frm.test_component();
//        frm.test_beside();
//        frm.test_above();
//        frm.test_beside_above();
//        frm.test_empty();
//        frm.test_hCenter();
//        frm.test_vCenter();
//        frm.test_center();
//        frm.test_hSeq();
//        frm.test_vSeq();
//        frm.test_block();
//        frm.test_blockm();
//        frm.test_minicalc();
//        frm.test_minicalc_margin();
        frm.test_globalparam();
        
        frm.centerShow();
    }
}
