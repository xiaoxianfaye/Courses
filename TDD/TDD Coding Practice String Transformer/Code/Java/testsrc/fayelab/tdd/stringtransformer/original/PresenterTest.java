package fayelab.tdd.stringtransformer.original;

import junit.framework.TestCase;

import static java.util.Arrays.asList;

import fayelab.tdd.stringtransformer.original.Presenter;

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
        assertEquals(asList("Upper", "Lower", "TrimPrefixSpaces"), viewStub.getAvailableTransIds());
    }
    
    public void test_add_a_transformer()
    {
        viewStub.setSelectedAvailableTransId("Upper");
        
        presenter.addTransformer();
        
        assertEquals(asList("Upper"), viewStub.getChainTransIds());
    }
    
    public void test_remove_a_transformer()
    {
        viewStub.setSelectedAvailableTransId("Upper");
        presenter.addTransformer();
        
        viewStub.setSelectedChainTransId("Upper");
        
        presenter.removeTransformer();
        
        assertEquals(asList(), viewStub.getChainTransIds());
    }
    
    public void test_apply_transformer_chain()
    {
        viewStub.setSelectedAvailableTransId("Upper");
        presenter.addTransformer();
        viewStub.setSourceStr("Hello, world!");
        
        presenter.applyTransformerChain();
        
        assertEquals("HELLO, WORLD!", viewStub.getResultStr());
    }
    
    public void test_remove_all_transformers()
    {
        viewStub.setSelectedAvailableTransId("Upper");
        presenter.addTransformer();
        viewStub.setSelectedAvailableTransId("Lower");
        presenter.addTransformer();
        
        presenter.removeAllTransformers();
        
        assertEquals(asList(), viewStub.getChainTransIds());
    }
    
    public void test_add_two_same_transformers()
    {
        viewStub.setSelectedAvailableTransId("Upper");
        presenter.addTransformer();
        viewStub.setSelectedAvailableTransId("Upper");
        
        presenter.addTransformer();
        
        assertEquals(asList("Upper"), viewStub.getChainTransIds());
    }
    
    public void test_apply_transformer_chain_when_source_string_is_empty()
    {
        viewStub.setSelectedAvailableTransId("Upper");
        presenter.addTransformer();
        viewStub.setSourceStr("");
        
        presenter.applyTransformerChain();
        
        assertTrue(viewStub.isEmptySourceStrInputNotified());
        assertNull(viewStub.getResultStr());
    }
    
    public void test_apply_transformer_chain_when_chain_is_empty()
    {
        viewStub.setSourceStr("Hello, world!");
        
        presenter.applyTransformerChain();
        
        assertTrue(viewStub.isEmptyChainInputNotified());
        assertNull(viewStub.getResultStr());
    }
}
