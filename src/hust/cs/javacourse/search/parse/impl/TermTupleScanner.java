package hust.cs.javacourse.search.parse.impl;

import hust.cs.javacourse.search.index.AbstractTermTuple;
import hust.cs.javacourse.search.index.impl.Term;
import hust.cs.javacourse.search.index.impl.TermTuple;
import hust.cs.javacourse.search.parse.AbstractTermTupleScanner;
import hust.cs.javacourse.search.util.Config;
import hust.cs.javacourse.search.util.StringSplitter;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

/**
 * @author :李裕阳
 *     AbstractTermTupleScanner是AbstractTermTupleStream的抽象子类，即一个具体的TermTupleScanner对象就是
 *     一个AbstractTermTupleStream流对象，它利用java.io.BufferedReader去读取文本文件得到一个个三元组TermTuple.
 *
 *     其具体子类需要重新实现next方法获得文本文件里的三元组
 */
public class TermTupleScanner extends AbstractTermTupleScanner {

    private int pos;            // 用于标记单词在文档中的位置
    private LinkedList<String> buf;   // 用于缓存分词后的单词序列
    StringSplitter splitter = new StringSplitter();
    /**
     * 缺省构造函数
     */
    public TermTupleScanner(){
        this.pos = 0;
        buf = new LinkedList<String>();
        splitter.setSplitRegex(Config.STRING_SPLITTER_REGEX);
    }

    /**
     * 构造函数
     * @param input：指定输入流对象，应该关联到一个文本文件
     */
    public TermTupleScanner(BufferedReader input){
        super(input);
        this.pos = 0;
        buf = new LinkedList<String>();     // java.lang.NullPointerException if "buf.clear()"
        splitter.setSplitRegex(Config.STRING_SPLITTER_REGEX);
    }

    /**
     * 获得下一个三元组
     *
     * @return: 下一个三元组；如果到了流的末尾，返回null
     */
    @Override
    public AbstractTermTuple next() {
        try {
//            if(buf.isEmpty()){      // 缓冲区空了就再读一行
            while (buf.isEmpty()){      // 改成while，防止遇到空行
                String line = this.input.readLine();
                if(line == null) return null;   // 输入流空了
                buf = new LinkedList<String>(splitter.splitByRegex(line));
//                if(buf.isEmpty()) return null;        // 这里返回null会导致遇到空行就停止扫描
            }
            String word = buf.getFirst();
            buf.removeFirst();
            return new TermTuple(word.toLowerCase(), this.pos++);     // pos从0开始，大写转小写
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

}
