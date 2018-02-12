package fayelab.tdd.stringtransformer.original;

import java.util.Map;

import fayelab.tdd.stringtransformer.original.Param.Name;

public interface View
{
    void onInitData(Map<Name, Object> data);

    Map<Name, Object> collectAddData();

    void onAddTransformer(Map<Name, Object> data);

    Map<Name, Object> collectRemoveData();

    void onRemoveTransformer(Map<Name, Object> data);

    void onRemoveAllTransformers(Map<Name, Object> data);

    Map<Name, Object> collectApplyData();

    void onApplyTransformerChain(Map<Name, Object> data);
    
    void onValidatingFailed(Map<Name, Object> data);
    
    void setPresenter(Presenter presenter);
}