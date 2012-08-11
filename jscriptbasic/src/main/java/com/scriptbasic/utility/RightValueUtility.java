/**
 *
 */
package com.scriptbasic.utility;
import com.scriptbasic.errors.BasicInterpreterInternalError;
import com.scriptbasic.executors.rightvalues.AbstractNumericRightValue;
import com.scriptbasic.executors.rightvalues.AbstractPrimitiveRightValue;
import com.scriptbasic.executors.rightvalues.BasicBooleanValue;
import com.scriptbasic.executors.rightvalues.BasicDoubleValue;
import com.scriptbasic.executors.rightvalues.BasicJavaObjectValue;
import com.scriptbasic.executors.rightvalues.BasicLongValue;
import com.scriptbasic.executors.rightvalues.BasicStringValue;
import com.scriptbasic.interfaces.BasicRuntimeException;
import com.scriptbasic.interfaces.ExecutionException;
import com.scriptbasic.interfaces.RightValue;
/**
 * @author Peter Verhas
 * date June 26, 2012
 *
 */
public final class RightValueUtility {
    private RightValueUtility() {
        UtilityUtility.throwExceptionToEnsureNobodyCallsIt();
    }
    @SuppressWarnings("unchecked")
    public static Object getValueObject(RightValue arg) {
        Object object;
        if (arg instanceof AbstractPrimitiveRightValue<?>) {
            object = ((AbstractPrimitiveRightValue<Object>) arg).getValue();
        } else {
            throw new BasicInterpreterInternalError(
                    "The class of the object "
                            + arg
                            + " is not convertible type to fetchs it's value as object.");
        }
        return object;
    }
    /**
     * @param index
     * @return
     */
    @SuppressWarnings("unchecked")
    public static Integer convert2Integer(RightValue index)
            throws ExecutionException {
        Integer result = 0;
        if (index.isNumeric()) {
            result = ((AbstractNumericRightValue<Number>) index).getValue()
                    .intValue();
        } else {
            throw new BasicRuntimeException(
                    index.toString()
                            + " is not a numeric value, can not be used to index and array");
        }
        return result;
    }
    /**
     * Create a right value from the object. If the object is already a right
     * value then just return the object. If the object is some primitive object
     * (Long, Integer, Short, Float, Double, String, Character, Boolean then it
     * creates a Basic object that holds a Long, Double, String or Boolean
     * value.
     * <p>
     * In other cases the method will return a {@see BasicJavaObjectValue}.
     *
     * @param value
     *            the original value to convert to a right value
     * @return the converted right value
     */
    public static RightValue createRightValue(Object value) {
        RightValue rightValue = null;
        if (value instanceof RightValue) {
            rightValue = (RightValue) value;
        } else if (value instanceof Double) {
            rightValue = new BasicDoubleValue((Double) value);
        } else if (value instanceof Float) {
            rightValue = new BasicDoubleValue(((Float) value).doubleValue());
        } else if (value instanceof Long) {
            rightValue = new BasicLongValue((Long) value);
        } else if (value instanceof Integer) {
            rightValue = new BasicLongValue(((Integer) value).longValue());
        } else if (value instanceof Short) {
            rightValue = new BasicLongValue(((Short) value).longValue());
        } else if (value instanceof String) {
            rightValue = new BasicStringValue((String) value);
        } else if (value instanceof Character) {
            rightValue = new BasicStringValue(new String(
                    new char[] { (Character) value }));
        } else if (value instanceof Boolean) {
            rightValue = new BasicBooleanValue((Boolean) value);
        } else {
            rightValue = new BasicJavaObjectValue(value);
        }
        return rightValue;
    }
}