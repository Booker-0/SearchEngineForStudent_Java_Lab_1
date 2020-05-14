package hust.cs.javacourse.search.parse.impl;

import hust.cs.javacourse.search.index.AbstractTermTuple;
import hust.cs.javacourse.search.parse.AbstractTermTupleFilter;
import hust.cs.javacourse.search.parse.AbstractTermTupleStream;
import hust.cs.javacourse.search.util.StopWords;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
/**
 * @author :李裕阳
 * 实现了过滤器，基于停用词进行过滤
 * 所有停用词均被过滤
 */
public class StopWordTermTupleFilter extends AbstractTermTupleFilter {

    private List<String> stopwords;

    /**
     * 构造函数
     *
     * @param input ：Filter的输入，类型为AbstractTermTupleStream
     */
    public StopWordTermTupleFilter(AbstractTermTupleStream input) {
        super(input);
        stopwords = new ArrayList<String>(Arrays.asList(StopWords.STOP_WORDS));
    }

    /**
     * 获得下一个三元组
     *
     * @return: 下一个三元组；如果到了流的末尾，返回null
     */
    @Override
    public AbstractTermTuple next() {
        AbstractTermTuple t = input.next();
        if(t == null) return null;
        while(stopwords.contains(t.term.getContent())){    // 是停用词
            t = input.next();
            if(t == null) return null;
        }
        return t;
    }
}
