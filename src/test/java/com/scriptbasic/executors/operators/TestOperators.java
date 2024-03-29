package com.scriptbasic.executors.operators;

import com.scriptbasic.interfaces.AnalysisException;
import com.scriptbasic.interfaces.BasicRuntimeException;
import com.scriptbasic.interfaces.ExecutionException;
import com.scriptbasic.interfaces.ExtendedInterpreter;
import com.scriptbasic.log.Logger;
import com.scriptbasic.log.LoggerFactory;
import org.junit.Assert;
import org.junit.Test;

import java.lang.reflect.InvocationTargetException;

import static com.scriptbasic.executors.operators.SupportTest.assertValueOfVariable_A;
import static com.scriptbasic.executors.operators.SupportTest.eval;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

/**
 * @author Peter Verhas
 * date June 23, 2012
 */

public class TestOperators {
    private static Logger log = LoggerFactory.getLogger();


    private static void a(final String s, Object expected)
            throws AnalysisException, ExecutionException {
        ExtendedInterpreter eInterpreter = eval(s);
        eInterpreter.execute();
        assertValueOfVariable_A(eInterpreter, expected);
    }

    private static void b(final String s, Object bVal, Object expected)
            throws AnalysisException, ExecutionException {
        ExtendedInterpreter eInterpreter = eval(s);
        eInterpreter.setVariable("b", bVal);
        eInterpreter.execute();
        assertValueOfVariable_A(eInterpreter, expected);

    }

    private static void c(final String s, Object bVal, Object cVal,
                          Object expected) throws AnalysisException, ExecutionException {
        ExtendedInterpreter eInterpreter = eval(s);
        eInterpreter.setVariable("b", bVal);
        eInterpreter.setVariable("c", cVal);
        eInterpreter.execute();
        assertValueOfVariable_A(eInterpreter, expected);

    }

    @Test
    public void testWhile() throws AnalysisException, ExecutionException {
        a("a=1\nwhile a < 10\na=a+1\nwend\n", 10);
    }

    @Test
    public void testOperators() throws AnalysisException,
            ExecutionException {
        a("a=1^3", 1.0);
        a("a=1*2", 2L);
        a("a=1.0*2", 2.0);
        a("a=1.0-2", -1.0);
        a("a=2/2", 1L);
        a("a= 3/2", 3.0 / 2.0);
        a("a= 3.2/2.0", 3.2 / 2.0);
        a("a= 3.2/2", 3.2 / 2.0);
        a("a=3-2", 1);
        a("a=3+2", 5);
        a("a=3%2", 1);
        a("a= 3.0 % 2.0", 1.0);
        a("a= 3 % 2.0", 1.0);
        a("a= 3.0 % 2", 1.0);
        a("a=+3-3", 0);
        a("a=+3.2+-2", 1.2);
        a("a=-3.4", -3.4);
        a("a= not a", true);
        a("a= not false", true);
        a("a= not true", false);
        a("a= 3 < 2", false);
        a("a= 3 > 2", true);
        a("a= 3 <= 2", false);
        a("a= 3 >= 2", true);
        a("a= 3 <> 2", true);
        a("a= 3 = 2", false);
        a("a= 3 <> 3", false);
        a("a= 3 = 3", true);

        a("a= \"x\" = \"x\"", true);
        a("a= \"x\" = \"y\"", false);
        a("a= false = false", true);
        a("a= true = true", true);
        a("a= false = true", false);
        a("a= true = false", false);

        a("a= 3.0 < 2", false);
        a("a= 3.0 > 2", true);
        a("a= 3.0 <= 2", false);
        a("a= 3.0 >= 2", true);
        a("a= 3.0 <> 2", true);
        a("a= 3.0 = 2", false);
        a("a= 3.0 <> 3.0", false);
        a("a= 3.0 <> 2.0", true);
        a("a= 3.0 = 3.0", true);

        a("a= 3.0 < 2.0", false);
        a("a= 2.0 < 3.0", true);
        a("a= 3.0 > 2.0", true);
        a("a= 3.0 <= 2.0", false);
        a("a= 3.0 >= 2.0", true);
        a("a= 3.0 <> 2.0", true);
        a("a= 3.0 = 2.0", false);
        // c("a= 3.0 <> 3", false);
        a("a= 3.0 = 3", true);
        a("a= \"x\" <> \"x\"", false);
        a("a= \"x\" <> \"y\"", true);
        a("a= \"x\" < \"y\"", true);
        a("a= \"y\" < \"x\"", false);
        a("a = false <> false", false);
        a("a = true <> true", false);
        a("a = true <> false", true);
        a("a = false <> true", true);

        a("a= false < true", true);
        a("a= false > true", false);
        a("a= false > false", false);
        a("a= true < true", false);
        a("a= true > true", false);
        a("a= false < false", false);

        a("a= false <= true", true);
        a("a= false >= true", false);
        a("a= false >= false", true);
        a("a= true <= true", true);
        a("a= true >= true", true);
        a("a= false <= false", true);

        a("a= \"apple\" <= \"apple\"", true);
        a("a= \"apple\" <= \"apale\"", false);
        a("a= \"apple\" < \"apple\"", false);
        a("a= \"apple\" < \"apale\"", false);

        a("a= \"apple\" >= \"apple\"", true);
        a("a= \"apple\" >= \"apale\"", true);
        a("a= \"apple\" > \"apple\"", false);
        a("a= \"apple\" > \"apale\"", true);

        a("a= \"x\" and \"\"", false);
        a("a= \"x\" or \"\"", true);
        a("a= \"x\" and \"z\"", true);
        a("a= \"x\" or \"z\"", true);
        a("a= 13.2 or false", true);

        try {
            a("a= \"13.2\" * 2.0", 26.4);
        } catch (BasicRuntimeException bre) {
        }

        a("a= 1 + true", "1true");

        a("a = 3 + \"hahhhh\"", "3hahhhh");
        a("a = 3.0 + \"hahhhh\"", "3.0hahhhh");
        a("a = true + \"a\"", "truea");
        a("a= true or false", true);
        a("a = true or (6 / 0)", true);
        a("a= 1 or 2", true);
        a("a=false or false", false);

        a("a=false and true", false);
        a("a=false and false", false);
        a("a=true and false ", false);
        a("a=true and true", true);

        a("a=0 and 1", false);
        a("a=0 and 0", false);
        a("a=1 and 0 ", false);
        a("a=1 and 1", true);

        a("a=1 div 1", 1);
        a("a= 1 div 3", 0);
        a("a=1.0 div 3.0", 0);

        try {
            a("a= -\"13\"", 0);
            fail();
        } catch (BasicRuntimeException bre) {

        }
        new PowerOperator().operatorName();
        new MultiplyOperator().operatorName();
        new MinusOperator().operatorName();
        new AddOperator().operatorName();
        new DivideOperator().operatorName();
        new IntegerDivideOperator().operatorName();
        new ModuloOperator().operatorName();
    }

    @Test
    public void testFunctionCall() throws Exception {
        a("sub apple(x)\nx=x+1\nreturn x\nendsub\na=apple(2)\n", 3);
    }

    @Test
    public void testObjectSet() throws AnalysisException,
            ExecutionException, NoSuchMethodException, SecurityException,
            IllegalAccessException, IllegalArgumentException,
            InvocationTargetException {
        b("b.www=55\na=b.www", new qqq(), 55);
        b("b.ccc.www=55\na=b.getQwww()", new qqq(), 55);
    }

    @Test
    public void testArraySetGet() throws Exception {
        b("c[13]=55\na=c[13]", null, 55);
        b("c[13,14]=66\n\na=c[13,14]", null, 66);
        qq b = new qq();
        b.arr[3] = 777;
        b("a=b.arr[3]", b, 777);
        b("z[12]=b", b, null);
    }

    @Test
    public void testObjectAccess() throws AnalysisException,
            ExecutionException, NoSuchMethodException, SecurityException,
            IllegalAccessException, IllegalArgumentException,
            InvocationTargetException {
        // b("program string", preset value for variable 'b', expected value of
        // 'a' after execution)
        b("a=b", 23L, 23);

        class zzz {
            public String toString() {
                return "zzz";
            }
        }

        class ttt implements Comparable<ttt> {
            private int t;

            ttt(int t) {
                this.t = t;
            }

            @Override
            public int compareTo(ttt o) {
                return Integer.compare(t, o.t);
            }

        }
        try {
            b("a= b = b", new zzz(), true);
            fail();
        } catch (BasicRuntimeException bre) {
        }
        try {
            b("a= b <> b", new zzz(), true);
            fail();
        } catch (BasicRuntimeException bre) {
        }
        try {
            b("a= b < b", new zzz(), false);
            fail();
        } catch (BasicRuntimeException bre) {
        }
        c("a= b < c", new ttt(1), new ttt(2), true);
        c("a= b > c", new ttt(1), new ttt(2), false);
        c("a= b < c", new ttt(2), new ttt(1), false);
        c("a= b > c", new ttt(2), new ttt(1), true);

        c("a= b <= c", new ttt(1), new ttt(2), true);
        c("a= b >= c", new ttt(1), new ttt(2), false);
        c("a= b <= c", new ttt(2), new ttt(1), false);
        c("a= b >= c", new ttt(2), new ttt(1), true);

        c("a= b <> c", new ttt(1), new ttt(2), true);
        c("a= b <> c", new ttt(1), new ttt(1), false);
        c("a= b = c", new ttt(1), new ttt(2), false);

        b("a=b+\"\"", new zzz(), "zzz");

        b("a=b.www", new qqq(), 13);
        b("a=b.fff", new qqq(), 13.0);
        b("a=b.ccc.www", new qqq(), 13);

        b("a=b.zpq()", new zp(), 14);
        zp ZP = new zp();
        qqq Q = ZP.getQ();

        b("a=b.oneArg(13)", ZP, Q);
    }

    public static class qqq {
        public Integer www = 13;
        public Float fff = (float) 13.0;
        public qq ccc = new qq();

        public Integer getQwww() {
            return ccc.www;
        }
    }

    public static class qq {
        public Integer www = 13;
        public Float fff = (float) 13.0;
        public Integer arr[] = new Integer[4];
    }

    public static class zp {
        qqq Q = new qqq();

        qqq getQ() {
            return Q;
        }

        public Integer zpq() {
            log.debug("zpq() was called");
            return 14;
        }

        public qqq oneArg(Long z) {
            log.debug("oneArg was called: {}", z);
            return Q;
        }
    }
}
