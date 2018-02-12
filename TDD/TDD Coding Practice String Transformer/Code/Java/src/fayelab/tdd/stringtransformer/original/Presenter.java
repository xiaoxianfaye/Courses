package fayelab.tdd.stringtransformer.original;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import fayelab.tdd.stringtransformer.original.Param.Name;

import static fayelab.tdd.stringtransformer.original.Param.Name.*;
import static fayelab.tdd.stringtransformer.original.Transformer.*;
import static fayelab.tdd.stringtransformer.original.DataBuilder.*;

public class Presenter
{
    static final int SELECTED_INDEX_NONE = -1;
    
    enum ValidatingFailedReason {
        ADD_DUPLICATE_TRANSFORMER
    };
    
    private View view;
    private BusinessLogic businessLogic;
    
    private List<Transformer> availableTransformers = null;
    private List<Transformer> chainTransformers = new ArrayList<>();
    
    public Presenter(View view, BusinessLogic businessLogic)
    {
        this.view = view;
        this.businessLogic = businessLogic;
        
        this.view.setPresenter(this);
    }

    public void init()
    {
        availableTransformers = businessLogic.getAllTransformers();
        view.onInitData(build(
                new Param<>(AVAILABLE_TRANSNAMES, Transformer.getNames(availableTransformers)),
                new Param<>(AVAILABLE_SELECTED_INDEX, 0)));
    }

    public void addTransformer()
    {
        Map<Name, Object> data = view.collectAddData();
        Transformer availableSelectedTransformer = getTransformer(toStr(data.get(AVAILABLE_SELECTED_TRANSNAME)));

        boolean isValidatingFailed = false;
        if(chainTransformers.contains(availableSelectedTransformer))
        {
            isValidatingFailed = true;
            view.onValidatingFailed(build(
                    new Param<>(VALIDATING_FAILED_REASON, ValidatingFailedReason.ADD_DUPLICATE_TRANSFORMER)));
        }
        else
        {
            chainTransformers.add(availableSelectedTransformer);
        }
        
        List<String> chainTransNames = Transformer.getNames(chainTransformers);
        int chainSelectedIndex = chainTransformers.indexOf(availableSelectedTransformer);
        int availableSelectedIndex = calcAvailableSelectedIndex(availableSelectedTransformer, isValidatingFailed);
        view.onAddTransformer(build(
                new Param<>(CHAIN_TRANSNAMES, chainTransNames),
                new Param<>(CHAIN_SELECTED_INDEX, chainSelectedIndex), 
                new Param<>(AVAILABLE_SELECTED_INDEX, availableSelectedIndex)));
    }

    private int calcAvailableSelectedIndex(Transformer availableSelectedTransformer, boolean isValidatingFailed)
    {
        int selectedIndex = availableTransformers.indexOf(availableSelectedTransformer);
        return isValidatingFailed ? selectedIndex 
                : (selectedIndex == availableTransformers.size() - 1 ? 0 : selectedIndex + 1);
    }

    public void removeTransformer()
    {
        Map<Name, Object> data = view.collectRemoveData();
        Transformer selectedChainTransformer = Transformer.getTransformer(toStr(data.get(CHAIN_SELECTED_TRANSNAME)));
        int chainSelectedIndex = calcChainSelectedIndex(selectedChainTransformer);
        chainTransformers.remove(selectedChainTransformer);
        List<String> chainTransNames = Transformer.getNames(chainTransformers);
        int availableSelectedIndex = availableTransformers.indexOf(selectedChainTransformer);
        view.onRemoveTransformer(build(
                new Param<>(CHAIN_TRANSNAMES, chainTransNames),
                new Param<>(CHAIN_SELECTED_INDEX, chainSelectedIndex), 
                new Param<>(AVAILABLE_SELECTED_INDEX, availableSelectedIndex)));
    }

    private int calcChainSelectedIndex(Transformer selectedChainTransformer)
    {
        int selectedIndex = chainTransformers.indexOf(selectedChainTransformer);
        return chainTransformers.size() == 1 ? SELECTED_INDEX_NONE 
                : (selectedIndex == chainTransformers.size() - 1 ? 0 : selectedIndex);
    }

    public void removeAllTransformers()
    {
        chainTransformers.clear();
        List<String> chainTransNames = Transformer.getNames(chainTransformers);
        view.onRemoveAllTransformers(build(
                new Param<>(CHAIN_TRANSNAMES, chainTransNames),
                new Param<>(CHAIN_SELECTED_INDEX, SELECTED_INDEX_NONE), 
                new Param<>(AVAILABLE_SELECTED_INDEX, 0)));
    }

    public void applyTransformerChain()
    {
        Map<Name, Object> data = view.collectApplyData();
        String sourceStr = toStr(data.get(SOURCESTR));
        String resultStr = businessLogic.transform(sourceStr, chainTransformers);
        view.onApplyTransformerChain(build(new Param<>(RESULTSTR, resultStr)));
    }
}