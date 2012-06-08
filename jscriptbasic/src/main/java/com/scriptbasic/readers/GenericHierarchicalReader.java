package com.scriptbasic.readers;

import java.util.Stack;

import com.scriptbasic.interfaces.HierarchicalReader;
import com.scriptbasic.interfaces.Reader;
import com.scriptbasic.interfaces.SourceProvider;

public class GenericHierarchicalReader implements HierarchicalReader {
    private Reader reader;

    private final Stack<Reader> readerStack = new Stack<Reader>();

    /**
     * Include a new reader into the chain and start to use that child reader so
     * long as long exhausts.
     * 
     * @param reader
     */
    @Override
    public void include(final Reader reader) {
        if (this.reader != null) {
            this.readerStack.push(this.reader);
        }
        this.reader = reader;
    }

    @Override
    public void set(final String sourceFileName) {
        this.reader.set(sourceFileName);
    }

    @Override
    public String fileName() {
        return this.reader.fileName();
    }

    @Override
    public int lineNumber() {
        return this.reader.lineNumber();
    }

    @Override
    public int position() {
        return this.reader.position();
    }

    @Override
    public void pushBack(final Integer ch) {
        this.reader.pushBack(ch);
    }

    /**
     * {@inheritDoc}
     * 
     * This version implements hierarchical reading. When a source finishes, it
     * returns to the parent reader and continues reading from there.
     */
    @Override
    public Integer get() {
        Integer ch = this.reader.get();
        while (ch == null && !this.readerStack.isEmpty()) {
            this.reader = this.readerStack.pop();
            ch = this.reader.get();
        }
        return ch;
    }

    @Override
    public SourceProvider getSourceProvider() {
        return this.reader.getSourceProvider();
    }
}
