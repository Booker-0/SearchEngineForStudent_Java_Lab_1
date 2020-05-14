package hust.cs.javacourse.search.index.impl;

import hust.cs.javacourse.search.index.AbstractDocument;
import hust.cs.javacourse.search.index.AbstractDocumentBuilder;
import hust.cs.javacourse.search.index.AbstractTermTuple;
import hust.cs.javacourse.search.parse.AbstractTermTupleStream;
import hust.cs.javacourse.search.parse.impl.LengthTermTupleFilter;
import hust.cs.javacourse.search.parse.impl.PatternTermTupleFilter;
import hust.cs.javacourse.search.parse.impl.StopWordTermTupleFilter;
import hust.cs.javacourse.search.parse.impl.TermTupleScanner;

import java.io.*;

/**
 * @author :李裕阳
 * AbstractDocumentBuilder是Document构造器的抽象父类.
 *      Document构造器的功能应该是由解析文本文档得到的TermTupleStream，产生Document对象.
 */
public class DocumentBuilder extends AbstractDocumentBuilder {
    /**
     * 由解析文本文档得到的TermTupleStream,构造Document对象.
     * @param docId             : 文档id
     * @param docPath           : 文档绝对路径
     * @param termTupleStream   : 文档对应的TermTupleStream
     * @return ：Document对象
     */
    @Override
    public AbstractDocument build(int docId, String docPath, AbstractTermTupleStream termTupleStream) {
        AbstractDocument doc = new Document(docId, docPath);
        AbstractTermTuple t = termTupleStream.next();
        while(t != null){
            doc.addTuple(t);
            t = termTupleStream.next();
        }
        return doc;
    }

    /**
     * 由给定的File,构造Document对象.
     * 该方法利用输入参数file构造出AbstractTermTupleStream子类对象后,内部调用
     *      AbstractDocument build(int docId, String docPath, AbstractTermTupleStream termTupleStream)
     * @param docId     : 文档id
     * @param docPath   : 文档绝对路径
     * @param file      : 文档对应File对象
     * @return          : Document对象
     */
    @Override
    public AbstractDocument build(int docId, String docPath, File file) {
        AbstractTermTupleStream termTupleStream = null;
        try {
            termTupleStream =
                    new LengthTermTupleFilter(new PatternTermTupleFilter(new StopWordTermTupleFilter(
                            new TermTupleScanner(new BufferedReader(new InputStreamReader( new FileInputStream(file)))))));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return this.build(docId, docPath, termTupleStream);
    }

    public static void main(String[] args) {
        AbstractDocumentBuilder builder = new DocumentBuilder();
        String path = "C:\\Users\\Administrator\\Documents\\大三下课程\\java课\\实验\\Java新实验一\\SearchEngineForStudent\\text\\2.txt";
        System.out.println(builder.build(0, path, new File(path)).toString());
    }
}
