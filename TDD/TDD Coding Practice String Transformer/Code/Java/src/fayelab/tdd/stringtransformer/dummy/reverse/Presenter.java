package fayelab.tdd.stringtransformer.dummy.reverse;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.BiConsumer;
import java.util.function.Function;
import java.util.function.Supplier;

import fayelab.tdd.stringtransformer.dummy.reverse.ValidatingResult.FailedReason;

import static java.util.Arrays.asList;
import static fayelab.tdd.stringtransformer.dummy.reverse.Trans.NONE_SELECTED_INDEX;
import static fayelab.tdd.stringtransformer.dummy.reverse.Trans.NONE_SELECTED_TRANS;
import static fayelab.tdd.stringtransformer.dummy.reverse.OperData.operData;
import static fayelab.tdd.stringtransformer.dummy.reverse.ParamValidatingRule.paramValidatingRule;

public class Presenter
{
    private View view;
    private BusinessLogic businessLogic;

    private int availSelectedIndex = 0;
    private int chainSelectedIndex = NONE_SELECTED_INDEX;
    private List<String> availTranses;
    private List<String> chainTranses = new ArrayList<>();
    private String resultStr;

    public Presenter(View view, BusinessLogic businessLogic)
    {
        this.view = view;
        this.businessLogic = businessLogic;
        this.view.setPresenter(this);
    }

    public void init()
    {
        availTranses = businessLogic.getAllTranses();
        view.presentAvailTranses(availTranses);
        view.setAvailSelectedIndex(availSelectedIndex);
    }

    public void addTrans()
    {
        operTrans(operData(Optional.of(this::collectViewDataForAdd), 
                           this::buildParamValidatingRulesForAdd, 
                           this::updatePresenterDataForAdd, 
                           this::presentViewDataForBuildTransChain));
    }

    private Optional<String> collectViewDataForAdd()
    {
        return Optional.ofNullable(view.getAvailSelectedTrans());
    }

    private List<ParamValidatingRule<?>> buildParamValidatingRulesForAdd(Optional<String> viewData)
    {
        String availSelectedTrans = viewData.orElse(NONE_SELECTED_TRANS);
        return asList(
                paramValidatingRule(availSelectedTrans, Presenter::transNotSpecified, 
                        view::notifyAvailTransNotSpecified, FailedReason.AVAIL_TRANS_NOT_SPECIFIED),
                paramValidatingRule(availSelectedTrans, this::alreadyExistedInChain, 
                        view::notifyAddAlreadyExistedInChainTrans, FailedReason.ADD_ALREADY_EXISTED_IN_CHAIN_TRANS));
    }

    private void updatePresenterDataForAdd(Optional<String> viewData, ValidatingResult validatingResult)
    {
        String availSelectedTrans = viewData.orElse(NONE_SELECTED_TRANS);
        if(validatingResult.isSucceeded())
        {
            chainTranses.add(availSelectedTrans);
        }
        updateChainSelectedIndexForAdd(availSelectedTrans, validatingResult.getFailedReason());
        updateAvailSelectedIndexForAdd(availSelectedTrans, validatingResult.getFailedReason());
    }

    private void updateChainSelectedIndexForAdd(String availSelectedTrans, FailedReason failedReason)
    {
        if(failedReason == FailedReason.AVAIL_TRANS_NOT_SPECIFIED)
        {
            return;
        }

        chainSelectedIndex = chainTranses.indexOf(availSelectedTrans);
    }

    private void updateAvailSelectedIndexForAdd(String availSelectedTrans, FailedReason failedReason)
    {
        if(failedReason == FailedReason.AVAIL_TRANS_NOT_SPECIFIED)
        {
            availSelectedIndex = 0;
            return;
        }

        int selectedIndex = availTranses.indexOf(availSelectedTrans);
        if(failedReason == FailedReason.ADD_ALREADY_EXISTED_IN_CHAIN_TRANS)
        {
            availSelectedIndex = selectedIndex;
        }
        else
        {
            availSelectedIndex = isLastIndex(selectedIndex, availTranses) ? 0 : selectedIndex + 1;
        }
    }

    public void removeTrans()
    {
        operTrans(operData(Optional.of(this::collectViewDataForRemove), 
                           this::buildParamValidatingRulesForRemove, 
                           this::updatePresenterDataForRemove, 
                           this::presentViewDataForBuildTransChain));
    }

    private Optional<String> collectViewDataForRemove()
    {
        return Optional.ofNullable(view.getChainSelectedTrans());
    }

    private List<ParamValidatingRule<?>> buildParamValidatingRulesForRemove(Optional<String> viewData)
    {
        String chainSelectedTrans = viewData.orElse(NONE_SELECTED_TRANS);
        return asList(
                paramValidatingRule(chainTranses, Presenter::emptyList, 
                        view::notifyChainEmpty, FailedReason.CHAIN_EMPTY),
                paramValidatingRule(chainSelectedTrans, Presenter::transNotSpecified, 
                        view::notifyChainTransNotSpecified, FailedReason.CHAIN_TRANS_NOT_SPECIFIED));
    }

    private void updatePresenterDataForRemove(Optional<String> viewData, ValidatingResult validatingResult)
    {
        String chainSelectedTrans = viewData.orElse(NONE_SELECTED_TRANS);
        updateChainSelectedIndexForRemove(chainSelectedTrans, validatingResult.getFailedReason());
        if(validatingResult.isSucceeded())
        {
            chainTranses.remove(chainSelectedTrans);
        }
        updateAvailSelectedIndexForRemove(chainSelectedTrans, validatingResult.getFailedReason());
    }

    private void updateChainSelectedIndexForRemove(String chainSelectedTrans, FailedReason failedReason)
    {
        if(failedReason == FailedReason.CHAIN_EMPTY)
        {
            chainSelectedIndex = NONE_SELECTED_INDEX;
            return;
        }

        if(failedReason == FailedReason.CHAIN_TRANS_NOT_SPECIFIED)
        {
            chainSelectedIndex = 0;
            return;
        }

        if(chainTranses.size() == 1)
        {
            chainSelectedIndex = NONE_SELECTED_INDEX;
            return;
        }

        int selectedIndex = chainTranses.indexOf(chainSelectedTrans);
        chainSelectedIndex = isLastIndex(selectedIndex, chainTranses) ? 0 : selectedIndex;
    }

    private void updateAvailSelectedIndexForRemove(String chainSelectedTrans, FailedReason failedReason)
    {
        if(failedReason == FailedReason.CHAIN_EMPTY)
        {
            availSelectedIndex = 0;
            return;
        }

        if(failedReason == FailedReason.CHAIN_TRANS_NOT_SPECIFIED)
        {
            return;
        }

        availSelectedIndex = availTranses.indexOf(chainSelectedTrans);
    }

    public void removeAllTranses()
    {
        operTrans(operData(Optional.empty(), 
                           this::buildParamValidatingRulesForRemoveAll, 
                           this::updatePresenterDataForRemoveAll, 
                           this::presentViewDataForBuildTransChain));
    }

    private List<ParamValidatingRule<?>> buildParamValidatingRulesForRemoveAll(Optional<?> emptyViewData)
    {
        return asList(
                paramValidatingRule(chainTranses, Presenter::emptyList, 
                        view::notifyChainEmpty, FailedReason.CHAIN_EMPTY));
    }

    private void updatePresenterDataForRemoveAll(Optional<?> emptyViewData, ValidatingResult validatingResult)
    {
        if(validatingResult.isSucceeded())
        {
            chainTranses.clear();
        }
        updateChainSelectedIndexForRemoveAll();
        updateAvailSelectedIndexForRemoveAll(validatingResult.getFailedReason());
    }

    private void updateChainSelectedIndexForRemoveAll()
    {
        chainSelectedIndex = NONE_SELECTED_INDEX;
    }

    private void updateAvailSelectedIndexForRemoveAll(FailedReason failedReason)
    {
        if(failedReason == FailedReason.CHAIN_EMPTY)
        {
            return;
        }

        availSelectedIndex = 0;
    }

    public void applyTransChain()
    {
        operTrans(operData(Optional.of(this::collectViewDataForApply), 
                           this::buildParamValidatingRulesForApply, 
                           this::updatePresenterDataForApply, 
                           this::presentViewDataForApply));
    }

    private Optional<String> collectViewDataForApply()
    {
        return Optional.ofNullable(view.getSourceStr());
    }

    private List<ParamValidatingRule<?>> buildParamValidatingRulesForApply(Optional<String> viewData)
    {
        String sourceStr = viewData.get();
        return asList(
                paramValidatingRule(sourceStr, Presenter::emptyStr, 
                        view::notifySourceStrEmpty, FailedReason.SOURCE_STR_EMPTY),
                paramValidatingRule(sourceStr, Presenter::illegalSourceStr, 
                        view::notifySourceStrIllegal, FailedReason.SOURCE_STR_ILLEGAL),
                paramValidatingRule(chainTranses, Presenter::emptyList, 
                        view::notifyChainEmpty, FailedReason.CHAIN_EMPTY));
    }

    private void updatePresenterDataForApply(Optional<String> viewData, ValidatingResult validatingResult)
    {
        resultStr = validatingResult.isSucceeded() ? businessLogic.transform(viewData.get(), chainTranses) : "";
        updateAvailSelectedIndexForApply(validatingResult.getFailedReason());
    }

    private void updateAvailSelectedIndexForApply(FailedReason failedReason)
    {
        if(failedReason == FailedReason.CHAIN_EMPTY)
        {
            availSelectedIndex = 0;
        }
    }

    private void presentViewDataForApply()
    {
        view.presentResultStr(resultStr);
        view.setAvailSelectedIndex(availSelectedIndex);
    }

    private <T> void operTrans(OperData<T> operData)
    {
        Optional<T> viewData = operData.getCollectViewDataFunc().orElse(() -> Optional.empty()).get();
        ValidatingResult validatingResult = Validator.validate(operData.getBuildParamValidatingRulesFunc().apply(viewData));
        operData.getUpdatePresenterDataFunc().accept(viewData, validatingResult);
        operData.getPresentViewDataFunc().run();
    }

    private void presentViewDataForBuildTransChain()
    {
        view.presentChainTranses(chainTranses);
        view.setChainSelectedIndex(chainSelectedIndex);
        view.setAvailSelectedIndex(availSelectedIndex);
    }

    private boolean alreadyExistedInChain(String trans)
    {
        return chainTranses.contains(trans);
    }

    private static boolean isLastIndex(int index, List<?> list)
    {
        return index == list.size() - 1;
    }

    private static boolean transNotSpecified(String trans)
    {
        return trans == NONE_SELECTED_TRANS;
    }

    private static boolean emptyList(List<?> list)
    {
        return list.isEmpty();
    }

    private static boolean emptyStr(String str)
    {
        return str.isEmpty();
    }

    private static boolean illegalSourceStr(String str)
    {
        return str.matches(".*[\u4e00-\u9fa5]+.*");
    }
}

class OperData<T>
{
    private Optional<Supplier<Optional<T>>> collectViewDataFunc;
    private Function<Optional<T>, List<ParamValidatingRule<?>>> buildParamValidatingRulesFunc;
    private BiConsumer<Optional<T>, ValidatingResult> updatePresenterDataFunc;
    private Runnable presentViewDataFunc;

    static <T> OperData<T> operData(
            Optional<Supplier<Optional<T>>> collectViewDataFunc, 
            Function<Optional<T>, List<ParamValidatingRule<?>>> buildParamValidatingRulesFunc, 
            BiConsumer<Optional<T>, ValidatingResult> updatePresenterDataFunc, 
            Runnable presentViewDataFunc)
    {
        return new OperData<>(collectViewDataFunc, buildParamValidatingRulesFunc, 
                updatePresenterDataFunc, presentViewDataFunc);
    }

    private OperData(Optional<Supplier<Optional<T>>> collectViewDataFunc, 
                     Function<Optional<T>, List<ParamValidatingRule<?>>> buildParamValidatingRulesFunc, 
                     BiConsumer<Optional<T>, ValidatingResult> updatePresenterDataFunc, 
                     Runnable presentViewDataFunc)
    {
        this.collectViewDataFunc = collectViewDataFunc;
        this.buildParamValidatingRulesFunc = buildParamValidatingRulesFunc;
        this.updatePresenterDataFunc = updatePresenterDataFunc;
        this.presentViewDataFunc = presentViewDataFunc;
    }

    public Optional<Supplier<Optional<T>>> getCollectViewDataFunc()
    {
        return collectViewDataFunc;
    }

    public Function<Optional<T>, List<ParamValidatingRule<?>>> getBuildParamValidatingRulesFunc()
    {
        return buildParamValidatingRulesFunc;
    }

    public BiConsumer<Optional<T>, ValidatingResult> getUpdatePresenterDataFunc()
    {
        return updatePresenterDataFunc;
    }

    public Runnable getPresentViewDataFunc()
    {
        return presentViewDataFunc;
    }
}
