package fayelab.tdd.stringtransformer.original;

import junit.framework.TestCase;

import java.util.Map;

import fayelab.tdd.stringtransformer.original.Param.Name;
import fayelab.tdd.stringtransformer.original.Presenter;
import fayelab.tdd.stringtransformer.original.Presenter.ValidatingFailedReason;
import fayelab.tdd.stringtransformer.original.Transformer;

import static java.util.Arrays.asList;
import static fayelab.tdd.stringtransformer.original.Param.Name.*;
import static fayelab.tdd.stringtransformer.original.Transformer.*;
import static fayelab.tdd.stringtransformer.original.DataBuilder.build;
import static fayelab.tdd.stringtransformer.original.Presenter.SELECTED_INDEX_NONE;

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
        Map<Name, Object> expected = build(
                new Param<>(AVAILABLE_TRANSNAMES, getNames(asList(Transformer.values()))),
                new Param<>(AVAILABLE_SELECTED_INDEX, 0));
        assertEquals(expected, viewStub.getOnInitData());
    }
    
    public void test_add_first_transformer()
    {
        viewStub.setAddData(build(new Param<>(AVAILABLE_SELECTED_TRANSNAME, UPPER.getName())));

        presenter.addTransformer();
        
        Map<Name, Object> expected = build(
                new Param<>(CHAIN_TRANSNAMES, getNames(asList(UPPER))),
                new Param<>(CHAIN_SELECTED_INDEX, 0),
                new Param<>(AVAILABLE_SELECTED_INDEX, 1));
        assertEquals(expected, viewStub.getOnAddData());
    }
    
    public void test_add_last_transformer()
    {
        viewStub.setAddData(build(new Param<>(AVAILABLE_SELECTED_TRANSNAME, TRIM_PREFIX_SPACES.getName())));

        presenter.addTransformer();
        
        Map<Name, Object> expected = build(
                new Param<>(CHAIN_TRANSNAMES, getNames(asList(TRIM_PREFIX_SPACES))),
                new Param<>(CHAIN_SELECTED_INDEX, 0),
                new Param<>(AVAILABLE_SELECTED_INDEX, 0));
        assertEquals(expected, viewStub.getOnAddData());
    }
    
    public void test_remove_first_transformer_when_chain_has_more_than_one_transformers()
    {
        viewStub.setAddData(build(new Param<>(AVAILABLE_SELECTED_TRANSNAME, UPPER.getName())));
        presenter.addTransformer();
        viewStub.setAddData(build(new Param<>(AVAILABLE_SELECTED_TRANSNAME, LOWER.getName())));
        presenter.addTransformer();
        viewStub.setRemoveData(build(new Param<>(CHAIN_SELECTED_TRANSNAME, UPPER.getName())));
        
        presenter.removeTransformer();
        
        Map<Name, Object> expected = build(
                new Param<>(CHAIN_TRANSNAMES, getNames(asList(LOWER))),
                new Param<>(CHAIN_SELECTED_INDEX, 0),
                new Param<>(AVAILABLE_SELECTED_INDEX, 0));
        assertEquals(expected, viewStub.getOnRemoveData());
    }
    
    public void test_remove_last_transformer_when_chain_has_more_than_one_transformers()
    {
        viewStub.setAddData(build(new Param<>(AVAILABLE_SELECTED_TRANSNAME, LOWER.getName())));
        presenter.addTransformer();
        viewStub.setAddData(build(new Param<>(AVAILABLE_SELECTED_TRANSNAME, TRIM_PREFIX_SPACES.getName())));
        presenter.addTransformer();
        viewStub.setRemoveData(build(new Param<>(CHAIN_SELECTED_TRANSNAME, TRIM_PREFIX_SPACES.getName())));
        
        presenter.removeTransformer();
        
        Map<Name, Object> expected = build(
                new Param<>(CHAIN_TRANSNAMES, getNames(asList(LOWER))),
                new Param<>(CHAIN_SELECTED_INDEX, 0),
                new Param<>(AVAILABLE_SELECTED_INDEX, 2));
        assertEquals(expected, viewStub.getOnRemoveData());  
    }
    
    public void test_remove_last_transformer_when_chain_has_only_one_transformer()
    {
        viewStub.setAddData(build(new Param<>(AVAILABLE_SELECTED_TRANSNAME, LOWER.getName())));
        presenter.addTransformer();
        
        viewStub.setRemoveData(build(new Param<>(CHAIN_SELECTED_TRANSNAME, LOWER.getName())));
        
        presenter.removeTransformer();
        
        Map<Name, Object> expected = build(
                new Param<>(CHAIN_TRANSNAMES, getNames(asList())),
                new Param<>(CHAIN_SELECTED_INDEX, SELECTED_INDEX_NONE),
                new Param<>(AVAILABLE_SELECTED_INDEX, 1));
        assertEquals(expected, viewStub.getOnRemoveData());
    }
    
    public void test_remove_all_transformers()
    {
        viewStub.setAddData(build(new Param<>(AVAILABLE_SELECTED_TRANSNAME, UPPER.getName())));
        presenter.addTransformer();
        viewStub.setAddData(build(new Param<>(AVAILABLE_SELECTED_TRANSNAME, LOWER.getName())));
        presenter.addTransformer();
        
        presenter.removeAllTransformers();
        
        Map<Name, Object> expected = build(
                new Param<>(CHAIN_TRANSNAMES, getNames(asList())),
                new Param<>(CHAIN_SELECTED_INDEX, SELECTED_INDEX_NONE),
                new Param<>(AVAILABLE_SELECTED_INDEX, 0));
        assertEquals(expected, viewStub.getOnRemoveAllData());
    }
    
    public void test_apply_transformer_chain()
    {
        viewStub.setAddData(build(new Param<>(AVAILABLE_SELECTED_TRANSNAME, UPPER.getName())));
        presenter.addTransformer();
        viewStub.setApplyData(build(new Param<>(SOURCESTR, "Hello, world.")));
        
        presenter.applyTransformerChain();
        
        Map<Name, Object> expected = build(new Param<>(RESULTSTR, "HELLO, WORLD."));
        assertEquals(expected, viewStub.getOnApplyData());
    }
    
    public void test_add_two_same_transformers()
    {
        viewStub.setAddData(build(new Param<>(AVAILABLE_SELECTED_TRANSNAME, UPPER.getName())));
        presenter.addTransformer();
        viewStub.setAddData(build(new Param<>(AVAILABLE_SELECTED_TRANSNAME, UPPER.getName())));
        
        presenter.addTransformer();
        
        Map<Name, Object> expectedValidatingFailedReason = build(
                new Param<>(VALIDATING_FAILED_REASON, ValidatingFailedReason.ADD_DUPLICATE_TRANSFORMER));
        assertEquals(expectedValidatingFailedReason, viewStub.getOnValidatingFailedData());        
        
        Map<Name, Object> expectedOnAddData = build(
                new Param<>(CHAIN_TRANSNAMES, getNames(asList(UPPER))),
                new Param<>(CHAIN_SELECTED_INDEX, 0),
                new Param<>(AVAILABLE_SELECTED_INDEX, 0));
        assertEquals(expectedOnAddData, viewStub.getOnAddData());
    }
}