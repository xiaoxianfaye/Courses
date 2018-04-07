package fayelab.tdd.stringtransformer.instruction.addall;

import java.util.Map;

import fayelab.tdd.stringtransformer.instruction.addall.Entry.Key;

public class ViewStub implements View
{
    private Map<Key, Value<?>> onInitData;
    private Map<Key, Value<?>> addTransData;
    private Map<Key, Value<?>> onAddTransData;
    private Map<Key, Value<?>> removeTransData;
    private Map<Key, Value<?>> onRemoveTransData;
    private Map<Key, Value<?>> onRemoveAllTransesData;
    private Map<Key, Value<?>> applyTransChainData;
    private Map<Key, Value<?>> onApplyTransChainData;
    private Map<Key, Value<?>> onAddAllTransesData;
    private Map<Key, Value<?>> onValidatingFailedData;

    @Override
    public void setPresenter(Presenter presenter)
    {
    }

    @Override
    public void onInit(Map<Key, Value<?>> data)
    {
        onInitData = data;
    }

    public Map<Key, Value<?>> getOnInitData()
    {
        return onInitData;
    }

    @Override
    public Map<Key, Value<?>> collectAddTransData()
    {
        return addTransData;
    }

    public void setAddTransData(Map<Key, Value<?>> data)
    {
        addTransData = data;
    }

    @Override
    public void onAddTrans(Map<Key, Value<?>> data)
    {
        onAddTransData = data;
    }

    public Map<Key, Value<?>> getOnAddTransData()
    {
        return onAddTransData;
    }

    @Override
    public Map<Key, Value<?>> collectRemoveTransData()
    {
        return removeTransData;
    }

    public void setRemoveTransData(Map<Key, Value<?>> data)
    {
        removeTransData = data;
    }

    @Override
    public void onRemoveTrans(Map<Key, Value<?>> data)
    {
        onRemoveTransData = data;
    }

    public Map<Key, Value<?>> getOnRemoveTransData()
    {
        return onRemoveTransData;
    }

    @Override
    public void onRemoveAllTranses(Map<Key, Value<?>> data)
    {
        onRemoveAllTransesData = data;
    }

    public Map<Key, Value<?>> getOnRemoveAllTransesData()
    {
        return onRemoveAllTransesData;
    }

    @Override
    public Map<Key, Value<?>> collectApplyTransChainData()
    {
        return applyTransChainData;
    }

    public void setApplyTransChainData(Map<Key, Value<?>> data)
    {
        applyTransChainData = data;
    }

    @Override
    public void onApplyTransChain(Map<Key, Value<?>> data)
    {
        onApplyTransChainData = data;
    }

    public Map<Key, Value<?>> getOnApplyTransChainData()
    {
        return onApplyTransChainData;
    }

    @Override
    public void onAddAllTranses(Map<Key, Value<?>> data)
    {
        onAddAllTransesData = data;
    }

    public Map<Key, Value<?>> getOnAddAllTransesData()
    {
        return onAddAllTransesData;
    }

    @Override
    public void onValidatingFailed(Map<Key, Value<?>> data)
    {
        onValidatingFailedData = data;
    }

    public Map<Key, Value<?>> getOnValidatingFailedData()
    {
        return onValidatingFailedData;
    }
}
