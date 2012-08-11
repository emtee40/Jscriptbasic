package com.scriptbasic.executors.rightvalues;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Iterator;
import java.util.List;
import com.scriptbasic.executors.AbstractIdentifieredExpressionListedExpression;
import com.scriptbasic.executors.commands.CommandSub;
import com.scriptbasic.executors.leftvalues.BasicLeftValue;
import com.scriptbasic.interfaces.BasicRuntimeException;
import com.scriptbasic.interfaces.ExecutionException;
import com.scriptbasic.interfaces.Expression;
import com.scriptbasic.interfaces.ExpressionList;
import com.scriptbasic.interfaces.ExtendedInterpreter;
import com.scriptbasic.interfaces.LeftValue;
import com.scriptbasic.interfaces.LeftValueList;
import com.scriptbasic.interfaces.RightValue;
import com.scriptbasic.utility.ExpressionUtility;
import com.scriptbasic.utility.RightValueUtility;
public class FunctionCall extends
        AbstractIdentifieredExpressionListedExpression {
    private RightValue callBasicFunction(ExtendedInterpreter interpreter)
            throws ExecutionException {
        RightValue result = null;
        RightValue[] argumentValues = evaluateArguments(getExpressionList(),
                interpreter);
        interpreter.push();
        LeftValueList arguments = commandSub.getArguments();
        registerLocalVariablesWithValues(arguments, argumentValues, interpreter);
        interpreter.disableHook();
        interpreter.setReturnValue(null);
        interpreter.enableHook();
        if (interpreter.getHook() != null) {
            interpreter.getHook().beforeSubroutineCall(getVariableName(),
                    arguments, argumentValues);
        }
        interpreter.execute(commandSub.getNextCommand());
        result = interpreter.getReturnValue();
        interpreter.pop();
        return result;
    }
    private RightValue callJavaFunction(ExtendedInterpreter interpreter)
            throws ExecutionException {
        RightValue result = null;
        String functionName = getVariableName();
        List<RightValue> args = ExpressionUtility.evaluateExpressionList(
                interpreter, getExpressionList());
        Method method = interpreter.getJavaMethod(null, functionName);
        if (method != null) {
            Object methodResult = null;
            try {
                if (interpreter.getHook() != null) {
                    interpreter.getHook().beforeCallJavaFunction(method);
                }
                methodResult = method.invoke(null, ExpressionUtility
                        .getObjectArray(args, method, interpreter));
                if (interpreter.getHook() != null) {
                    methodResult = interpreter.getHook().afterCallJavaFunction(
                            method, methodResult);
                }
            } catch (IllegalAccessException | IllegalArgumentException
                    | InvocationTargetException e) {
                throw new BasicRuntimeException("Can not invoke method "
                        + functionName, e,interpreter.getCurrentCommand());
            } catch (Exception e) {
                throw new BasicRuntimeException("Invoking function '"
                        + functionName + "' throws exception:", e,interpreter.getCurrentCommand());
            }
            result = RightValueUtility.createRightValue(methodResult);
        }
        return result;
    }
    private boolean commandNeedLookup = true;
    private CommandSub commandSub = null;
    private void lookUpCommandSub(ExtendedInterpreter interpreter) {
        if (commandNeedLookup) {
            commandNeedLookup = false;
            commandSub = interpreter.getSubroutine(getVariableName());
        }
    }
    private static RightValue[] evaluateArguments(ExpressionList argumentList,
            ExtendedInterpreter interpreter) throws ExecutionException {
        RightValue[] argumentValues;
        if (argumentList == null) {
            argumentValues = null;
        } else {
            Iterator<Expression> expressionIterator = argumentList.iterator();
            argumentValues = new RightValue[argumentList.size()];
            for (int i = 0; i < argumentValues.length; i++) {
                argumentValues[i] = expressionIterator.next().evaluate(
                        interpreter);
            }
        }
        return argumentValues;
    }
    /**
     * @param arguments
     * @param argumentValues
     * @throws ExecutionException
     */
    private static void registerLocalVariablesWithValues(
            LeftValueList arguments, RightValue[] argumentValues,
            ExtendedInterpreter interpreter) throws ExecutionException {
        if (arguments != null) {
            Iterator<LeftValue> argumentIterator = arguments.iterator();
            for (int i = 0; i < argumentValues.length; i++) {
                LeftValue argument = argumentIterator.next();
                if (argument instanceof BasicLeftValue) {
                    String name = ((BasicLeftValue) argument).getIdentifier();
                    interpreter.getVariables().registerLocalVariable(name);
                    interpreter.setVariable(name, argumentValues[i]);
                } else {
                    throw new BasicRuntimeException(
                            "subroutine formal argument is erroneous",interpreter.getCurrentCommand());
                }
            }
        }
    }
    @Override
    public RightValue evaluate(ExtendedInterpreter interpreter)
            throws ExecutionException {
        lookUpCommandSub(interpreter);
        RightValue result = null;
        if (commandSub == null) {
            result = callJavaFunction(interpreter);
        } else {
            result = callBasicFunction(interpreter);
        }
        return result;
    }
}