package fayelab.tdd.stringtransformer.original;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;

import fayelab.tdd.stringtransformer.original.Param.Name;
import fayelab.tdd.stringtransformer.original.Presenter.ValidatingFailedReason;

import static fayelab.tdd.stringtransformer.original.Param.Name.*;
import static fayelab.tdd.stringtransformer.original.DataBuilder.*;
import static fayelab.tdd.stringtransformer.original.Presenter.SELECTED_INDEX_NONE;
import static fayelab.tdd.stringtransformer.original.Presenter.ValidatingFailedReason.*;

public class ViewImpl extends JFrame implements View
{
    private Presenter presenter;
    
    private static Map<ValidatingFailedReason, String> vfrAndTip = null;
    static
    {
        vfrAndTip = new HashMap<>();
        vfrAndTip.put(ADD_DUPLICATE_TRANSFORMER, "The transformer to be added has been existed in the chain.");
    }

    public ViewImpl()
    {
        this.initUI();
    }

    @Override
    public void onInitData(Map<Name, Object> data)
    {
        setListData(lstAvailableTransformers, data, AVAILABLE_TRANSNAMES);
        setListSelectedItem(lstAvailableTransformers, data, AVAILABLE_SELECTED_INDEX);
    }

    @Override
    public Map<Name, Object> collectAddData()
    {
        return build(getListSelectedItem(lstAvailableTransformers, AVAILABLE_SELECTED_TRANSNAME));
    }

    @Override
    public void onAddTransformer(Map<Name, Object> data)
    {
        updateBothLists(data);
    }

    @Override
    public Map<Name, Object> collectRemoveData()
    {
        return build(getListSelectedItem(lstTransformerChain, CHAIN_SELECTED_TRANSNAME));
    }

    @Override
    public void onRemoveTransformer(Map<Name, Object> data)
    {
        updateBothLists(data);
    }

    @Override
    public void onRemoveAllTransformers(Map<Name, Object> data)
    {
        updateBothLists(data);
    }

    @Override
    public Map<Name, Object> collectApplyData()
    {
        return build(new Param<>(SOURCESTR, txtSourceStr.getText()));
    }

    @Override
    public void onApplyTransformerChain(Map<Name, Object> data)
    {
        txtResultStr.setText(toStr(data.get(RESULTSTR)));
    }

    @Override
    public void onValidatingFailed(Map<Name, Object> data)
    {
        ValidatingFailedReason vfr = toValidatingFailedReason(data.get(VALIDATING_FAILED_REASON));
        JOptionPane.showMessageDialog(this, vfrAndTip.get(vfr));
    }

    private void updateBothLists(Map<Name, Object> data)
    {
        setListData(lstTransformerChain, data, CHAIN_TRANSNAMES);
        setListSelectedItem(lstTransformerChain, data, CHAIN_SELECTED_INDEX);
        setListSelectedItem(lstAvailableTransformers, data, AVAILABLE_SELECTED_INDEX);
    }
    
    private void setListData(JList<String> lst, Map<Name, Object> data, Name name)
    {
        lst.setListData(toStringArray(data.get(name)));
    }
    
    private void setListSelectedItem(JList<String> lst, Map<Name, Object> data, Name name)
    {
        int selectedIndex = toInt(data.get(name));
        if(selectedIndex == SELECTED_INDEX_NONE)
        {
            lst.clearSelection();
        }
        else
        {
            lst.setSelectedIndex(selectedIndex);
        }
    }
    
    private Param<String> getListSelectedItem(JList<String> lst, Name name)
    {
        return new Param<>(name, lst.getSelectedValue());
    }
    
    @Override
    public void setPresenter(Presenter presenter)
    {
        this.presenter = presenter;
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
        lblAvailableTransformers.setText("Available Transformers");
        pnCWest.add(lblAvailableTransformers, BorderLayout.NORTH);
        pnCWest.add(scrPnAvailableTransformers, BorderLayout.CENTER);
        scrPnAvailableTransformers.getViewport().add(lstAvailableTransformers);
        lstAvailableTransformers.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        
        pnCenter.add(pnCEast, BorderLayout.EAST);
        pnCEast.setPreferredSize(new Dimension(200, 0));
        pnCEast.setBorder(BorderFactory.createEmptyBorder(12, 6, 12, 6));
        pnCEast.setLayout(new BorderLayout(0, 6));
        lblTransformerChain.setText("Transformer Chain");
        pnCEast.add(lblTransformerChain, BorderLayout.NORTH);
        pnCEast.add(scrPnTransformerChain, BorderLayout.CENTER);
        scrPnTransformerChain.getViewport().add(lstTransformerChain);
        lstTransformerChain.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        
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
        presenter.addTransformer();
    }

    private void btnRemove_actionPerformed(ActionEvent e)
    {
        presenter.removeTransformer();
    }

    private void btnRemoveAll_actionPerformed(ActionEvent e)
    {
        presenter.removeAllTransformers();
    }

    private void btnApply_actionPerformed(ActionEvent e)
    {
        presenter.applyTransformerChain();
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
    private JLabel lblAvailableTransformers = new JLabel();
    private JLabel lblTransformerChain = new JLabel();
    private JScrollPane scrPnAvailableTransformers = new JScrollPane();
    private JList<String> lstAvailableTransformers = new JList<>();
    private JScrollPane scrPnTransformerChain = new JScrollPane();
    private JList<String> lstTransformerChain = new JList<String>();
    private JPanel pnBtnAdd = new JPanel();
    private JButton btnAdd = new JButton();
    private JPanel pnBtnRemove = new JPanel();
    private JButton btnRemove = new JButton();
    private JPanel pnBtnRemoveAll = new JPanel();
    private JButton btnRemoveAll = new JButton();
    
    private static final long serialVersionUID = 1L;
}