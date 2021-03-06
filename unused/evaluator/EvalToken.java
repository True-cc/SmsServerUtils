package uwu.smsgamer.smsserverutils.evaluator;

public abstract class EvalToken {
    public int nestingLevel;

    protected EvalToken(int nestingLevel) {
        this.nestingLevel = nestingLevel;
    }

    public abstract EvalVar<?> toVar(Evaluator ev);
}
