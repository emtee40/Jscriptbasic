/**
 *
 */
package com.scriptbasic.utility;
import java.lang.reflect.Field;
/**
 * @author Peter Verhas
 * date Aug 2, 2012
 *
 */
public class BeanUtility {
    private BeanUtility() {
        UtilityUtility.throwExceptionToEnsureNobodyCallsIt();
    }
    /**
     * Set the named field of the object be it either inherited by the object
     * class from its parent or declared in the class of the object. Note that
     * the accessibility (private, protected or package access) is overridden.
     * <p>
     * Generally the use of this method is a signal of bad practice. Possible
     * uses are copying values from one bean to another, e.g.: configuration
     * value to bean and using the configuration keys to name the field names.
     * When you design such code bear in mind that accepting the name of the
     * field from some external source usually poses a security risk, you have
     * to trust the source from where you get the field names, since any of the
     * fields can be set using this method, even private fields.
     *
     *
     * @param object
     *            the object in which the field has to be set. Note that if the
     *            object is a {@code Class<?>} then the method {@see
     *            #setField(Class, String, Object)} will be invoked.
     * @param fieldName
     *            the name of the field
     * @param value
     *            the new value of the field
     * @throws NoSuchFieldException
     *             if the field is not declared in the class of the object and
     *             also there is no such field inherited from any parent
     * @throws SecurityException
     *             if the code is not allowed to execute reflection code
     *             controlled by the SecurityManager
     * @throws IllegalArgumentException
     *             if reflection throws this exception, usually the type of the
     *             {@code value} is not compatible with the named field
     * @throws IllegalAccessException
     *             if the field can not be set. This is not expected to be
     *             thrown since the code sets the accessibility of the field.
     */
    public static void setField(final Object object, final String fieldName,
            final Object value) throws NoSuchFieldException, SecurityException,
            IllegalArgumentException, IllegalAccessException {
        setField(null, object, fieldName, value);
    }
    /**
     * Set the named static field in the class {@code klass}. This method
     * performs the same function as {@see #setField(Object, String, Object)}
     * except that it sets static fields.
     * <p>
     * For more information on the arguments and the exceptions see the
     * documentation of the method {@see #setField(Object, String, Object)}.
     *
     * @param klass
     *            the class of which the static field to be set.
     * @param fieldName
     * @param value
     * @throws NoSuchFieldException
     * @throws SecurityException
     * @throws IllegalArgumentException
     * @throws IllegalAccessException
     */
    public static void setField(final Class<?> klass, final String fieldName,
            final Object value) throws NoSuchFieldException, SecurityException,
            IllegalArgumentException, IllegalAccessException {
        setField(klass, null, fieldName, value);
    }
    private static void setField(final Class<?> klass, final Object object,
            final String fieldName, final Object value)
            throws NoSuchFieldException, SecurityException,
            IllegalArgumentException, IllegalAccessException {
        Field field;
        Class<?> kLass = klass;
        if (kLass == null) {
            kLass = object.getClass();
        }
        try {
            field = kLass.getDeclaredField(fieldName);
        } catch (final NoSuchFieldException e0) {
            try {
                field = kLass.getField(fieldName);
            } catch (final NoSuchFieldException e1) {
                field = null;
            }
        }
        if (field == null) {
            throw new NoSuchFieldException(fieldName + " in "
                    + object.getClass());
        }
        field.setAccessible(true);
        field.set(object, value);
    }
}