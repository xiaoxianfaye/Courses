package fayelab.tdd.stringtransformer.instruction.addall;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.BiConsumer;
import java.util.function.Function;
import java.util.function.Supplier;

import fayelab.tdd.stringtransformer.instruction.addall.Entry.Key;
import fayelab.tdd.stringtransformer.instruction.addall.ValidatingResult.FailedReason;

import static java.util.Arrays.asList;
import static fayelab.tdd.stringtransformer.instruction.addall.Interaction.*;
import static fayelab.tdd.stringtransformer.instruction.addall.Entry.*;
import static fayelab.tdd.stringtransformer.instruction.addall.Entry.Key.*;
import static fayelab.tdd.stringtransformer.instruction.addall.ParamValidatingRule.*;
import static fayelab.tdd.stringtransformer.instruction.addall.OperData.*;

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
        view.setPresenter(this);
    }

    public void init()
    {
        availTranses = businessLogic.getAllTranses();
        view.onInit(interactionData(
                entry(AVAIL_TRANSES, availTranses),
                entry(AVAIL_SELECTED_INDEX, availSelectedIndex)));
    }

    public void addTrans()
    {
        operTrans(operData(Optional.of(this::collectViewDataForAdd), 
                           Optional.of(this::buildParamValidatingRulesForAdd), 
                           this::updatePresenterDataForAdd, 
                           this::presentViewDataForAdd));
    }

    private Optional<String> collectViewDataForAdd()
    {
        return Optional.ofNullable(view.collectAddTransData().get(AVAIL_SELECTED_TRANS).toStr());
    }

    private List<ParamValidatingRule<?>> buildParamValidatingRulesForAdd(Optional<String> viewData)
    {
        String availSelectedTrans = viewData.orElse(NONE_SELECTED_TRANS);
        return asList(
                paramValidatingRule(availSelectedTrans, Presenter::transNotSpecified, 
                        FailedReason.AVAIL_TRANS_NOT_SPECIFIED),
                paramValidatingRule(availSelectedTrans, this::alreadyExistedInChain, 
                        FailedReason.ADD_ALREADY_EXISTED_IN_CHAIN_TRANS));
    }

    private void updatePresenterDataForAdd(Optional<String> viewData, Optional<ValidatingResult> opValidatingResult)
    {
        String availSelectedTrans = viewData.orElse(NONE_SELECTED_TRANS);
        ValidatingResult validatingResult = opValidatingResult.get();
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
        view.onAddTrans(buildPresentViewDataForBuildingTransChain());
    }

    public void removeTrans()
    {
        operTrans(operData(Optional.of(this::collectViewDataForRemove), 
                           Optional.of(this::buildParamValidatingRulesForRemove), 
                           this::updatePresenterDataForRemove, 
                           this::presentViewDataForRemove));
    }

    private Optional<String> collectViewDataForRemove()
    {
        Map<Key, Value<?>> removeTransData = view.collectRemoveTransData();
        String chainSelectedTrans = removeTransData != null ? removeTransData.get(CHAIN_SELECTED_TRANS).toStr() : null;
        return Optional.ofNullable(chainSelectedTrans);
    }

    private List<ParamValidatingRule<?>> buildParamValidatingRulesForRemove(Optional<String> viewData)
    {
        String chainSelectedTrans = viewData.orElse(NONE_SELECTED_TRANS);
        return asList(
                paramValidatingRule(chainTranses, Presenter::emptyList, 
                        FailedReason.CHAIN_EMPTY),
                paramValidatingRule(chainSelectedTrans, Presenter::transNotSpecified, 
                        FailedReason.CHAIN_TRANS_NOT_SPECIFIED));
    }

    private void updatePresenterDataForRemove(Optional<String> viewData, Optional<ValidatingResult> opValidatingResult)
    {
        String chainSelectedTrans = viewData.orElse(NONE_SELECTED_TRANS);
        ValidatingResult validatingResult = opValidatingResult.get();
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

    private void presentViewDataForRemove()
    {
        view.onRemoveTrans(buildPresentViewDataForBuildingTransChain());
    }

    public void removeAllTranses()
    {
        operTrans(operData(Optional.empty(), 
                           Optional.of(this::buildParamValidatingRulesForRemoveAll), 
                           this::updatePresenterDataForRemoveAll, 
                           this::presentViewDataForRemoveAll));
    }

    private List<ParamValidatingRule<?>> buildParamValidatingRulesForRemoveAll(Optional<?> emptyViewData)
    {
        return asList(paramValidatingRule(chainTranses, Presenter::emptyList, FailedReason.CHAIN_EMPTY));
    }

    private void updatePresenterDataForRemoveAll(Optional<?> emptyViewData, Optional<ValidatingResult> opValidatingResult)
    {
        ValidatingResult validatingResult = opValidatingResult.get();
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

    private void presentViewDataForRemoveAll()
    {
        view.onRemoveAllTranses(buildPresentViewDataForBuildingTransChain());
    }

    public void applyTransChain()
    {
        operTrans(operData(Optional.of(this::collectViewDataForApply), 
                           Optional.of(this::buildParamValidatingRulesForApply), 
                           this::updatePresenterDataForApply, 
                           this::presentViewDataForApply));
    }

    private Optional<String> collectViewDataForApply()
    {
        return Optional.ofNullable(view.collectApplyTransChainData().get(SOURCE_STR).toStr());
    }

    private List<ParamValidatingRule<?>> buildParamValidatingRulesForApply(Optional<String> viewData)
    {
        String sourceStr = viewData.get();
        return asList(
                paramValidatingRule(sourceStr, Presenter::emptyStr, 
                        FailedReason.SOURCE_STR_EMPTY),
                paramValidatingRule(sourceStr, Presenter::illegalSourceStr, 
                        FailedReason.SOURCE_STR_ILLEGAL),
                paramValidatingRule(chainTranses, Presenter::emptyList, 
                        FailedReason.CHAIN_EMPTY));
    }

    private void updatePresenterDataForApply(Optional<String> viewData, Optional<ValidatingResult> opValidatingResult)
    {
        ValidatingResult validatingResult = opValidatingResult.get();
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
        view.onApplyTransChain(interactionData(
                entry(RESULT_STR, resultStr),
                entry(AVAIL_SELECTED_INDEX, availSelectedIndex)));
    }

    public void addAllTranses()
    {
        operTrans(operData(Optional.empty(), 
                           Optional.empty(), 
                           this::updatePresenterDataForAddAll, 
                           this::presentViewDataForAddAll));
    }

    private void updatePresenterDataForAddAll(Optional<?> emptyViewData, Optional<?> emptyValidatingResult)
    {
        chainTranses.clear();
        chainTranses.addAll(availTranses);
        updateChainSelectedIndexForAddAll();
        updateAvailSelectedIndexForAddAll();
    }

    private void updateChainSelectedIndexForAddAll()
    {
        chainSelectedIndex = chainTranses.size() - 1;
    }

    private void updateAvailSelectedIndexForAddAll()
    {
        availSelectedIndex = 0;
    }

    private void presentViewDataForAddAll()
    {
        view.onAddAllTranses(buildPresentViewDataForBuildingTransChain());
    }

    private <T> void operTrans(OperData<T> operData)
    {
        Optional<T> viewData = operData.getCollectViewDataFunc().orElse(() -> Optional.empty()).get();
        Optional<ValidatingResult> validatingResult = 
                operData.getBuildParamValidatingRulesFunc()
                        .flatMap(func -> Optional.of(validate(func.apply(viewData))));
        operData.getUpdatePresenterDataFunc().accept(viewData, validatingResult);
        operData.getPresentViewDataFunc().run();
    }

    private Map<Key, Value<?>> buildPresentViewDataForBuildingTransChain()
    {
        return interactionData(
                entry(CHAIN_TRANSES, chainTranses),
                entry(CHAIN_SELECTED_INDEX, chainSelectedIndex),
                entry(AVAIL_SELECTED_INDEX, availSelectedIndex));
    }

    private ValidatingResult validate(List<ParamValidatingRule<?>> rules)
    {
        ValidatingResult validatingResult = Validator.validate(rules);
        if(!validatingResult.isSucceeded())
        {
            view.onValidatingFailed(interactionData(
                    entry(VALIDATING_FAILED_REASON, validatingResult.getFailedReason())));
        }
        return validatingResult;
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
    private Optional<Function<Optional<T>, List<ParamValidatingRule<?>>>> buildParamValidatingRulesFunc;
    private BiConsumer<Optional<T>, Optional<ValidatingResult>> updatePresenterDataFunc;
    private Runnable presentViewDataFunc;

    static <T> OperData<T> operData(
            Optional<Supplier<Optional<T>>> collectViewDataFunc, 
            Optional<Function<Optional<T>, List<ParamValidatingRule<?>>>> buildParamValidatingRulesFunc, 
            BiConsumer<Optional<T>, Optional<ValidatingResult>> updatePresenterDataFunc, 
            Runnable presentViewDataFunc)
    {
        return new OperData<>(collectViewDataFunc, buildParamValidatingRulesFunc, 
                updatePresenterDataFunc, presentViewDataFunc);
    }

    private OperData(Optional<Supplier<Optional<T>>> collectViewDataFunc, 
                     Optional<Function<Optional<T>, List<ParamValidatingRule<?>>>> buildParamValidatingRulesFunc, 
                     BiConsumer<Optional<T>, Optional<ValidatingResult>> updatePresenterDataFunc, 
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

    public Optional<Function<Optional<T>, List<ParamValidatingRule<?>>>> getBuildParamValidatingRulesFunc()
    {
        return buildParamValidatingRulesFunc;
    }

    public BiConsumer<Optional<T>, Optional<ValidatingResult>> getUpdatePresenterDataFunc()
    {
        return updatePresenterDataFunc;
    }

    public Runnable getPresentViewDataFunc()
    {
        return presentViewDataFunc;
    }
}