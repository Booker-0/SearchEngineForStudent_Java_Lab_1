package hust.cs.javacourse.search.index.impl;

import hust.cs.javacourse.search.index.AbstractTerm;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * Term 表示一个文档中的一个单词
 * @author :李裕阳
 */

public class Term extends AbstractTerm {

    /**
     * 默认构造函数
     */
    public Term(){}

    /**
     * 额外的构造函数
     * @param content ：单词的字符串表示
     */
    public Term(String content){
        this.content = content;
    }
    /**
     * @param obj ：要比较的另外一个Term
     * @return  :比较结果
     */
    @Override
    public boolean equals(Object obj) {
        if(obj instanceof Term){    // 先判断类型是否相同
            return this.content.equals(((Term) obj).content);
        }
        return false;
    }

    /**
     * @return ：Term返回content字符串，外加双引号
     */
    @Override
    public String toString() {
//        return "\"" + this.content + "\"";
        return this.content;        // 为了满足测试需要
    }

    /**
     * @return ：返回content
     */
    @Override
    public String getContent() {
        return this.content;
    }

    /**
     * @param content：Term的内容
     */
    @Override
    public void setContent(String content) {
        this.content = content;
    }

    /**
     * @param o： 要比较的Term对象
     * @return ：content字符串比较的结果
     */
    @Override
    public int compareTo(AbstractTerm o) {
        return this.content.compareTo(o.getContent());
    }

    /**
     * 向输出流中写入content内容
     * @param out :输出流对象
     */
    @Override
    public void writeObject(ObjectOutputStream out) {
        try {
//            out.writeUTF(this.content);       // 无法通过测试
            out.writeObject(this.content);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    /**
     * 从输入流中读取字符串并赋值给content
     * @param in ：输入流对象
     */
    @Override
    public void readObject(ObjectInputStream in) {
        try {
//            this.content = in.readUTF();          // 无法通过测试
            this.content = (String) in.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
