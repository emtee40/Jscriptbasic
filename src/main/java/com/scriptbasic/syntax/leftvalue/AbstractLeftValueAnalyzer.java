package com.scriptbasic.syntax.leftvalue;

import com.scriptbasic.errors.BasicInterpreterInternalError;
import com.scriptbasic.executors.leftvalues.ArrayElementAccessLeftValueModifier;
import com.scriptbasic.executors.leftvalues.BasicLeftValue;
import com.scriptbasic.executors.leftvalues.LeftValueModifier;
import com.scriptbasic.executors.leftvalues.ObjectFieldAccessLeftValueModifier;
import com.scriptbasic.factories.Context;
import com.scriptbasic.interfaces.*;

/**
 * Left value is defined as
 * <p>
 * <pre>
 * LEFTVALUE ::= identifier modifier*
 * modifier  ::= '[' expression_list '] | '.' id
 * </pre>
 *
 * @author Peter Verhas
 * date June 12, 2012
 */
public abstract class AbstractLeftValueAnalyzer implements LeftValueAnalyzer {
    protected final Context ctx;

    protected AbstractLeftValueAnalyzer(Context ctx) {
        this.ctx = ctx;
    }

    private static LeftValueModifier analyzeFieldAccess(
            LexicalAnalyzer lexicalAnalyzer) throws AnalysisException {
        lexicalAnalyzer.get();
        ObjectFieldAccessLeftValueModifier lvm = new ObjectFieldAccessLeftValueModifier();
        LexicalElement lexicalElement = lexicalAnalyzer.peek();
        if (lexicalElement != null && lexicalElement.isIdentifier()) {
            lexicalAnalyzer.get();
            lvm.setFieldName(lexicalElement.getLexeme());
            return lvm;
        }
        throw new BasicSyntaxException(
                "Left value . is not followed by a field name", lexicalElement,
                null);
    }

    private static boolean isModifierStart(LexicalElement lexicalElement) {
        return lexicalElement != null
                && (lexicalElement.isSymbol(".") || lexicalElement
                .isSymbol("["));
    }

    private static boolean isArrayAccessStart(LexicalElement lexicalElement) {
        return lexicalElement != null && lexicalElement.isSymbol("[");
    }

    private static boolean isFieldAccessStart(LexicalElement lexicalElement) {
        return lexicalElement != null && lexicalElement.isSymbol(".");
    }

    private LeftValueModifier analyzeArrayAccess(LexicalAnalyzer lexicalAnalyzer)
            throws AnalysisException {
        lexicalAnalyzer.get();
        ArrayElementAccessLeftValueModifier lvm = new ArrayElementAccessLeftValueModifier();

        ExpressionList indexList = ctx.expressionListAnalyzer.analyze();
        lvm.setIndexList(indexList);
        LexicalElement lexicalElement = lexicalAnalyzer.peek();
        if (lexicalElement != null && lexicalElement.isSymbol("]")) {
            lexicalAnalyzer.get();
            return lvm;
        }
        throw new BasicSyntaxException(
                "Left value array access does not have ]", lexicalElement, null);
    }

    @Override
    public LeftValue analyze() throws AnalysisException {
        BasicLeftValue leftValue;
        LexicalElement lexicalElement = ctx.lexicalAnalyzer.peek();
        if (lexicalElement != null && lexicalElement.isIdentifier()) {
            ctx.lexicalAnalyzer.get();
            leftValue = new BasicLeftValue();
            leftValue.setIdentifier(lexicalElement.getLexeme());
            lexicalElement = ctx.lexicalAnalyzer.peek();
            while (isModifierStart(lexicalElement)) {
                final LeftValueModifier modifier;
                if (isArrayAccessStart(lexicalElement)) {
                    modifier = analyzeArrayAccess(ctx.lexicalAnalyzer);
                } else if (isFieldAccessStart(lexicalElement)) {
                    modifier = analyzeFieldAccess(ctx.lexicalAnalyzer);
                } else {
                    throw new BasicInterpreterInternalError(
                            "left value parsing internal error, there is a modifier with unknown type");
                }
                leftValue.addModifier(modifier);
                lexicalElement = ctx.lexicalAnalyzer.peek();
            }
        } else {
            throw new BasicSyntaxException(
                    "left value should start with an identifier",
                    lexicalElement, null);
        }
        return leftValue;
    }
}
