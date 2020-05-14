package hust.cs.javacourse.search.query.impl;

import hust.cs.javacourse.search.index.AbstractPosting;
import hust.cs.javacourse.search.index.AbstractTerm;
import hust.cs.javacourse.search.query.AbstractHit;
import hust.cs.javacourse.search.util.FileUtil;

import java.util.Map;

/**
 * @author :李裕阳
 * AbstractHit是一个搜索命中结果的抽象类. 该类子类要实现Comparable接口.
 * 实现该接口是因为需要必须比较大小，用于命中结果的排序.
 * Hit是具体的实现类
 */
public class Hit extends AbstractHit {
    /**
     * 默认构造函数
     */
    public Hit(){}

    /**
     * 构造函数
     * @param docId     : 文档id
     * @param docPath   : 文档绝对路径
     */
    public Hit(int docId, String docPath){
        super(docId, docPath);
    }

    /**
     * 构造函数
     * @param docId              ：文档id
     * @param docPath            ：文档绝对路径
     * @param termPostingMapping ：命中的三元组列表
     */
    public Hit(int docId, String docPath, Map<AbstractTerm, AbstractPosting> termPostingMapping){
        super(docId, docPath, termPostingMapping);
    }

    /**
     * 获得文档id
     *
     * @return ： 文档id
     */
    @Override
    public int getDocId() {
        return this.docId;
    }

    /**
     * 获得文档绝对路径
     *
     * @return ：文档绝对路径
     */
    @Override
    public String getDocPath() {
        return this.docPath;
    }

    /**
     * 获得文档内容
     *
     * @return ： 文档内容
     */
    @Override
    public String getContent() {
        return this.content;
    }

    /**
     * 设置文档内容
     *
     * @param content ：文档内容
     */
    @Override
    public void setContent(String content) {
        this.content = content;
    }

    /**
     * 获得文档得分
     *
     * @return ： 文档得分
     */
    @Override
    public double getScore() {
        return this.score;
    }

    /**
     * 设置文档得分
     *
     * @param score ：文档得分
     */
    @Override
    public void setScore(double score) {
        this.score = score;
    }

    /**
     * 获得命中的单词和对应的Posting键值对
     *
     * @return ：命中的单词和对应的Posting键值对
     */
    @Override
    public Map<AbstractTerm, AbstractPosting> getTermPostingMapping() {
        return this.termPostingMapping;
    }

    /**
     * 获得命中结果的字符串表示, 用于显示搜索结果.
     * 为了可读性，只显示docID和score
     * @return : 命中结果的字符串表示
     */
    @Override
    public String toString(){
        StringBuffer buf = new StringBuffer("[");
        buf.append(this.docPath).append(", ");
        buf.append(this.content).append(", ");
        buf.append(this.termPostingMapping.toString()).append("]");
        return buf.toString();
    }
//    public String toString() {
//        StringBuffer buf = new StringBuffer("{\tdocPath = ");
//        buf.append(this.docPath).append(",\n");
//        buf.append("\tdocID = ").append(this.docId).append(",\n");
//        buf.append("\tscore = ").append(this.score).append(",\n");
//        int times = 0;
//        for(AbstractPosting p : this.termPostingMapping.values()){
//            times += p.getPositions().size();
//        }
//        buf.append("\ttimes = ").append(times).append(",\n");
//        buf.append("\tcontent =").append('\n');
////        buf.append(this.content).append("\n}");
//        String content_o = content;
//        for(AbstractTerm t : this.termPostingMapping.keySet()){
//            String regex = t.getContent();      // 高亮
//            content_o = content_o.replaceAll(regex.replaceAll("\\s", "\\\\s"),
//                    "\033[32m" + regex + "\033[0m");
//        }
//        buf.append(content_o).append("\n}");
//        return buf.toString();
//    }

    /**
     * 比较二个命中结果的大小，根据score比较
     *
     * @param o ：要比较的名字结果
     * @return ：二个命中结果得分的差值
     */
    @Override
    public int compareTo(AbstractHit o) {
        return (int) Math.round(this.score - o.getScore());
    }
}
