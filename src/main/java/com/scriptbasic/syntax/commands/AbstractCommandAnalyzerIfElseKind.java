package com.scriptbasic.syntax.commands;

import com.scriptbasic.executors.commands.AbstractCommandIfElseKind;
import com.scriptbasic.context.Context;
import com.scriptbasic.interfaces.AnalysisException;

/**
 * @author Peter Verhas
 * date Jul 13, 2012
 */
public abstract class AbstractCommandAnalyzerIfElseKind extends
        AbstractCommandAnalyzer {

    public AbstractCommandAnalyzerIfElseKind(final Context ctx) {
        super(ctx);
    }

    protected void registerAndSwapNode(final AbstractCommandIfElseKind node)
            throws AnalysisException {
        registerAndPopNode(node);
        pushNode(node);
    }

    protected void registerAndPopNode(final AbstractCommandIfElseKind node)
            throws AnalysisException {
        ctx.nestedStructureHouseKeeper.pop(AbstractCommandIfElseKind.class).setNext(node);
    }

}
