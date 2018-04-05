package fayelab.tdd.stringtransformer.instruction.original;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import fayelab.tdd.stringtransformer.instruction.original.Entry.Key;
import fayelab.tdd.stringtransformer.instruction.original.ValidatingResult.FailedReason;

import static java.util.Arrays.asList;
import static fayelab.tdd.stringtransformer.instruction.original.Interaction.*;
import static fayelab.tdd.stringtransformer.instruction.original.Entry.*;
import static fayelab.tdd.stringtransformer.instruction.original.Entry.Key.*;
import static fayelab.tdd.stringtransformer.instruction.original.ParamValidatingRule.*;

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
        String availSelectedTrans = view.collectAddTransData().get(AVAIL_SELECTED_TRANS).toStr();

        ValidatingResult validatingResult = validate(buildParamValidatingRulesForAdd(availSelectedTrans));
        if(validatingResult.isSucceeded())
        {
            chainTranses.add(availSelectedTrans);
        }
        updateChainSelectedIndexForAdd(availSelectedTrans, validatingResult.getFailedReason());
        updateAvailSelectedIndexForAdd(availSelectedTrans, validatingResult.getFailedReason());

        view.onAddTrans(interactionData(
                entry(CHAIN_TRANSES, chainTranses),
                entry(CHAIN_SELECTED_INDEX, chainSelectedIndex),
                entry(AVAIL_SELECTED_INDEX, availSelectedIndex)));
    }

    private List<ParamValidatingRule<?>> buildParamValidatingRulesForAdd(String availSelectedTrans)
    {
        return asList(
                paramValidatingRule(availSelectedTrans, Presenter::transNotSpecified, 
                        FailedReason.AVAIL_TRANS_NOT_SPECIFIED),
                paramValidatingRule(availSelectedTrans, this::alreadyExistedInChain, 
                        FailedReason.ADD_ALREADY_EXISTED_IN_CHAIN_TRANS));
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
        Map<Key, Value<?>> removeTransData = view.collectRemoveTransData();
        String chainSelectedTrans = removeTransData != null ? removeTransData.get(CHAIN_SELECTED_TRANS).toStr() : null;

        ValidatingResult validatingResult = validate(buildParamValidatingRulesForRemove(chainSelectedTrans));
        updateChainSelectedIndexForRemove(chainSelectedTrans, validatingResult.getFailedReason());
        if(validatingResult.isSucceeded())
        {
            chainTranses.remove(chainSelectedTrans);
        }
        updateAvailSelectedIndexForRemove(chainSelectedTrans, validatingResult.getFailedReason());

        view.onRemoveTrans(interactionData(
                entry(CHAIN_TRANSES, chainTranses),
                entry(AVAIL_SELECTED_INDEX, availSelectedIndex),
                entry(CHAIN_SELECTED_INDEX, chainSelectedIndex)));
    }

    private List<ParamValidatingRule<?>> buildParamValidatingRulesForRemove(String chainSelectedTrans)
    {
        return asList(
                paramValidatingRule(chainTranses, Presenter::emptyList, 
                        FailedReason.CHAIN_EMPTY),
                paramValidatingRule(chainSelectedTrans, Presenter::transNotSpecified, 
                        FailedReason.CHAIN_TRANS_NOT_SPECIFIED));
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
        ValidatingResult validatingResult = validate(buildParamValidatingRulesForRemoveAll());
        if(validatingResult.isSucceeded())
        {
            chainTranses.clear();
        }
        updateChainSelectedIndexForRemoveAll();
        updateAvailSelectedIndexForRemoveAll(validatingResult.getFailedReason());

        view.onRemoveAllTranses(interactionData(
                entry(CHAIN_TRANSES, chainTranses),
                entry(CHAIN_SELECTED_INDEX, chainSelectedIndex),
                entry(AVAIL_SELECTED_INDEX, availSelectedIndex)));
    }

    private List<ParamValidatingRule<?>> buildParamValidatingRulesForRemoveAll()
    {
        return asList(paramValidatingRule(chainTranses, Presenter::emptyList, FailedReason.CHAIN_EMPTY));
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
}