package com.scriptbasic.executors.operators;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import com.scriptbasic.executors.AbstractIdentifieredExpression;
import com.scriptbasic.executors.rightvalues.AbstractPrimitiveRightValue;
import com.scriptbasic.executors.rightvalues.ArrayElementAccess;
import com.scriptbasic.executors.rightvalues.FunctionCall;
import com.scriptbasic.executors.rightvalues.VariableAccess;
import com.scriptbasic.interfaces.BasicRuntimeException;
import com.scriptbasic.interfaces.ExecutionException;
import com.scriptbasic.interfaces.Expression;
import com.scriptbasic.interfaces.ExpressionList;
import com.scriptbasic.interfaces.ExtendedInterpreter;
import com.scriptbasic.interfaces.RightValue;
import com.scriptbasic.utility.ExpressionUtility;
import com.scriptbasic.utility.KlassUtility;
import com.scriptbasic.utility.ReflectionUtility;
import com.scriptbasic.utility.RightValueUtility;

/**
 * This is the highest priority operator (priority 1) that is used to access a
 * field of an object. This operator is the (.) dot operator.
 * 
 * @author Peter Verhas
 * 
 */
public class JavaObjectFieldAccessOperator extends AbstractBinaryOperator {

	private Object fetchFieldObject(ExtendedInterpreter extendedInterpreter)
			throws ExecutionException {
		Object object = getLeftOperandObject(extendedInterpreter);
		AbstractIdentifieredExpression rightOp = (AbstractIdentifieredExpression) getRightOperand();
		String fieldName = rightOp.getVariableName();
		Object fieldObject = KlassUtility.getField(object, fieldName);
		return fieldObject;
	}

	private RightValue fetchField(ExtendedInterpreter extendedInterpreter)
			throws ExecutionException {
		Object fieldObject = fetchFieldObject(extendedInterpreter);
		RightValue rightValue = RightValueUtility.createRightValue(fieldObject);
		return rightValue;
	}

	private static Class<?>[] getClassArray(List<RightValue> args) {
		ArrayList<Class<?>> result = null;
		if (args != null) {
			result = new ArrayList<Class<?>>();
			for (RightValue arg : args) {
				result.add(RightValueUtility.getValueObject(arg).getClass());
			}
		}
		return result == null ? null : result.toArray(new Class<?>[0]);
	}

	private RightValue callMethod(final ExtendedInterpreter interpreter,
			final Object object, final Class<?> klass)
			throws ExecutionException {
		RightValue result = null;
		FunctionCall rightOp = (FunctionCall) getRightOperand();
		String methodName = rightOp.getVariableName();
		ExpressionList expressionList = rightOp.getExpressionList();
		List<RightValue> args = ExpressionUtility.evaluateExpressionList(
				interpreter, expressionList);
		Method method = null;
		final Class<?> calculatedKlass = klass == null ? object.getClass()
				: klass;
		method = interpreter.getJavaMethod(calculatedKlass, methodName);
		if (method == null) {
			try {
				method = calculatedKlass.getMethod(methodName,
						getClassArray(args));
			} catch (Exception e) {
				throw new BasicRuntimeException("Method '" + methodName
						+ "' from class '" + klass + "' can not be accessed", e);
			}
		}
		Object methodResultObject = null;
		methodResultObject = ReflectionUtility.invoke(methodName, interpreter,
				method, object, args);

		result = RightValueUtility.createRightValue(methodResultObject);
		return result;
	}

	@SuppressWarnings("unchecked")
	private Object getLeftOperandObject(ExtendedInterpreter extendedInterpreter)
			throws ExecutionException {
		RightValue leftOp = getLeftOperand().evaluate(extendedInterpreter);
		if (!(leftOp instanceof AbstractPrimitiveRightValue<?>)) {
			throw new BasicRuntimeException("Can not get field access from "
					+ (leftOp == null ? "null" : leftOp.getClass())
					+ " from variable "
					+ ((VariableAccess) getLeftOperand()).getVariableName());
		}
		return ((AbstractPrimitiveRightValue<Object>) leftOp).getValue();
	}

	private Class<?> getStaticClass(ExtendedInterpreter interpreter) {
		Class<?> result = null;
		if (getLeftOperand() instanceof VariableAccess) {
			String classAsName = ((VariableAccess) getLeftOperand())
					.getVariableName();
			if (interpreter.getUseMap().containsKey(classAsName)) {
				result = interpreter.getUseMap().get(classAsName);
			}
		}
		return result;
	}

	private static Object getArrayElement(Object[] array, Integer index)
			throws ExecutionException {
		if (index < 0) {
			throw new BasicRuntimeException("Can not use " + index
					+ " < 0 as array index");
		}
		if (index >= array.length) {
			throw new BasicRuntimeException("Cann not use index" + index
					+ " > max index" + (array.length - 1) + " ");
		}
		return array[index];
	}

	@Override
	public RightValue evaluate(ExtendedInterpreter interpreter)
			throws ExecutionException {
		RightValue result = null;
		Expression rightOp = getRightOperand();

		if (rightOp instanceof VariableAccess) {

			result = fetchField(interpreter);

		} else if (rightOp instanceof FunctionCall) {
			Class<?> klass = getStaticClass(interpreter);
			Object object = null;
			if (klass == null) {
				object = getLeftOperandObject(interpreter);
			}
			result = callMethod(interpreter, object, klass);

		} else if (rightOp instanceof ArrayElementAccess) {
			Object variable = fetchFieldObject(interpreter);
			for (Expression expression : ((ArrayElementAccess) rightOp)
					.getExpressionList()) {
				if (variable instanceof Object[]) {
					Integer index = RightValueUtility
							.convert2Integer(expression.evaluate(interpreter));
					variable = getArrayElement((Object[]) variable, index);
				} else {
					throw new BasicRuntimeException(
							"Java object field is not array, can not access it that way.");
				}
			}
			result = RightValueUtility.createRightValue(variable);
		} else {
			throw new BasicRuntimeException(
					"Field access operator is not implemented to handle variable field.");
		}
		return result;
	}
}