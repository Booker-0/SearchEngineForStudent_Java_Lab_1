package hust.cs.javacourse.search.index.impl;

import hust.cs.javacourse.search.index.AbstractDocument;
import hust.cs.javacourse.search.index.AbstractDocumentBuilder;
import hust.cs.javacourse.search.index.AbstractIndex;
import hust.cs.javacourse.search.index.AbstractIndexBuilder;
import hust.cs.javacourse.search.util.Config;
import hust.cs.javacourse.search.util.FileUtil;

import java.io.File;
import java.io.IOException;

/**
 * @author :李裕阳
 * AbstractIndexBuilder是索引构造器的抽象父类
 *      需要实例化一个具体子类对象完成索引构造的工作
 * IndexBuilder是具体子类
 */
public class IndexBuilder extends AbstractIndexBuilder {

    private int docNum;      // 记录文档数量

    /**
     * 构造函数
     * @param docBuilder :文档构造器
     */
    public IndexBuilder(AbstractDocumentBuilder docBuilder) {
        super(docBuilder);
        docNum = 0;
    }

    /**
     * 构建指定目录下的所有文本文件的倒排索引.
     *      需要遍历和解析目录下的每个文本文件, 得到对应的Document对象，再依次加入到索引，并将索引保存到文件.
     * @param rootDirectory ：指定目录
     * @return ：构建好的索引
     */
    @Override
    public AbstractIndex buildIndex(String rootDirectory) {
        AbstractIndex index = new Index();
        for(String path: FileUtil.list(rootDirectory)){ // 遍历目录中的所有文件
            AbstractDocument doc = this.docBuilder.build(docNum++, path, new File(path));   // 文章索引从0开始
            index.addDocument(doc);     // 把文档加入倒排索引中
        }
        File file = new File(Config.INDEX_DIR + "index.dat");
        if(!file.exists()){
            try {
                if(!file.createNewFile())
                    throw new IOException();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        index.save(file);
        return index;
    }
}

