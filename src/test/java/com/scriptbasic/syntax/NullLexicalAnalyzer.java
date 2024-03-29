package com.scriptbasic.syntax;

import com.scriptbasic.interfaces.AnalysisException;
import com.scriptbasic.interfaces.LexicalAnalyzer;
import com.scriptbasic.interfaces.LexicalElement;
import com.scriptbasic.interfaces.LexicalElementAnalyzer;

public class NullLexicalAnalyzer implements LexicalAnalyzer {
    @Override
    public LexicalElement get() throws AnalysisException {
        return new NullLexicalElement();
    }

    @Override
    public LexicalElement peek() throws AnalysisException {
        return new NullLexicalElement();
    }

    @Override
    public void registerElementAnalyzer(LexicalElementAnalyzer lea) {

    }
}
