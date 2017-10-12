package fayelab.tdd.stringtransformer.original;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

public class Presenter
{
    private View view;
    private BusinessLogic businessLogic;
    
    private List<String> chainTransIds = new ArrayList<>();

    public Presenter(View view, BusinessLogic businessLogic)
    {
        this.view = view;
        this.businessLogic = businessLogic;
        
        this.view.setPresenter(this);
    }

    public void init()
    {
        view.presentAvailableTransIds(businessLogic.getAllTransIds());
    }

    public void addTransformer()
    {
        String selectedAvailableTransId = view.getSelectedAvailableTransId();
        if(notExistedInChain(selectedAvailableTransId))
        {
            chainTransIds.add(selectedAvailableTransId);
        }
        
        view.presentChainTransIds(chainTransIds);
    }

    private boolean notExistedInChain(String selectedAvailableTransId)
    {
        return !chainTransIds.contains(selectedAvailableTransId);
    }

    public void removeTransformer()
    {
        chainTransIds.remove(view.getSelectedChainTransId());
        view.presentChainTransIds(chainTransIds);
    }

    public void applyTransformerChain()
    {
        String sourceStr = view.getSourceStr();
        if(!checkInput(sourceStr, chainTransIds))
        {
            return;
        }
        
        view.presentResultStr(businessLogic.transform(sourceStr, chainTransIds));
    }

    public void removeAllTransformers()
    {
        chainTransIds.clear();
        view.presentChainTransIds(chainTransIds);
    }

    private boolean checkInput(String sourceStr, List<String> chain)
    {
        return checkSourceStr(sourceStr) && checkChain(chain);
    }

    private boolean checkSourceStr(String str)
    {
        return checkParam(str, s -> s.isEmpty(), () -> view.onEmptySourceStrInput());
    }

    private boolean checkChain(List<String> chain)
    {
        return checkParam(chain, c -> c.isEmpty(), () -> view.onEmptyChainInput());
    }
    
    private <T> boolean checkParam(T param, Predicate<T> pred, Runnable notify)
    {
        if(pred.test(param))
        {
            notify.run();
            return false;
        }
        
        return true;
    }
}
