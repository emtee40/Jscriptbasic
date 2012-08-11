package com.scriptbasic.lexer.elements;
import com.scriptbasic.exceptions.LexicalException;
import com.scriptbasic.interfaces.LexicalElement;
import com.scriptbasic.interfaces.LexicalElementAnalyzer;
import com.scriptbasic.interfaces.Reader;
public abstract class AbstractElementAnalyzer implements LexicalElementAnalyzer {
    private Reader reader;
    public Reader getReader() {
        return this.reader;
    }
    @Override
    public void setReader(final Reader reader) {
        this.reader = reader;
    }
    @Override
    public abstract LexicalElement read() throws LexicalException;
}