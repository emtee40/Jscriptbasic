package com.scriptbasic.executors.rightvalues;
import com.scriptbasic.interfaces.BasicRuntimeException;
import com.scriptbasic.interfaces.RightValue;
public class BasicDoubleValue extends AbstractNumericRightValue<Double> {
    public BasicDoubleValue(final Double d) {
        setValue(d);
    }
    public static Double convert(final RightValue rv)
            throws BasicRuntimeException {
        if (rv == null) {
            return null;
        }
        if (rv.isBoolean()) {
            return ((BasicBooleanValue) rv).getValue() ? 1.0 : 0.0;
        }
        if (rv.isString()) {
            final String s = ((BasicStringValue) rv).getValue();
            if (s == null) {
                return null;
            }
            return Double.parseDouble(s);
        }
        if (rv.isLong()) {
            final Long l = ((BasicLongValue) rv).getValue();
            if (l == null) {
                return null;
            }
            return l.doubleValue();
        }
        if (rv.isDouble()) {
            return ((BasicDoubleValue) rv).getValue();
        }
        if (rv.isJavaObject()) {
            final Object o = ((BasicJavaObjectValue) rv).getValue();
            if (o instanceof Double) {
                return (Double) o;
            }
            // TODO elaborate the conversion with other object classes, like
            // Long, String...
        }
        throw new BasicRuntimeException("Can not convert value "+rv+" to double");
    }
}