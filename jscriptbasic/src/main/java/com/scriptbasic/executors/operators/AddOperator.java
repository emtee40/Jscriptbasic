package com.scriptbasic.executors.operators;

import com.scriptbasic.executors.rightvalues.BasicBooleanValue;
import com.scriptbasic.executors.rightvalues.BasicDoubleValue;
import com.scriptbasic.executors.rightvalues.BasicJavaObjectValue;
import com.scriptbasic.executors.rightvalues.BasicLongValue;
import com.scriptbasic.executors.rightvalues.BasicStringValue;
import com.scriptbasic.interfaces.BasicRuntimeException;
import com.scriptbasic.interfaces.ExtendedInterpreter;
import com.scriptbasic.interfaces.RightValue;

public class AddOperator extends AbstractBinaryFullCircuitHalfDoubleOperator {
    @Override
    protected RightValue operateOnDoubleDouble(final Double a, final Double b)
            throws BasicRuntimeException {
        return new BasicDoubleValue(a + b);
    }

    @Override
    protected RightValue operateOnLongLong(final Long a, final Long b)
            throws BasicRuntimeException {
        return new BasicLongValue(a + b);
    }

    private static String getString(final RightValue op)
            throws BasicRuntimeException {
        if (op.isString()) {
            return ((BasicStringValue) op).getValue();
        }
        if (op.isDouble()) {
            return ((BasicDoubleValue) op).getValue().toString();
        }
        if (op.isLong()) {
            return ((BasicLongValue) op).getValue().toString();
        }
        if (op.isBoolean()) {
            return ((BasicBooleanValue) op).getValue().toString();
        }
        if (op.isJavaObject()) {
            return ((BasicJavaObjectValue) op).getValue().toString();
        }
        throw new BasicRuntimeException(
                "Argument can not be converted to string");
    }

    @Override
    protected RightValue operateOnValues(ExtendedInterpreter interpreter,
            final RightValue leftOperand, final RightValue rightOperand)
            throws BasicRuntimeException {
        return new BasicStringValue(getString(leftOperand)
                + getString(rightOperand));
    }

    @Override
    protected String operatorName() {
        return "Plus";
    }
}
