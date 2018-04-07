package fayelab.tdd.stringtransformer.instruction.addall;

import java.util.List;
import java.util.function.Predicate;

import fayelab.tdd.stringtransformer.instruction.addall.ValidatingResult.FailedReason;

public class Validator
{
    public static ValidatingResult validate(List<ParamValidatingRule<?>> rules)
    {
        for(ParamValidatingRule<?> rule : rules)
        {
            ValidatingResult validatingResult = validateParam(rule);
            if(!validatingResult.isSucceeded())
            {
                return validatingResult;
            }
        }

        return ValidatingResult.succeededResult();
    }

    private static <T> ValidatingResult validateParam(ParamValidatingRule<T> rule)
    {
        if(rule.getFailedPred().test(rule.getParam()))
        {
            return ValidatingResult.failedResult(rule.getFailedReason());
        }

        return ValidatingResult.succeededResult();
    }
}

class ValidatingResult
{
    enum FailedReason
    {
        NONE,
        AVAIL_TRANS_NOT_SPECIFIED,
        ADD_ALREADY_EXISTED_IN_CHAIN_TRANS,
        CHAIN_TRANS_NOT_SPECIFIED,
        CHAIN_EMPTY,
        SOURCE_STR_EMPTY,
        SOURCE_STR_ILLEGAL
    }

    private boolean succeeded;
    private FailedReason failedReason;

    private ValidatingResult(boolean succeeded, FailedReason failedReason)
    {
        this.succeeded = succeeded;
        this.failedReason = failedReason;
    }

    public boolean isSucceeded()
    {
        return succeeded;
    }

    public FailedReason getFailedReason()
    {
        return failedReason;
    }

    public static ValidatingResult succeededResult()
    {
        return new ValidatingResult(true, FailedReason.NONE);
    }

    public static ValidatingResult failedResult(FailedReason failedReason)
    {
        return new ValidatingResult(false, failedReason);
    }
}

class ParamValidatingRule<T>
{
    private T param;
    private Predicate<T> failedPred;
    private FailedReason failedReason;

    static <T> ParamValidatingRule<T> paramValidatingRule(T param, Predicate<T> failedPred, FailedReason failedReason)
    {
        return new ParamValidatingRule<>(param, failedPred, failedReason);
    }

    private ParamValidatingRule(T param, Predicate<T> failedPred, FailedReason failedReason)
    {
        this.param = param;
        this.failedPred = failedPred;
        this.failedReason = failedReason;
    }

    public T getParam()
    {
        return param;
    }

    public Predicate<T> getFailedPred()
    {
        return failedPred;
    }

    public FailedReason getFailedReason()
    {
        return failedReason;
    }
}
