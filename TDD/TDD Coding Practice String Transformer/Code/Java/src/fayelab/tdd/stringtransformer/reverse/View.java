package fayelab.tdd.stringtransformer.reverse;

import java.util.List;

public interface View
{
    void presentAvailableTransIds(List<String> transIds);

    String getSelectedAvailableTransId();

    void presentChainTransIds(List<String> transIds);

    String getSelectedChainTransId();

    String getSourceStr();

    void presentResultStr(String str);

    void onEmptySourceStrInput();

    void onEmptyChainInput();

    void setPresenter(Presenter presenter);
}
