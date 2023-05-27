package com.scriptbasic.utility;

import com.scriptbasic.api.BasicFunction;
import com.scriptbasic.errors.BasicInterpreterInternalError;
import com.scriptbasic.interfaces.BasicRuntimeException;
import com.scriptbasic.interfaces.ExtensionInterfaceVersion;
import com.scriptbasic.log.Logger;
import com.scriptbasic.log.LoggerFactory;
import com.scriptbasic.spi.Interpreter;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author Peter Verhas date Jul 22, 2012
 */
public class MethodRegisterUtility implements ExtensionInterfaceVersion {
    private static final Logger LOG = LoggerFactory.getLogger();

    private MethodRegisterUtility() {
        NoInstance.isPossible();
    }

    /**
     * Register all annotated methods of the class {@code klass} so that they
     * can be accessed from BASIC.
     *
     * @param klass       the class that contains the static methods to register
     * @param interpreter the interpreter to register the methods into as BASIC
     *                    functions
     * @throws BasicRuntimeException when a function is double defined and not an identical manner
     */
    public static void registerFunctions(final Class<?> klass,
                                         final Interpreter interpreter) throws BasicRuntimeException {

        final var method = new FunctionLoadParameters();

        for (final Method methodObj : klass.getMethods()) {
            method.initParameters(methodObj, klass);
            if (method.isFunctionAnnotated()) {
                if (!Modifier.isStatic(methodObj.getModifiers())) {
                    LOG.error(
                            "Method {}, {} , {}, {} is NOT STATIC, CAN NOT BE REGISTERED",
                            method.alias, method.methodName, method.klass,
                            method.classifications);
                    throw new BasicRuntimeException("Method " + methodObj + " is not static.");
                }
                method.setAlias();

                method.setMethodName();

                method.setClass();

                method.setClassification();

                LOG.debug("Registering {}, {} , {}, {}", method.alias,
                    method.methodName, method.klass, method.classifications);
                method.register(interpreter);
            }
        }
    }

    /**
     * Looks up the classifications in the configuration and calculates the
     * numerical value of the different allow and deny configurations. Each
     * configuration should have the form:
     * <p>
     * <pre>
     *   allow(com.scriptbasic.classificaton.Math) = 20
     *   deny(com.scriptbasic.classification.System) = 30
     * </pre>
     * <p>
     * The numeric values are summed up: allow values are added, deny values are
     * subtracted. If the final result is negative then the method is not
     * allowed to be registered. If the final value is zero or positive then the
     * method is to be registered.
     *
     * @param classifications the array of classification class usually fetched from the
     *                        static method annotation
     * @return true if the configuration allows the registering of the method
     */
    private static boolean classificationsAllowRegistering(
            final Interpreter interpreter, final Class<?>[] classifications) {
        if (classifications == null) {
            throw new BasicInterpreterInternalError("Some of the extension functions do not have classifications.\n" +
                    "Since this is Java code, it is an internal error of the host application.");
        }
        final var config = interpreter.getConfiguration();
        final var allowLevel = new AtomicInteger(0);
        for (final Class<?> classification : classifications) {
            final var name = classification.getName();
            allowLevel.addAndGet(config.getConfigValue("allow(" + name + ")").map(Integer::parseInt).orElse(0) -
                    config.getConfigValue("deny(" + name + ")").map(Integer::parseInt).orElse(0));
        }
        return allowLevel.get() >= 0;
    }

    private static class FunctionLoadParameters {
        Class<?> klass;
        String methodName;
        String alias;
        Class<?>[] parameterTypes;
        BasicFunction annotation;
        Class<?>[] classifications;

        void initParameters(final Method method, final Class<?> klassPar) {
            klass = klassPar;
            methodName = method.getName();
            parameterTypes = method.getParameterTypes();
            annotation = method.getAnnotation(BasicFunction.class);
        }

        void setAlias() {
            alias = annotation.alias();
            if (alias.length() == 0) {
                alias = methodName;
            }
        }

        void setMethodName() {
            if (annotation.substituteMethod().length() > 0) {
                methodName = annotation.substituteMethod();
            }
        }

        void setClass() {
            if (annotation.substituteClass() != BasicFunction.class) {
                klass = annotation.substituteClass();
            }
        }

        void setClassification() {
            classifications = annotation.classification();
            if (classifications.length == 1
                    && classifications[0] == BasicFunction.class) {
                classifications = null;
            }
        }

        boolean versionIsCompatible() {
            final var requiredVersion = annotation.requiredVersion();
            if (annotation.requiredVersion() > EXTENSION_INTERFACE_VERSION) {
                LOG.error(
                        "The method {} can not be registered, because it requires the interface version {} and the implemented version is {}.",
                        methodName, requiredVersion, EXTENSION_INTERFACE_VERSION);
            }
            return requiredVersion <= EXTENSION_INTERFACE_VERSION;
        }

        void register(final Interpreter interpreter)
                throws BasicRuntimeException {
            if (versionIsCompatible()) {
                if (classificationsAllowRegistering(interpreter, classifications)) {
                    interpreter.registerJavaMethod(alias, klass, methodName, parameterTypes);
                } else {
                    LOG.info("Classification prevents the registration of the method {}", methodName);
                }
            }
        }

        boolean isFunctionAnnotated() {
            return annotation != null;
        }
    }
}
