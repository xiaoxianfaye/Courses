package fayelab.ddd.layout.original.functional;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;
import javax.swing.JPanel;

import static fayelab.ddd.layout.original.functional.Layout.*;

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
        button("Button").apply(rectangle(0, 0, 200, 60)).accept(container);
    }
    
    private void test_beside()
    {
        beside(textField(), button("Btn"), 0.8f).apply(rectangle(0, 0, 300, 60)).accept(container);
    }
    
    private void test_above()
    {
        above(textField(), button("Button"), 0.5f).apply(rectangle(0, 0, 300, 60)).accept(container);
    }
//    
//    private void test_beside_above()
//    {
//        above(beside(textField(), button().title("Btn1"), 0.8f), 
//              button().title("Btn2"), 0.5f).at(0, 0, 300, 60).in(container);        
//    }
//    
//    private void test_empty()
//    {        
//        beside(empty(), button().title("Button"), 0.5f).at(0, 0, 300, 60).in(container);
//    }
//    
//    private void test_hCenter()
//    {
//        hCenter(button().title("Button"), 0.1f).at(0, 0, 545, 325).in(container);
//    }
//    
//    private void test_vCenter()
//    {
//        vCenter(button().title("Button"), 0.1f).at(0, 0, 545, 325).in(container);
//    }
//    
//    private void test_center()
//    {
//        center(button().title("Center"), 0.2f, 0.1f).at(0, 0, 545, 325).in(container);
//    }
//    
//    private void test_hSeq()
//    {
//        hSeq(button().title("1"), button().title("2"), button().title("3")).at(0, 0, 300, 60).in(container);
//    }
//    
//    private void test_vSeq()
//    {
//        vSeq(button().title("1"), button().title("2"), button().title("3")).at(0, 0, 150, 200).in(container);
//    }
//    
//    private void test_block()
//    {
//        Component[] cmps = IntStream.rangeClosed(1, 11)
//                                    .mapToObj(i -> button().title(String.valueOf(i)))
//                                    .toArray(Component[]::new);
//                
//        block(cmps, 4, 3).at(0, 0, 545, 325).in(container);
//    }
//    
//    private void test_blockWithMargin()
//    {
//        Component[] cmps = IntStream.rangeClosed(1, 11)
//                                    .mapToObj(i -> button().title(String.valueOf(i)))
//                                    .toArray(Component[]::new);
//                
//        blockWithMargin(cmps, 4, 3, 0.1f, 0.1f).at(0, 0, 545, 325).in(container);
//    }
//
//    private void test_minicalc_without_margin()
//    {
//        Component[] operButtons = new Component[] {
//                button().title("0"), button().title("1"), button().title("2"), button().title("+"),
//                button().title("3"), button().title("4"), button().title("5"), button().title("-"),
//                button().title("6"), button().title("7"), button().title("8"), button().title("*"),
//                button().title("9"), button().title("="), button().title("%"), button().title("/")};
//
//        above(above(textField(), beside(button().title("Backspace"), button().title("C"), 0.5f), 0.5f), 
//              block(operButtons, 4, 4), 0.3f).at(0, 0, 545, 325).in(container);
//    }
//    
//    private void test_minicalc_with_margin()
//    {
//        Component[] operButtons = new Component[]{
//                button().title("0"), button().title("1"), button().title("2"), button().title("+"),
//                button().title("3"), button().title("4"), button().title("5"), button().title("-"),
//                button().title("6"), button().title("7"), button().title("8"), button().title("*"),
//                button().title("9"), button().title("="), button().title("%"), button().title("/")};
//
//        above(above(textField(), beside(button().title("Backspace"), button().title("C"), 0.5f), 0.5f), 
//              blockWithMargin(operButtons, 4, 4, 0.02f, 0.02f), 0.3f).at(0, 0, 545, 325).in(container);
//    }

    public static void main(String[] args)
    {
        TestFrm frm = new TestFrm();
        
        //Test Cases
//        frm.test_component();
//        frm.test_beside();
        frm.test_above();
//        frm.test_beside_above();
//        frm.test_empty();
//        frm.test_hCenter();
//        frm.test_vCenter();
//        frm.test_center();
//        frm.test_hSeq();
//        frm.test_vSeq();
//        frm.test_block();
//        frm.test_blockWithMargin();
//        frm.test_minicalc_without_margin();
//        frm.test_minicalc_with_margin();
        
        frm.centerShow();
    }
}
