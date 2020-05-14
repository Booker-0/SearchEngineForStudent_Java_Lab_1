package hust.cs.javacourse.search.parse.impl;

import hust.cs.javacourse.search.index.AbstractTermTuple;
import hust.cs.javacourse.search.parse.AbstractTermTupleFilter;
import hust.cs.javacourse.search.parse.AbstractTermTupleStream;

/**
 * @author :李裕阳
 * 实现了过滤器，基于单词的长度进行过滤
 * 长度小于3或大于20均被过滤
 */
public class LengthTermTupleFilter extends AbstractTermTupleFilter {

    /**
     * 构造函数
     *
     * @param input ：Filter的输入，类型为AbstractTermTupleStream
     */
    public LengthTermTupleFilter(AbstractTermTupleStream input) {
        super(input);
    }

    /**
     * 获得下一个三元组
     * 过滤长度小于3或长度大于20的单词
     * @return: 下一个三元组；如果到了流的末尾，返回null
     */
    @Override
    public AbstractTermTuple next() {
        AbstractTermTuple t = input.next();
        if(t == null) return null;
        while(t.term.getContent().length() < 3 || t.term.getContent().length() > 20){
            t = input.next();
            if(t == null) return null;
        }
        return t;
    }
}
