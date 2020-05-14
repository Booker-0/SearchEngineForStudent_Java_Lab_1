package hust.cs.javacourse.search.run;

import hust.cs.javacourse.search.index.impl.Term;
import hust.cs.javacourse.search.query.AbstractHit;
import hust.cs.javacourse.search.query.AbstractIndexSearcher;
import hust.cs.javacourse.search.query.impl.IndexSearcher;
import hust.cs.javacourse.search.query.impl.SimpleSorter;
import hust.cs.javacourse.search.util.Config;
import hust.cs.javacourse.search.util.StopWords;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 测试搜索
 */
public class TestSearchIndex {
    /**
     *  搜索程序入口
     * @param args ：命令行参数
     */
    public static void main(String[] args) throws IOException {
        IndexSearcher searcher = new IndexSearcher();
        searcher.open(Config.INDEX_DIR + "index.dat");
        SimpleSorter simpleSorter = new SimpleSorter();
        // 查询一个单词
        String req;
        System.out.print("请输入查询单词: ");
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        while((req = br.readLine()) != null && !req.equals("q")){
            String[] reqs = req.split("[\\s]+");   // 用空白符切分输入的这行
            List<String> stopWords = new ArrayList<String>(Arrays.asList(StopWords.STOP_WORDS));
            for(String s : reqs){
                if(stopWords.contains(s))
                    System.out.println("\033[31mWarning: 停用词: " + s + "\033[0m");
            }
            if(reqs.length < 1){
                System.out.println("请至少输入一个单词");
            } else if(reqs.length == 1){

                AbstractHit[] hits = searcher.search(new Term(req), simpleSorter);
                if(hits == null) System.out.println("未搜索到任何结果");
                else for(AbstractHit h: hits)
                    System.out.println(h.toString());
                System.out.print("请输入查询单词: ");

            } else if(reqs.length == 2){        // 查询两个在文中相邻的单词

                AbstractHit[] hits = searcher.search(new Term(reqs[0]), new Term(reqs[1]), simpleSorter);
                if(hits == null) System.out.println("未搜索到任何结果");
                else for(AbstractHit h: hits)
                    System.out.println(h.toString());
                System.out.print("请输入查询单词: ");

            } else if(reqs.length == 3){        // 查询两个单词，中间用&表示“与”，|表示或

                if(reqs[1].equals("&") || reqs[1].equals("*")){     // 与
                    AbstractHit[] hits = searcher.search(new Term(reqs[0]), new Term(reqs[2]),
                            simpleSorter, AbstractIndexSearcher.LogicalCombination.AND);
                    if(hits == null || hits.length < 1) System.out.println("未搜索到任何结果");
                    else for(AbstractHit h: hits)
                        System.out.println(h.toString());

                } else if(reqs[1].equals("|") || reqs[1].equals("+")){  // 或
                    AbstractHit[] hits = searcher.search(new Term(reqs[0]), new Term(reqs[2]),
                            simpleSorter, AbstractIndexSearcher.LogicalCombination.OR);
                    if(hits == null || hits.length < 1) System.out.println("未搜索到任何结果");
                    else for(AbstractHit h: hits)
                        System.out.println(h.toString());

                } else {
                    System.out.println("\033[31m逻辑关系解析失败\033[0m");
                    System.out.println("输入格式： word combine word");
                    System.out.println("combine :   or: +,|  and: &,*");
                }
                System.out.print("请输入查询单词: ");

            } else {
                System.out.println("查询的单词数过多");
                System.out.print("请输入查询单词: ");
            }
        }
    }
}
