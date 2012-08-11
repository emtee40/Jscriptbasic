/**
 *
 */
package com.scriptbasic.executors.commands;
import com.scriptbasic.interfaces.ExecutionException;
import com.scriptbasic.interfaces.ExtendedInterpreter;
/**
 * @author Peter Verhas
 * date Jul 20, 2012
 *
 */
public class CommandNext extends AbstractCommand {
    private CommandFor loopStartNode;
    /**
     * @return the loopStartNode
     */
    public CommandFor getLoopStartNode() {
        return loopStartNode;
    }
    /**
     * @param loopStartNode
     *            the loopStartNode to set
     */
    public void setLoopStartNode(final CommandFor loopStartNode) {
        this.loopStartNode = loopStartNode;
    }
    /*
     * (non-Javadoc)
     *
     * @see
     * com.scriptbasic.executors.commands.AbstractCommand#execute(com.scriptbasic
     * .interfaces.ExtendedInterpreter)
     */
    @Override
    public void execute(final ExtendedInterpreter interpreter)
            throws ExecutionException {
        loopStartNode.stepLoopVariable(interpreter);
    }
}