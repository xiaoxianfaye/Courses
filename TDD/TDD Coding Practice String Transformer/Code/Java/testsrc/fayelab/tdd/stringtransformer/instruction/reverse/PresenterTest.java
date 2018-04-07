package fayelab.tdd.stringtransformer.instruction.reverse;

import junit.framework.TestCase;

import fayelab.tdd.stringtransformer.instruction.reverse.ValidatingResult.FailedReason;

import static java.util.Arrays.asList;
import static fayelab.tdd.stringtransformer.instruction.reverse.Interaction.*;
import static fayelab.tdd.stringtransformer.instruction.reverse.Entry.*;
import static fayelab.tdd.stringtransformer.instruction.reverse.Entry.Key.*;
import static fayelab.tdd.stringtransformer.instruction.reverse.Trans.*;

public class PresenterTest extends TestCase
{
    private ViewStub viewStub;
    private BusinessLogicStub businessLogicStub;
    private Presenter presenter;

    @Override
    protected void setUp()
    {
        viewStub = new ViewStub();
        businessLogicStub = new BusinessLogicStub();
        presenter = new Presenter(viewStub, businessLogicStub);

        presenter.init();
    }

    public void test_init()
    {
        assertEquals(interactionData(
                entry(AVAIL_TRANSES, asList(UPPER_TRANS, LOWER_TRANS, TRIM_PREFIX_SPACES_TRANS)),
                entry(AVAIL_SELECTED_INDEX, 0)), viewStub.getOnInitData());
    }

    public void test_add_not_the_last_trans()
    {
        viewStub.setAddTransData(interactionData(entry(AVAIL_SELECTED_TRANS, UPPER_TRANS)));

        presenter.addTrans();

        assertEquals(interactionData(
                entry(CHAIN_TRANSES, asList(UPPER_TRANS)),
                entry(CHAIN_SELECTED_INDEX, 0),
                entry(AVAIL_SELECTED_INDEX, 1)), viewStub.getOnAddTransData());
    }

    public void test_add_the_last_trans()
    {
        viewStub.setAddTransData(interactionData(entry(AVAIL_SELECTED_TRANS, TRIM_PREFIX_SPACES_TRANS)));

        presenter.addTrans();

        assertEquals(interactionData(
                entry(CHAIN_TRANSES, asList(TRIM_PREFIX_SPACES_TRANS)),
                entry(CHAIN_SELECTED_INDEX, 0),
                entry(AVAIL_SELECTED_INDEX, 0)), viewStub.getOnAddTransData());
    }

    public void test_add_already_existed_in_chain_trans()
    {
        viewStub.setAddTransData(interactionData(entry(AVAIL_SELECTED_TRANS, UPPER_TRANS)));
        presenter.addTrans();
        viewStub.setAddTransData(interactionData(entry(AVAIL_SELECTED_TRANS, LOWER_TRANS)));
        presenter.addTrans();
        viewStub.setAddTransData(interactionData(entry(AVAIL_SELECTED_TRANS, LOWER_TRANS)));

        presenter.addTrans();

        assertEquals(interactionData(
                entry(VALIDATING_FAILED_REASON, FailedReason.ADD_ALREADY_EXISTED_IN_CHAIN_TRANS)),
                viewStub.getOnValidatingFailedData());
        assertEquals(interactionData(
                entry(CHAIN_TRANSES, asList(UPPER_TRANS, LOWER_TRANS)),
                entry(CHAIN_SELECTED_INDEX, 1),
                entry(AVAIL_SELECTED_INDEX, 1)), viewStub.getOnAddTransData());
    }

    public void test_add_trans_but_avail_trans_not_specified()
    {
        viewStub.setAddTransData(interactionData(entry(AVAIL_SELECTED_TRANS, UPPER_TRANS)));
        presenter.addTrans();
        viewStub.setAddTransData(interactionData(entry(AVAIL_SELECTED_TRANS, LOWER_TRANS)));
        presenter.addTrans();
        viewStub.setAddTransData(interactionData(entry(AVAIL_SELECTED_TRANS, NONE_SELECTED_TRANS)));

        presenter.addTrans();

        assertEquals(interactionData(
                entry(VALIDATING_FAILED_REASON, FailedReason.AVAIL_TRANS_NOT_SPECIFIED)),
                viewStub.getOnValidatingFailedData());
        assertEquals(interactionData(
                entry(CHAIN_TRANSES, asList(UPPER_TRANS, LOWER_TRANS)),
                entry(CHAIN_SELECTED_INDEX, 1),
                entry(AVAIL_SELECTED_INDEX, 0)), viewStub.getOnAddTransData());
    }

    public void test_remove_not_the_last_trans_when_chain_has_more_than_one_transes()
    {
        viewStub.setAddTransData(interactionData(entry(AVAIL_SELECTED_TRANS, UPPER_TRANS)));
        presenter.addTrans();
        viewStub.setAddTransData(interactionData(entry(AVAIL_SELECTED_TRANS, LOWER_TRANS)));
        presenter.addTrans();
        viewStub.setAddTransData(interactionData(entry(AVAIL_SELECTED_TRANS, TRIM_PREFIX_SPACES_TRANS)));
        presenter.addTrans();
        viewStub.setRemoveTransData(interactionData(entry(CHAIN_SELECTED_TRANS, LOWER_TRANS)));

        presenter.removeTrans();

        assertEquals(interactionData(
                entry(CHAIN_TRANSES, asList(UPPER_TRANS, TRIM_PREFIX_SPACES_TRANS)),
                entry(AVAIL_SELECTED_INDEX, 1),
                entry(CHAIN_SELECTED_INDEX, 1)), viewStub.getOnRemoveTransData());
    }

    public void test_remove_the_last_trans_when_chain_has_more_than_one_transes()
    {
        viewStub.setAddTransData(interactionData(entry(AVAIL_SELECTED_TRANS, UPPER_TRANS)));
        presenter.addTrans();
        viewStub.setAddTransData(interactionData(entry(AVAIL_SELECTED_TRANS, LOWER_TRANS)));
        presenter.addTrans();
        viewStub.setAddTransData(interactionData(entry(AVAIL_SELECTED_TRANS, TRIM_PREFIX_SPACES_TRANS)));
        presenter.addTrans();
        viewStub.setRemoveTransData(interactionData(entry(CHAIN_SELECTED_TRANS, TRIM_PREFIX_SPACES_TRANS)));

        presenter.removeTrans();

        assertEquals(interactionData(
                entry(CHAIN_TRANSES, asList(UPPER_TRANS, LOWER_TRANS)),
                entry(AVAIL_SELECTED_INDEX, 2),
                entry(CHAIN_SELECTED_INDEX, 0)), viewStub.getOnRemoveTransData());
    }

    public void test_remove_a_trans_when_chain_has_only_one_transes()
    {
        viewStub.setAddTransData(interactionData(entry(AVAIL_SELECTED_TRANS, UPPER_TRANS)));
        presenter.addTrans();
        viewStub.setRemoveTransData(interactionData(entry(CHAIN_SELECTED_TRANS, UPPER_TRANS)));

        presenter.removeTrans();

        assertEquals(interactionData(
                entry(CHAIN_TRANSES, asList()),
                entry(AVAIL_SELECTED_INDEX, 0),
                entry(CHAIN_SELECTED_INDEX, NONE_SELECTED_INDEX)), viewStub.getOnRemoveTransData());
    }

    public void test_remove_trans_when_chain_is_not_empty_but_chain_trans_not_specified()
    {
        viewStub.setAddTransData(interactionData(entry(AVAIL_SELECTED_TRANS, UPPER_TRANS)));
        presenter.addTrans();
        viewStub.setRemoveTransData(interactionData(entry(CHAIN_SELECTED_TRANS, NONE_SELECTED_TRANS)));

        presenter.removeTrans();

        assertEquals(interactionData(
                entry(VALIDATING_FAILED_REASON, FailedReason.CHAIN_TRANS_NOT_SPECIFIED)),
                viewStub.getOnValidatingFailedData());
        assertEquals(interactionData(
                entry(CHAIN_TRANSES, asList(UPPER_TRANS)),
                entry(AVAIL_SELECTED_INDEX, 1),
                entry(CHAIN_SELECTED_INDEX, 0)), viewStub.getOnRemoveTransData());
    }

    public void test_remove_trans_when_chain_is_empty()
    {
        presenter.removeTrans();

        assertEquals(interactionData(
                entry(VALIDATING_FAILED_REASON, FailedReason.CHAIN_EMPTY)),
                viewStub.getOnValidatingFailedData());
        assertEquals(interactionData(
                entry(CHAIN_TRANSES, asList()),
                entry(AVAIL_SELECTED_INDEX, 0),
                entry(CHAIN_SELECTED_INDEX, NONE_SELECTED_INDEX)), viewStub.getOnRemoveTransData());
    }

    public void test_remove_all_transes_when_chain_is_not_empty()
    {
        viewStub.setAddTransData(interactionData(entry(AVAIL_SELECTED_TRANS, UPPER_TRANS)));
        presenter.addTrans();
        viewStub.setAddTransData(interactionData(entry(AVAIL_SELECTED_TRANS, LOWER_TRANS)));
        presenter.addTrans();

        presenter.removeAllTranses();

        assertEquals(interactionData(
                entry(CHAIN_TRANSES, asList()),
                entry(AVAIL_SELECTED_INDEX, 0),
                entry(CHAIN_SELECTED_INDEX, NONE_SELECTED_INDEX)), viewStub.getOnRemoveAllTransesData());
    }

    public void test_remove_all_transes_when_chain_is_empty()
    {
        viewStub.setAddTransData(interactionData(entry(AVAIL_SELECTED_TRANS, LOWER_TRANS)));
        presenter.addTrans();
        viewStub.setRemoveTransData(interactionData(entry(CHAIN_SELECTED_TRANS, LOWER_TRANS)));
        presenter.removeTrans();

        presenter.removeAllTranses();

        assertEquals(interactionData(
                entry(VALIDATING_FAILED_REASON, FailedReason.CHAIN_EMPTY)),
                viewStub.getOnValidatingFailedData());
        assertEquals(interactionData(
                entry(CHAIN_TRANSES, asList()),
                entry(AVAIL_SELECTED_INDEX, 1),
                entry(CHAIN_SELECTED_INDEX, NONE_SELECTED_INDEX)), viewStub.getOnRemoveAllTransesData());
    }

    public void test_apply_trans_chain()
    {
        viewStub.setAddTransData(interactionData(entry(AVAIL_SELECTED_TRANS, UPPER_TRANS)));
        presenter.addTrans();
        viewStub.setApplyTransChainData(interactionData(entry(SOURCE_STR, "Hello, world.")));

        presenter.applyTransChain();

        assertEquals(interactionData(
                entry(RESULT_STR, "HELLO, WORLD."), entry(AVAIL_SELECTED_INDEX, 1)), 
                viewStub.getOnApplyTransChainData());
    }

    public void test_apply_trans_chain_when_source_str_is_empty()
    {
        viewStub.setAddTransData(interactionData(entry(AVAIL_SELECTED_TRANS, UPPER_TRANS)));
        presenter.addTrans();
        viewStub.setApplyTransChainData(interactionData(entry(SOURCE_STR, "")));

        presenter.applyTransChain();

        assertEquals(interactionData(
                entry(VALIDATING_FAILED_REASON, FailedReason.SOURCE_STR_EMPTY)),
                viewStub.getOnValidatingFailedData());
        assertEquals(interactionData(
                entry(RESULT_STR, ""), entry(AVAIL_SELECTED_INDEX, 1)), 
                viewStub.getOnApplyTransChainData());
    }

    public void test_apply_trans_chain_when_source_str_is_illegal()
    {
        viewStub.setAddTransData(interactionData(entry(AVAIL_SELECTED_TRANS, UPPER_TRANS)));
        presenter.addTrans();
        viewStub.setApplyTransChainData(interactionData(entry(SOURCE_STR, "aÖÐÎÄb")));

        presenter.applyTransChain();

        assertEquals(interactionData(
                entry(VALIDATING_FAILED_REASON, FailedReason.SOURCE_STR_ILLEGAL)),
                viewStub.getOnValidatingFailedData());
        assertEquals(interactionData(
                entry(RESULT_STR, ""), entry(AVAIL_SELECTED_INDEX, 1)), 
                viewStub.getOnApplyTransChainData());
    }

    public void test_apply_trans_chain_when_chain_is_empty()
    {
        viewStub.setAddTransData(interactionData(entry(AVAIL_SELECTED_TRANS, LOWER_TRANS)));
        presenter.addTrans();
        viewStub.setRemoveTransData(interactionData(entry(CHAIN_SELECTED_TRANS, LOWER_TRANS)));
        presenter.removeTrans();
        viewStub.setApplyTransChainData(interactionData(entry(SOURCE_STR, "Hello, world.")));

        presenter.applyTransChain();

        assertEquals(interactionData(
                entry(VALIDATING_FAILED_REASON, FailedReason.CHAIN_EMPTY)),
                viewStub.getOnValidatingFailedData());
        assertEquals(interactionData(
                entry(RESULT_STR, ""), entry(AVAIL_SELECTED_INDEX, 0)), 
                viewStub.getOnApplyTransChainData());
    }
}
