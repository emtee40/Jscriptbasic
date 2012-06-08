package com.scriptbasic.lexer;

import com.scriptbasic.interfaces.LexicalException;
import com.scriptbasic.interfaces.Reader;

public class BasicLexicalException extends LexicalException {

    private static final long serialVersionUID = 8068731911556013119L;

    public BasicLexicalException() {
        super();
    }

    public BasicLexicalException(final String arg0, final Throwable arg1) {
        super(arg0, arg1);
    }

    public BasicLexicalException(final String arg0) {
        super(arg0);
    }

    public BasicLexicalException(final Throwable arg0) {
        super(arg0);
    }

    private String fileName;
    private int lineNumber;
    private int position;

    public void setPosition(final Reader reader) {
        this.fileName = reader.fileName();
        this.lineNumber = reader.lineNumber();
        this.position = reader.position();
    }

    @Override
    public String fileName() {
        return this.fileName;
    }

    @Override
    public int lineNumber() {
        return this.lineNumber;
    }

    @Override
    public int position() {
        return this.position;
    }

}
