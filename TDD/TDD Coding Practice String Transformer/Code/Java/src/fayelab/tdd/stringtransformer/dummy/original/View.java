package fayelab.tdd.stringtransformer.dummy.original;

import java.util.List;

public interface View
{
    void setPresenter(Presenter presenter);

    void presentAvailTranses(List<String> transes);

    void setAvailSelectedIndex(int index);

    String getAvailSelectedTrans();

    void presentChainTranses(List<String> transes);

    void setChainSelectedIndex(int index);

    void notifyAddAlreadyExistedInChainTrans();

    void notifyAvailTransNotSpecified();

    String getChainSelectedTrans();

    void notifyChainTransNotSpecified();

    void notifyChainEmpty();

    String getSourceStr();

    void presentResultStr(String str);

    void notifySourceStrEmpty();

    void notifySourceStrIllegal();
}
