package fayelab.tdd.stringtransformer.dummy.original;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;

public class ViewImpl extends JFrame implements View
{
    private Presenter presenter;

    public ViewImpl()
    {
        this.initUI();
    }

    @Override
    public void setPresenter(Presenter presenter)
    {
        this.presenter = presenter;
    }

    @Override
    public void presentAvailTranses(List<String> transes)
    {
        lstAvail.setListData(transes.toArray(new String[] {}));
    }

    @Override
    public void setAvailSelectedIndex(int index)
    {
        lstAvail.setSelectedIndex(index);
    }

    @Override
    public String getAvailSelectedTrans()
    {
        return lstAvail.getSelectedValue();
    }

    @Override
    public void presentChainTranses(List<String> transes)
    {
        lstChain.setListData(transes.toArray(new String[] {}));
    }

    @Override
    public void setChainSelectedIndex(int index)
    {
        lstChain.setSelectedIndex(index);
    }

    @Override
    public void notifyAddAlreadyExistedInChainTrans()
    {
        showInfo("The transformer to be added has been already existed in the chain.");
    }

    @Override
    public void notifyAvailTransNotSpecified()
    {
        showInfo("Specify an available transformer, please.");
    }

    @Override
    public String getChainSelectedTrans()
    {
        return lstChain.getSelectedValue();
    }

    @Override
    public void notifyChainTransNotSpecified()
    {
        showInfo("Specify a transformer from the chain, please.");
    }

    @Override
    public void notifyChainEmpty()
    {
        showInfo("Specify the transformer chain, please.");
    }

    @Override
    public String getSourceStr()
    {
        return txtSourceStr.getText();
    }

    @Override
    public void presentResultStr(String str)
    {
        txtResultStr.setText(str);
    }

    @Override
    public void notifySourceStrEmpty()
    {
        showInfo("Specify the source string, please.");
        txtSourceStr.requestFocus();
    }

    @Override
    public void notifySourceStrIllegal()
    {
        showInfo("Specify the legal source string, please.");
        txtSourceStr.requestFocus();
        txtSourceStr.selectAll();
    }

    private void showInfo(String info)
    {
        JOptionPane.showMessageDialog(this, info);
    }

    private void initUI()
    {
        this.setTitle("String Transformer");
        this.setSize(560, 410);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        this.getContentPane().add(pnParent, BorderLayout.CENTER);
        pnParent.setBorder(BorderFactory.createEmptyBorder(12, 6, 12, 6));
        pnParent.setLayout(new BorderLayout(0, 6));

        pnParent.add(pnSouth, BorderLayout.SOUTH);
        pnSouth.setLayout(new BorderLayout(12, 0));
        pnSouth.add(pnSLeft, BorderLayout.CENTER);
        pnSLeft.setLayout(new BorderLayout(6, 0));
        pnSouth.add(pnSRight, BorderLayout.EAST);
        pnSRight.setLayout(new FlowLayout(FlowLayout.RIGHT, 6, 0));
        pnSRight.add(btnApply);
        btnApply.setText("Apply");
        btnApply.setPreferredSize(new Dimension(90, 23));
        btnApply.setMnemonic('A');
        btnApply.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                btnApply_actionPerformed(e);
            }
        });
        pnSRight.add(btnExit);
        btnExit.setText("Exit");
        btnExit.setPreferredSize(new Dimension(90, 23));
        btnExit.setMnemonic('E');
        btnExit.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                btnExit_actionPerformed(e);
            }
        });

        pnParent.add(pnNorth, BorderLayout.NORTH);
        pnNorth.setBorder(BorderFactory.createEmptyBorder(12, 6, 12, 6));
        pnNorth.setLayout(new BorderLayout(6, 0));
        pnNorth.add(pnNWest, BorderLayout.WEST);
        pnNorth.add(pnNCenter, BorderLayout.CENTER);
        pnNWest.setLayout(new GridLayout(2, 1, 0, 6));
        pnNWest.add(lblSourceStr);
        lblSourceStr.setText("Source String");
        pnNWest.add(lblResultStr);
        lblResultStr.setText("Result String");
        pnNCenter.setLayout(new GridLayout(2, 1, 0, 6));
        pnNCenter.add(txtSourceStr);
        txtResultStr.setEditable(false);
        pnNCenter.add(txtResultStr);

        pnParent.add(pnCenter, BorderLayout.CENTER);
        pnCenter.setLayout(new BorderLayout(6, 0));

        pnCenter.add(pnCWest, BorderLayout.WEST);
        pnCWest.setPreferredSize(new Dimension(200, 0));
        pnCWest.setBorder(BorderFactory.createEmptyBorder(12, 6, 12, 6));
        pnCWest.setLayout(new BorderLayout(0, 6));
        lblAvail.setText("Available Transformers");
        pnCWest.add(lblAvail, BorderLayout.NORTH);
        lstAvail.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        pnCWest.add(lstAvail, BorderLayout.CENTER);

        pnCenter.add(pnCEast, BorderLayout.EAST);
        pnCEast.setPreferredSize(new Dimension(200, 0));
        pnCEast.setBorder(BorderFactory.createEmptyBorder(12, 6, 12, 6));
        pnCEast.setLayout(new BorderLayout(0, 6));
        lblChain.setText("Transformer Chain");
        pnCEast.add(lblChain, BorderLayout.NORTH);
        lstChain.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        pnCEast.add(lstChain, BorderLayout.CENTER);

        pnCenter.add(pnCCenter, BorderLayout.CENTER);
        pnCCenter.setLayout(new GridLayout(8, 1));
        pnCCenter.add(new JPanel());
        pnCCenter.add(new JPanel());
        pnCCenter.add(pnBtnAdd);
        pnCCenter.add(new JPanel());
        pnCCenter.add(pnBtnRemove);
        pnCCenter.add(new JPanel());
        pnCCenter.add(pnBtnRemoveAll);
        pnCCenter.add(new JPanel());

        pnBtnAdd.setLayout(new BorderLayout());
        btnAdd.setText("Add >>");
        btnAdd.setPreferredSize(new Dimension(120, 23));
        btnAdd.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                btnAdd_actionPerformed(e);
            }
        });
        pnBtnAdd.add(btnAdd, BorderLayout.NORTH);

        pnBtnRemove.setLayout(new BorderLayout());
        btnRemove.setText("Remove <<");
        btnRemove.setPreferredSize(new Dimension(120, 23));
        btnRemove.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                btnRemove_actionPerformed(e);
            }
        });
        pnBtnRemove.add(btnRemove, BorderLayout.NORTH);

        pnBtnRemoveAll.setLayout(new BorderLayout());
        btnRemoveAll.setText("Remove All");
        btnRemoveAll.setPreferredSize(new Dimension(120, 23));
        btnRemoveAll.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                btnRemoveAll_actionPerformed(e);
            }
        });
        pnBtnRemoveAll.add(btnRemoveAll, BorderLayout.NORTH);
    }

    private void btnAdd_actionPerformed(ActionEvent e)
    {
        presenter.addTrans();
    }

    private void btnRemove_actionPerformed(ActionEvent e)
    {
        presenter.removeTrans();
    }

    private void btnRemoveAll_actionPerformed(ActionEvent e)
    {
        presenter.removeAllTranses();
    }

    private void btnApply_actionPerformed(ActionEvent e)
    {
        presenter.applyTransChain();
    }

    private void btnExit_actionPerformed(ActionEvent e)
    {
        System.exit(0);
    }

    private static void centerShow(ViewImpl impl)
    {
        Toolkit tk = Toolkit.getDefaultToolkit();
        impl.setLocation((tk.getScreenSize().width - impl.getWidth()) / 2,
                (tk.getScreenSize().height - impl.getHeight()) / 2);
        impl.setVisible(true);
    }

    public static void main(String[] args)
    {
        ViewImpl viewImpl = new ViewImpl();
        BusinessLogic businessLogicImpl = new BusinessLogicImpl();
        Presenter presenter = new Presenter(viewImpl, businessLogicImpl);
        presenter.init();

        centerShow(viewImpl);
    }

    private JPanel pnParent = new JPanel();
    private JPanel pnSouth = new JPanel();
    private JPanel pnSLeft = new JPanel();
    private JPanel pnSRight = new JPanel();
    private JButton btnApply = new JButton();
    private JButton btnExit = new JButton();
    private JPanel pnNorth = new JPanel();
    private JPanel pnNWest = new JPanel();
    private JLabel lblSourceStr = new JLabel();
    private JLabel lblResultStr = new JLabel();
    private JPanel pnNCenter = new JPanel();
    private JTextField txtSourceStr = new JTextField();
    private JTextField txtResultStr = new JTextField();
    private JPanel pnCenter = new JPanel();
    private JPanel pnCWest = new JPanel();
    private JPanel pnCCenter = new JPanel();
    private JPanel pnCEast = new JPanel();
    private JLabel lblAvail = new JLabel();
    private JLabel lblChain = new JLabel();
    private JList<String> lstAvail = new JList<>();
    private JList<String> lstChain = new JList<String>();
    private JPanel pnBtnAdd = new JPanel();
    private JButton btnAdd = new JButton();
    private JPanel pnBtnRemove = new JPanel();
    private JButton btnRemove = new JButton();
    private JPanel pnBtnRemoveAll = new JPanel();
    private JButton btnRemoveAll = new JButton();

    private static final long serialVersionUID = 1L;
}