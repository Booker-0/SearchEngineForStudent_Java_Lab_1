package hust.cs.javacourse.search.parse.impl;

import hust.cs.javacourse.search.index.AbstractTermTuple;
import hust.cs.javacourse.search.parse.AbstractTermTupleFilter;
import hust.cs.javacourse.search.parse.AbstractTermTupleStream;
import hust.cs.javacourse.search.util.Config;

/**
 * @author :李裕阳
 * 实现了过滤器，基于正则表达式进行过滤
 * 所有含有非英文字符均被过滤
 */
public class PatternTermTupleFilter extends AbstractTermTupleFilter {

    private String patten;

    /**
     * 构造函数
     *
     * @param input ：Filter的输入，类型为AbstractTermTupleStream
     */
    public PatternTermTupleFilter(AbstractTermTupleStream input) {
        super(input);
        patten = Config.TERM_FILTER_PATTERN;
    }

    /**
     * 获得下一个三元组
     * 基于正则表达式来过滤
     * @return: 下一个三元组；如果到了流的末尾，返回null
     */
    @Override
    public AbstractTermTuple next() {
        AbstractTermTuple t = input.next();
        if(t == null) return null;
        while(!t.term.getContent().matches(patten)){    // 不满足正则表达式，即含有非字母字符
            t = input.next();
            if(t == null) return null;
        }
        return t;
    }
}
