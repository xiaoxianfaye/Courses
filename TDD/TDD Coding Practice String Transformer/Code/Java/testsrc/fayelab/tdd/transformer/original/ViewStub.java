package fayelab.tdd.transformer.original;

import java.util.List;

public class ViewStub implements View
{
    private List<String> availableTransIds;
    private String selectedAvailableTransId;
    private List<String> chainTransIds;
    private String selectedChainTransId;
    private String sourceStr;
    private String resultStr;
    private boolean isEmptySourceStrInputNotified;
    private boolean isEmptyChainInputNotified;

    @Override
    public void presentAvailableTransIds(List<String> transIds)
    {
        availableTransIds = transIds;
    }
    
    public List<String> getAvailableTransIds()
    {
        return availableTransIds;
    }

    @Override
    public String getSelectedAvailableTransId()
    {
        return selectedAvailableTransId;
    }

    public void setSelectedAvailableTransId(String transId)
    {
        selectedAvailableTransId = transId;
    }

    @Override
    public void presentChainTransIds(List<String> transIds)
    {
        chainTransIds = transIds;
    }

    public List<String> getChainTransIds()
    {
        return chainTransIds;
    }

    @Override
    public String getSelectedChainTransId()
    {
        return selectedChainTransId;
    }

    public void setSelectedChainTransId(String transId)
    {
        selectedChainTransId = transId;
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
    public void onEmptySourceStrInput()
    {
        isEmptySourceStrInputNotified = true;
    }

    public boolean isEmptySourceStrInputNotified()
    {
        return isEmptySourceStrInputNotified;
    }

    @Override
    public void onEmptyChainInput()
    {
        isEmptyChainInputNotified = true;
    }

    public boolean isEmptyChainInputNotified()
    {
        return isEmptyChainInputNotified;
    }
}
