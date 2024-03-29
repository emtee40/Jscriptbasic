package com.scriptbasic.syntax.commands;

import com.scriptbasic.executors.commands.AbstractCommandLeftValueListed;
import com.scriptbasic.executors.commands.CommandLocal;
import com.scriptbasic.factories.Context;

/**
 * @author Peter Verhas
 * date Jul 12, 2012
 */
public class CommandAnalyzerLocal extends AbstractCommandAnalyzerGlobalLocal {

    public CommandAnalyzerLocal(Context ctx) {
        super(ctx);
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.scriptbasic.syntax.commandanalyzers.AbstractCommandAnalyzerGlobalLocal
     * #newNode()
     */
    @Override
    protected AbstractCommandLeftValueListed newNode() {
        return new CommandLocal();
    }

}
