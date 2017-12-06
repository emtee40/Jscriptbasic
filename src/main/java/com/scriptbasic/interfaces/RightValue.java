package com.scriptbasic.interfaces;

import com.scriptbasic.executors.rightvalues.*;

public interface RightValue extends Value {
    default Boolean isNumeric() {
        return isLong() || isDouble();
    }

    default Boolean isLong() {
        return this instanceof BasicLongValue;
    }

    default Boolean isDouble() {
        return this instanceof BasicDoubleValue;
    }

    default Boolean isBoolean() {
        return this instanceof BasicBooleanValue;
    }

    default Boolean isString() {
        return this instanceof BasicStringValue;
    }

    default Boolean isArray() {
        return this instanceof BasicArrayValue;
    }

    default Boolean isJavaObject() {
        return this instanceof BasicJavaObjectValue;
    }

}
