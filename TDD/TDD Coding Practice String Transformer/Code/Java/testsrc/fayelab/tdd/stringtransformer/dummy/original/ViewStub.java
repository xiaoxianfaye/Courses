package fayelab.tdd.stringtransformer.dummy.original;

import java.util.List;

public class ViewStub implements View
{
    private List<String> availTranses;
    private int availSelectedIndex;
    private String availSelectedTrans;
    private List<String> chainTranses;
    private int chainSelectedIndex;
    private boolean addAlreadyExistedInChainTransNotified;
    private boolean availTransNotSpecifiedNotified;

    @Override
    public void setPresenter(Presenter presenter)
    {
    }

    @Override
    public void presentAvailTranses(List<String> transes)
    {
        availTranses = transes;
    }

    public List<String> getAvailTranses()
    {
        return availTranses;
    }

    @Override
    public void setAvailSelectedIndex(int index)
    {
        availSelectedIndex = index;
    }

    public int getAvailSelectedIndex()
    {
        return availSelectedIndex;
    }

    @Override
    public String getAvailSelectedTrans()
    {
        return availSelectedTrans;
    }

    public void setAvailSelectedTrans(String trans)
    {
        availSelectedTrans = trans;
    }

    @Override
    public void presentChainTranses(List<String> transes)
    {
        chainTranses = transes;
    }

    public List<String> getChainTranses()
    {
        return chainTranses;
    }

    @Override
    public void setChainSelectedIndex(int index)
    {
        chainSelectedIndex = index;
    }

    public int getChainSelectedIndex()
    {
        return chainSelectedIndex;
    }

    @Override
    public void notifyAddAlreadyExistedInChainTrans()
    {
        addAlreadyExistedInChainTransNotified = true;
    }

    public boolean isAddAlreadyExistedInChainTransNotified()
    {
        return addAlreadyExistedInChainTransNotified;
    }

    @Override
    public void notifyAvailTransNotSpecified()
    {
        availTransNotSpecifiedNotified = true;
    }

    public boolean isAvailTransNotSpecifiedNotified()
    {
        return availTransNotSpecifiedNotified;
    }
}
