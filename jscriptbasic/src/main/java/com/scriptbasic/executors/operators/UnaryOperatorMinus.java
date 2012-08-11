package com.scriptbasic.executors.operators;

import com.scriptbasic.executors.rightvalues.BasicDoubleValue;
import com.scriptbasic.executors.rightvalues.BasicLongValue;
import com.scriptbasic.interfaces.BasicRuntimeException;
import com.scriptbasic.interfaces.ExecutionException;
import com.scriptbasic.interfaces.Expression;
import com.scriptbasic.interfaces.ExtendedInterpreter;
import com.scriptbasic.interfaces.RightValue;

public class UnaryOperatorMinus extends AbstractUnaryOperator {
    @Override
    public RightValue evaluate(ExtendedInterpreter interpreter)
            throws ExecutionException {
        final Expression operand = getOperand();
        final RightValue rightValue = operand.evaluate(interpreter);
        RightValue result = null;
        if (!rightValue.isNumeric()) {
            throw new BasicRuntimeException(
                    "Unary minus operator applied to non numeric value",
                    interpreter.getCurrentCommand());
        }
        if (rightValue.isDouble()) {
            result = new BasicDoubleValue(-1
                    * ((BasicDoubleValue) rightValue).getValue());
        } else if (rightValue.isLong()) {
            result = new BasicLongValue(-1
                    * ((BasicLongValue) rightValue).getValue());
        }
        return result;
    }
}
