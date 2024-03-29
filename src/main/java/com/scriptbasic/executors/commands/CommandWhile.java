package com.scriptbasic.executors.commands;

import com.scriptbasic.executors.rightvalues.AbstractPrimitiveRightValue;
import com.scriptbasic.executors.rightvalues.BasicBooleanValue;
import com.scriptbasic.interfaces.*;

public class CommandWhile extends AbstractCommand {
    private Command wendNode;
    private Expression condition;

    public Command getWendNode() {
        return wendNode;
    }

    public void setWendNode(Command wendNode) {
        this.wendNode = wendNode;
    }

    public Expression getCondition() {
        return condition;
    }

    public void setCondition(Expression condition) {
        this.condition = condition;
    }

    private void jumpAfterTheWendCommand(final ExtendedInterpreter interpreter) {
        interpreter.setNextCommand(getWendNode().getNextCommand());
    }

    @Override
    public void execute(final ExtendedInterpreter interpreter)
            throws ExecutionException {
        final RightValue conditionValue = getCondition().evaluate(interpreter);
        if (conditionValue instanceof AbstractPrimitiveRightValue<?>) {
            if (!BasicBooleanValue.asBoolean(conditionValue)) {
                jumpAfterTheWendCommand(interpreter);
            }
        } else {
            throw new BasicRuntimeException(
                    "While condition can not be evaluated to boolean");
        }
    }

}
