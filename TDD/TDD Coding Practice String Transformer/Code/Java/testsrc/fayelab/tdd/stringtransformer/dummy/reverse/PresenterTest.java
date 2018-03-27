package fayelab.tdd.stringtransformer.dummy.reverse;

import junit.framework.TestCase;

import static java.util.Arrays.asList;
import static fayelab.tdd.stringtransformer.dummy.reverse.Trans.*;

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
        assertEquals(asList(UPPER_TRANS, LOWER_TRANS, TRIM_PREFIX_SPACES_TRANS), viewStub.getAvailTranses());
        assertEquals(0, viewStub.getAvailSelectedIndex());
    }

    public void test_add_not_the_last_trans()
    {
        viewStub.setAvailSelectedTrans(UPPER_TRANS);

        presenter.addTrans();

        assertEquals(asList(UPPER_TRANS), viewStub.getChainTranses());
        assertEquals(0, viewStub.getChainSelectedIndex());
        assertEquals(1, viewStub.getAvailSelectedIndex());
    }

    public void test_add_the_last_trans()
    {
        viewStub.setAvailSelectedTrans(TRIM_PREFIX_SPACES_TRANS);

        presenter.addTrans();

        assertEquals(asList(TRIM_PREFIX_SPACES_TRANS), viewStub.getChainTranses());
        assertEquals(0, viewStub.getChainSelectedIndex());
        assertEquals(0, viewStub.getAvailSelectedIndex());
    }

    public void test_add_already_existed_in_chain_trans()
    {
        viewStub.setAvailSelectedTrans(UPPER_TRANS);
        presenter.addTrans();
        viewStub.setAvailSelectedTrans(LOWER_TRANS);
        presenter.addTrans();
        viewStub.setAvailSelectedTrans(LOWER_TRANS);

        presenter.addTrans();

        assertTrue(viewStub.isAddAlreadyExistedInChainTransNotified());
        assertEquals(asList(UPPER_TRANS, LOWER_TRANS), viewStub.getChainTranses());
        assertEquals(1, viewStub.getChainSelectedIndex());
        assertEquals(1, viewStub.getAvailSelectedIndex());
    }

    public void test_add_trans_but_avail_trans_not_specified()
    {
        viewStub.setAvailSelectedTrans(UPPER_TRANS);
        presenter.addTrans();
        viewStub.setAvailSelectedTrans(LOWER_TRANS);
        presenter.addTrans();
        viewStub.setAvailSelectedTrans(NONE_SELECTED_TRANS);

        presenter.addTrans();

        assertTrue(viewStub.isAvailTransNotSpecifiedNotified());
        assertEquals(asList(UPPER_TRANS, LOWER_TRANS), viewStub.getChainTranses());
        assertEquals(1, viewStub.getChainSelectedIndex());
        assertEquals(0, viewStub.getAvailSelectedIndex());
    }

    public void test_remove_not_the_last_trans_when_chain_has_more_than_one_transes()
    {
        viewStub.setAvailSelectedTrans(UPPER_TRANS);
        presenter.addTrans();
        viewStub.setAvailSelectedTrans(LOWER_TRANS);
        presenter.addTrans();
        viewStub.setAvailSelectedTrans(TRIM_PREFIX_SPACES_TRANS);
        presenter.addTrans();
        viewStub.setChainSelectedTrans(LOWER_TRANS);

        presenter.removeTrans();

        assertEquals(asList(UPPER_TRANS, TRIM_PREFIX_SPACES_TRANS), viewStub.getChainTranses());
        assertEquals(1, viewStub.getAvailSelectedIndex());
        assertEquals(1, viewStub.getChainSelectedIndex());
    }

    public void test_remove_the_last_trans_when_chain_has_more_than_one_transes()
    {
        viewStub.setAvailSelectedTrans(UPPER_TRANS);
        presenter.addTrans();
        viewStub.setAvailSelectedTrans(LOWER_TRANS);
        presenter.addTrans();
        viewStub.setAvailSelectedTrans(TRIM_PREFIX_SPACES_TRANS);
        presenter.addTrans();
        viewStub.setChainSelectedTrans(TRIM_PREFIX_SPACES_TRANS);

        presenter.removeTrans();

        assertEquals(asList(UPPER_TRANS, LOWER_TRANS), viewStub.getChainTranses());
        assertEquals(2, viewStub.getAvailSelectedIndex());
        assertEquals(0, viewStub.getChainSelectedIndex());
    }

    public void test_remove_a_trans_when_chain_has_only_one_transes()
    {
        viewStub.setAvailSelectedTrans(UPPER_TRANS);
        presenter.addTrans();
        viewStub.setChainSelectedTrans(UPPER_TRANS);

        presenter.removeTrans();

        assertEquals(asList(), viewStub.getChainTranses());
        assertEquals(0, viewStub.getAvailSelectedIndex());
        assertEquals(NONE_SELECTED_INDEX, viewStub.getChainSelectedIndex());
    }

    public void test_remove_trans_when_chain_is_not_empty_but_chain_trans_not_specified()
    {
        viewStub.setAvailSelectedTrans(UPPER_TRANS);
        presenter.addTrans();
        viewStub.setChainSelectedTrans(NONE_SELECTED_TRANS);

        presenter.removeTrans();

        assertTrue(viewStub.isChainTransNotSpecifiedNotified());
        assertEquals(asList(UPPER_TRANS), viewStub.getChainTranses());
        assertEquals(1, viewStub.getAvailSelectedIndex());
        assertEquals(0, viewStub.getChainSelectedIndex());
    }

    public void test_remove_trans_when_chain_is_empty()
    {
        presenter.removeTrans();

        assertTrue(viewStub.isChainEmptyNotified());
        assertEquals(asList(), viewStub.getChainTranses());
        assertEquals(0, viewStub.getAvailSelectedIndex());
        assertEquals(NONE_SELECTED_INDEX, viewStub.getChainSelectedIndex());
    }

    public void test_remove_all_transes_when_chain_is_not_empty()
    {
        viewStub.setAvailSelectedTrans(UPPER_TRANS);
        presenter.addTrans();
        viewStub.setAvailSelectedTrans(LOWER_TRANS);
        presenter.addTrans();

        presenter.removeAllTranses();

        assertEquals(asList(), viewStub.getChainTranses());
        assertEquals(0, viewStub.getAvailSelectedIndex());
        assertEquals(NONE_SELECTED_INDEX, viewStub.getChainSelectedIndex());
    }

    public void test_remove_all_transes_when_chain_is_empty()
    {
        viewStub.setAvailSelectedTrans(LOWER_TRANS);
        presenter.addTrans();
        viewStub.setChainSelectedTrans(LOWER_TRANS);
        presenter.removeTrans();

        presenter.removeAllTranses();

        assertTrue(viewStub.isChainEmptyNotified());
        assertEquals(asList(), viewStub.getChainTranses());
        assertEquals(1, viewStub.getAvailSelectedIndex());
        assertEquals(NONE_SELECTED_INDEX, viewStub.getChainSelectedIndex());
    }

    public void test_apply_trans_chain()
    {
        viewStub.setAvailSelectedTrans(UPPER_TRANS);
        presenter.addTrans();
        viewStub.setSourceStr("Hello, world.");

        presenter.applyTransChain();

        assertEquals("HELLO, WORLD.", viewStub.getResultStr());
    }

    public void test_apply_trans_chain_when_source_str_is_empty()
    {
        viewStub.setAvailSelectedTrans(UPPER_TRANS);
        presenter.addTrans();
        viewStub.setSourceStr("");

        presenter.applyTransChain();

        assertTrue(viewStub.isSourceStrEmptyNotified());
        assertEquals("", viewStub.getResultStr());
    }

    public void test_apply_trans_chain_when_source_str_is_illegal()
    {
        viewStub.setAvailSelectedTrans(UPPER_TRANS);
        presenter.addTrans();
        viewStub.setSourceStr("aÖÐÎÄb");

        presenter.applyTransChain();

        assertTrue(viewStub.isSourceStrIllegalNotified());
        assertEquals("", viewStub.getResultStr());
    }

    public void test_apply_trans_chain_when_chain_is_empty()
    {
        viewStub.setAvailSelectedTrans(LOWER_TRANS);
        presenter.addTrans();
        viewStub.setChainSelectedTrans(LOWER_TRANS);
        presenter.removeTrans();
        viewStub.setSourceStr("Hello, world.");

        presenter.applyTransChain();

        assertTrue(viewStub.isChainEmptyNotified());
        assertEquals("", viewStub.getResultStr());
        assertEquals(0, viewStub.getAvailSelectedIndex());
    }
}
