package com.scriptbasic.syntax;

import com.scriptbasic.exceptions.LexicalException;
import com.scriptbasic.exceptions.SyntaxException;
import com.scriptbasic.interfaces.*;
import com.scriptbasic.log.Logger;
import com.scriptbasic.log.LoggerFactory;

import java.util.Stack;

public abstract class AbstractNestedStructureHouseKeeper implements NestedStructureHouseKeeper {
    private static final Logger LOG = LoggerFactory.getLogger();
    private static final Structure MATCH_NOTHING = new Structure() {
        public <T> boolean match(Class<T> expectedClass) {
            return false;
        }
    };
    private final Stack<Structure> stack = new Stack<>();
    private final LexicalAnalyzer analyzer;
    private boolean stackIsHealthy = true;

    protected AbstractNestedStructureHouseKeeper(LexicalAnalyzer analyzer) {
        this.analyzer = analyzer;
    }

    @Override
    public void push(final NestedStructure element) {
        push(element.getClass(), element);
    }

    protected boolean isStackIsHealthy() {
        return stackIsHealthy;
    }

    @Override
    public void push(Class<?> klass, NestedStructure element) {
        Structure stackFrame = new Structure();
        stackFrame.setElementType(klass);
        stackFrame.setPushedElement(element);
        stack.push(stackFrame);
    }

    @SuppressWarnings("unchecked")
    @Override
    public <T extends NestedStructure> T pop(Class<T> expectedClass)
            throws AnalysisException {
        Structure stackFrame = stack.isEmpty() ? MATCH_NOTHING : stack.peek();
        if (!stackFrame.match(expectedClass)) {
            stackIsHealthy = false;
            final SyntaxException se = new BasicSyntaxException("Bad nested structures");
            try {
                se.setLocation(analyzer.peek());
            } catch (LexicalException e) {
                LOG.error("There was an error when trying to fetch the current source location", e);
            }
            throw se;
        } else {
            stack.pop();
        }
        return (T) (stackFrame == null ? null : stackFrame.getPushedElement());
    }

    protected static class Structure {
        private Class<?> elementType;
        private NestedStructure pushedElement;

        public Class<?> getElementType() {
            return elementType;
        }

        public void setElementType(Class<?> elementType) {
            this.elementType = elementType;
        }

        public NestedStructure getPushedElement() {
            return pushedElement;
        }

        public void setPushedElement(NestedStructure pushedElement) {
            this.pushedElement = pushedElement;
        }

        public <T> boolean match(Class<T> expectedClass) {
            return expectedClass.isAssignableFrom(getElementType());
        }
    }
}
