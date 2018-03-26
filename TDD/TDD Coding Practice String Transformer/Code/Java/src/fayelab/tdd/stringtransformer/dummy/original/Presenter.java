package fayelab.tdd.stringtransformer.dummy.original;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.BiConsumer;
import java.util.function.Function;
import java.util.function.Supplier;

import fayelab.tdd.stringtransformer.dummy.original.ValidatingResult.FailedReason;

import static java.util.Arrays.asList;
import static fayelab.tdd.stringtransformer.dummy.original.Trans.NONE_SELECTED_INDEX;
import static fayelab.tdd.stringtransformer.dummy.original.Trans.NONE_SELECTED_TRANS;

public class Presenter
{
    private View view;
    private BusinessLogic businessLogic;

    private int availSelectedIndex = 0;
    private int chainSelectedIndex = NONE_SELECTED_INDEX;
    private List<String> availTranses;
    private List<String> chainTranses = new ArrayList<>();

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
        operTrans(new OperData<String>(Optional.of(this::collectViewDataForAdd), 
                                       this::buildParamValidatingRulesForAdd, 
                                       this::updatePresenterDataForAdd, 
                                       this::presentViewDataForAdd));
    }

    private Optional<String> collectViewDataForAdd()
    {
        return Optional.ofNullable(view.getAvailSelectedTrans());
    }

    private List<ParamValidatingRule<?>> buildParamValidatingRulesForAdd(Optional<String> viewData)
    {
        String availSelectedTrans = viewData.orElse(NONE_SELECTED_TRANS);
        return asList(
                new ParamValidatingRule<>(availSelectedTrans, Presenter::transNotSpecified, 
                        view::notifyAvailTransNotSpecified, FailedReason.AVAIL_TRANS_NOT_SPECIFIED),
                new ParamValidatingRule<>(availSelectedTrans, this::alreadyExistedInChain, 
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

    private void presentViewDataForAdd()
    {
        view.presentChainTranses(chainTranses);
        view.setChainSelectedIndex(chainSelectedIndex);
        view.setAvailSelectedIndex(availSelectedIndex);
    }

    public void removeTrans()
    {
        String chainSelectedTrans = view.getChainSelectedTrans();

        ValidatingResult validatingResult = Validator.validate(buildParamValidatingRulesForRemove(chainSelectedTrans));
        updateChainSelectedIndexForRemove(chainSelectedTrans, validatingResult.getFailedReason());
        if(validatingResult.isSucceeded())
        {
            chainTranses.remove(chainSelectedTrans);
        }
        updateAvailSelectedIndexForRemove(chainSelectedTrans, validatingResult.getFailedReason());

        view.presentChainTranses(chainTranses);
        view.setAvailSelectedIndex(availSelectedIndex);
        view.setChainSelectedIndex(chainSelectedIndex);
    }

    private List<ParamValidatingRule<?>> buildParamValidatingRulesForRemove(String chainSelectedTrans)
    {
        return asList(
                new ParamValidatingRule<>(chainTranses, Presenter::emptyList, 
                        view::notifyChainEmpty, FailedReason.CHAIN_EMPTY),
                new ParamValidatingRule<>(chainSelectedTrans, Presenter::transNotSpecified, 
                        view::notifyChainTransNotSpecified, FailedReason.CHAIN_TRANS_NOT_SPECIFIED));
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
        ValidatingResult validatingResult = Validator.validate(buildParamValidatingRulesForRemoveAll());
        if(validatingResult.isSucceeded())
        {
            chainTranses.clear();
        }
        updateChainSelectedIndexForRemoveAll();
        updateAvailSelectedIndexForRemoveAll(validatingResult.getFailedReason());

        view.presentChainTranses(chainTranses);
        view.setChainSelectedIndex(chainSelectedIndex);
        view.setAvailSelectedIndex(availSelectedIndex);
    }

    private List<ParamValidatingRule<?>> buildParamValidatingRulesForRemoveAll()
    {
        return asList(
                new ParamValidatingRule<>(chainTranses, Presenter::emptyList, 
                        view::notifyChainEmpty, FailedReason.CHAIN_EMPTY));
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
        String sourceStr = view.getSourceStr();

        ValidatingResult validatingResult = Validator.validate(buildParamValidatingRulesForApply(sourceStr));
        String resultStr = validatingResult.isSucceeded() ? 
                businessLogic.transform(sourceStr, chainTranses) : "";
        updateAvailSelectedIndexForApply(validatingResult.getFailedReason());

        view.presentResultStr(resultStr);
        view.setAvailSelectedIndex(availSelectedIndex);
    }

    private List<ParamValidatingRule<?>> buildParamValidatingRulesForApply(String sourceStr)
    {
        return asList(
                new ParamValidatingRule<>(sourceStr, Presenter::emptyStr, 
                        view::notifySourceStrEmpty, FailedReason.SOURCE_STR_EMPTY),
                new ParamValidatingRule<>(sourceStr, Presenter::illegalSourceStr, 
                        view::notifySourceStrIllegal, FailedReason.SOURCE_STR_ILLEGAL),
                new ParamValidatingRule<>(chainTranses, Presenter::emptyList, 
                        view::notifyChainEmpty, FailedReason.CHAIN_EMPTY));
    }

    private void updateAvailSelectedIndexForApply(FailedReason failedReason)
    {
        if(failedReason == FailedReason.CHAIN_EMPTY)
        {
            availSelectedIndex = 0;
        }
    }

    private <T> void operTrans(OperData<T> operData)
    {
        Optional<T> viewData = operData.getCollectViewDataFunc().orElse(() -> Optional.empty()).get();
        ValidatingResult validatingResult = Validator.validate(operData.getBuildParamValidatingRulesFunc().apply(viewData));
        operData.getUpdatePresenterDataFunc().accept(viewData, validatingResult);
        operData.getPresentViewDataFunc().run();
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

    public OperData(Optional<Supplier<Optional<T>>> collectViewDataFunc, 
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
