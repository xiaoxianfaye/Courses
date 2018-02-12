package fayelab.tdd.stringtransformer.original;

import java.util.Map;

import fayelab.tdd.stringtransformer.original.Param.Name;
import fayelab.tdd.stringtransformer.original.Presenter;
import fayelab.tdd.stringtransformer.original.View;

public class ViewStub implements View
{
    private Map<Name, Object> onInitData;
    private Map<Name, Object> addData;
    private Map<Name, Object> onAddData;
    private Map<Name, Object> removeData;
    private Map<Name, Object> onRemoveData;
    private Map<Name, Object> onRemoveAllData;
    private Map<Name, Object> applyData;
    private Map<Name, Object> onApplyData;
    private Map<Name, Object> onValidatingFailedData;

    @Override
    public void onInitData(Map<Name, Object> data)
    {
        onInitData = data;
    }
    
    public Map<Name, Object> getOnInitData()
    {
        return onInitData;
    }

    @Override
    public Map<Name, Object> collectAddData()
    {
        return addData;
    }

    public void setAddData(Map<Name, Object> data)
    {
        addData = data;
    }

    @Override
    public void onAddTransformer(Map<Name, Object> data)
    {
        onAddData = data;
    }

    public Object getOnAddData()
    {
        return onAddData;
    }

    @Override
    public Map<Name, Object> collectRemoveData()
    {
        return removeData;
    }

    public void setRemoveData(Map<Name, Object> data)
    {
        removeData = data;
    }

    @Override
    public void onRemoveTransformer(Map<Name, Object> data)
    {
        onRemoveData = data;
    }
    
    public Map<Name, Object> getOnRemoveData()
    {
        return onRemoveData;
    }

    public Map<Name, Object> getOnRemoveAllData()
    {
        return onRemoveAllData;
    }

    @Override
    public void onRemoveAllTransformers(Map<Name, Object> data)
    {
        onRemoveAllData = data;
    }

    @Override
    public Map<Name, Object> collectApplyData()
    {
        return applyData;
    }

    public void setApplyData(Map<Name, Object> data)
    {
        applyData = data;
    }
    
    public Map<Name, Object> getOnApplyData()
    {
        return onApplyData;
    }
    
    @Override
    public void onApplyTransformerChain(Map<Name, Object> data)
    {
        onApplyData = data;
    }

    @Override
    public void onValidatingFailed(Map<Name, Object> data)
    {
        onValidatingFailedData = data;
    }

    public Map<Name, Object> getOnValidatingFailedData()
    {
        return onValidatingFailedData;
    }

    @Override
    public void setPresenter(Presenter presenter)
    {
    }
}