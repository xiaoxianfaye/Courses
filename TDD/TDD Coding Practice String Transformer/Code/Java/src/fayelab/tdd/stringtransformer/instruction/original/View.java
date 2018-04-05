package fayelab.tdd.stringtransformer.instruction.original;

import java.util.Map;

import fayelab.tdd.stringtransformer.instruction.original.Entry.Key;

public interface View
{
    void setPresenter(Presenter presenter);

    void onInit(Map<Key, Value<?>> data);

    Map<Key, Value<?>> collectAddTransData();

    void onAddTrans(Map<Key, Value<?>> data);

    Map<Key, Value<?>> collectRemoveTransData();

    void onRemoveTrans(Map<Key, Value<?>> data);

    void onRemoveAllTranses(Map<Key, Value<?>> data);

    void onValidatingFailed(Map<Key, Value<?>> data);
}
