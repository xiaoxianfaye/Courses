package fayelab.tdd.stringtransformer.dummy.original;

import junit.framework.TestCase;

import static java.util.Arrays.asList;
import static fayelab.tdd.stringtransformer.dummy.original.Trans.*;

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
}
