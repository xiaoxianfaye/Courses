package fayelab.tdd.stringtransformer.dummy.original;

import java.util.ArrayList;
import java.util.List;

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
        String availSelectedTrans = view.getAvailSelectedTrans();

        ValidatingResult validatingResult = validateAddTrans(availSelectedTrans);
        if(validatingResult.isSucceeded())
        {
            chainTranses.add(availSelectedTrans);
        }

        updateChainSelectedIndex(availSelectedTrans, validatingResult.getFailedReason());
        updateAvailSelectedIndex(availSelectedTrans, validatingResult.getFailedReason());

        view.presentChainTranses(chainTranses);
        view.setChainSelectedIndex(chainSelectedIndex);
        view.setAvailSelectedIndex(availSelectedIndex);
    }

    private ValidatingResult validateAddTrans(String availSelectedTrans)
    {
        List<ParamValidatingRule<?>> rules = asList(
                new ParamValidatingRule<>(availSelectedTrans, Presenter::transNotSpecified, 
                        () -> view.notifyAvailTransNotSpecified(), FailedReason.AVAIL_TRANS_NOT_SPECIFIED),
                new ParamValidatingRule<>(availSelectedTrans, this::alreadyExistedInChain, 
                        () -> view.notifyAddAlreadyExistedInChainTrans(), FailedReason.ADD_ALREADY_EXISTED_IN_CHAIN_TRANS));

        return Validator.validate(rules);
    }

    private void updateChainSelectedIndex(String availSelectedTrans, FailedReason failedReason)
    {
        if(failedReason == FailedReason.AVAIL_TRANS_NOT_SPECIFIED)
        {
            return;
        }

        chainSelectedIndex = chainTranses.indexOf(availSelectedTrans);
    }

    private void updateAvailSelectedIndex(String availSelectedTrans, FailedReason failedReason)
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
}
