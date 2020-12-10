package com.huaban.analysis.jieba;

public class SegToken {
    public String word;

    public int startOffset;

    public int endOffset;

    public String properties;


    public SegToken(String word, int startOffset, int endOffset, String properties) {
        this.word = word;
        this.startOffset = startOffset;
        this.endOffset = endOffset;
        this.properties = properties;
    }


    @Override
    public String toString() {
        return "[" + word + ", " + startOffset + ", " + endOffset + ", " + properties + "]";
    }

}