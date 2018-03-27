package fayelab.tdd.stringtransformer.dummy.reverse;

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
    private String chainSelectedTrans;
    private boolean chainTransNotSpecifiedNotified;
    private boolean chainEmptyNotified;
    private String sourceStr;
    private String resultStr;
    private boolean sourceStrEmptyNotified;
    private boolean sourceStrIllegalNotified;

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

    @Override
    public String getChainSelectedTrans()
    {
        return chainSelectedTrans;
    }

    public void setChainSelectedTrans(String trans)
    {
        chainSelectedTrans = trans;
    }

    @Override
    public void notifyChainTransNotSpecified()
    {
        chainTransNotSpecifiedNotified = true;
    }

    public boolean isChainTransNotSpecifiedNotified()
    {
        return chainTransNotSpecifiedNotified;
    }

    @Override
    public void notifyChainEmpty()
    {
        chainEmptyNotified = true;
    }

    public boolean isChainEmptyNotified()
    {
        return chainEmptyNotified;
    }

    @Override
    public String getSourceStr()
    {
        return sourceStr;
    }

    public void setSourceStr(String str)
    {
        sourceStr = str;
    }

    @Override
    public void presentResultStr(String str)
    {
        resultStr = str;
    }

    public String getResultStr()
    {
        return resultStr;
    }

    @Override
    public void notifySourceStrEmpty()
    {
        sourceStrEmptyNotified = true;
    }

    public boolean isSourceStrEmptyNotified()
    {
        return sourceStrEmptyNotified;
    }

    @Override
    public void notifySourceStrIllegal()
    {
        sourceStrIllegalNotified = true;
    }

    public boolean isSourceStrIllegalNotified()
    {
        return sourceStrIllegalNotified;
    }
}
