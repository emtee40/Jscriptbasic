package com.scriptbasic.executors.commands;

import com.scriptbasic.interfaces.ExtendedInterpreter;

/**
 * @author Peter Verhas
 * date Jul 12, 2012
 */
public abstract class AbstractCommandIfElseKind extends AbstractCommand {

    private final static String CONDITIONJUMP_KEY = "CJK";
    private final static String CONDITIONDONE_KEY = "CDK";
    private AbstractCommandIfElseKind previous;
    private AbstractCommandIfElseKind next;

    /**
     * Indicate that the execution just executed a jump after the IF statement
     * or after the ELSEIF statement. It means that the condition was false and
     * then the next IF has to be evaluated and the following code has to be
     * executed if the condition of the ELSEIF is true or the code has to be
     * executed after the ELSE.
     *
     * @param interpreter
     */
    static void indicateConditionalJump(
            ExtendedInterpreter interpreter) {
        interpreter.getMap().put(CONDITIONJUMP_KEY, Boolean.TRUE);
    }

    static void jumpDone(ExtendedInterpreter interpreter) {
        interpreter.getMap().put(CONDITIONJUMP_KEY, Boolean.FALSE);
    }

    static Boolean itWasConditionalJump(
            ExtendedInterpreter interpreter) {
        return interpreter.getMap().get(CONDITIONJUMP_KEY) == Boolean.TRUE;
    }

    /**
     * Indicate that one of the conditions in the IF/ELSEIF/.../ELSE/ENDIF was
     * already true and the ELSEIF should not be executed but jump even if the
     * condition is true and the control got there jumping.
     *
     * @param interpreter
     */
    static void indicateConditionDone(ExtendedInterpreter interpreter) {
        interpreter.getMap().put(CONDITIONDONE_KEY, Boolean.TRUE);
    }

    protected static void doneUndone(ExtendedInterpreter interpreter) {
        interpreter.getMap().put(CONDITIONDONE_KEY, Boolean.FALSE);
    }

    static Boolean conditionWasNotDoneYet(ExtendedInterpreter interpreter) {
        return !(interpreter.getMap().get(CONDITIONDONE_KEY) == Boolean.TRUE);
    }

    /**
     * @return the previous
     */
    public AbstractCommandIfElseKind getPrevious() {
        return previous;
    }

    /**
     * @param previous the previous to set
     */
    public void setPrevious(AbstractCommandIfElseKind previous) {
        this.previous = previous;
    }

    /**
     * @return the next
     */
    AbstractCommandIfElseKind getNext() {
        return next;
    }

    /**
     * @param next the next to set
     */
    public void setNext(AbstractCommandIfElseKind next) {
        this.next = next;
    }

}
