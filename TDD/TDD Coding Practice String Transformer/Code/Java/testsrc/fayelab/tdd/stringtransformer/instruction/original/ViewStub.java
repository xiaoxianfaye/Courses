package fayelab.tdd.stringtransformer.instruction.original;

import java.util.Map;

import fayelab.tdd.stringtransformer.instruction.original.Entry.Key;

public class ViewStub implements View
{
    private Map<Key, Value<?>> onInitData;
    private Map<Key, Value<?>> addTransData;
    private Map<Key, Value<?>> onAddTransData;
    private Map<Key, Value<?>> removeTransData;
    private Map<Key, Value<?>> onRemoveTransData;
    private Map<Key, Value<?>> onRemoveAllTransesData;
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
    public void onValidatingFailed(Map<Key, Value<?>> data)
    {
        onValidatingFailedData = data;
    }

    public Map<Key, Value<?>> getOnValidatingFailedData()
    {
        return onValidatingFailedData;
    }
}
