# Java lab1心得

实验1是要求实现

搜索引擎的倒排索引数据结构

#### 实验1知识点:

1. 集合类的使用如ArrayList，HashMap

   ◦对这些集合类的操作包括：遍历、添加、排序，得到集合的大小、判断集合里是否已存在指定元素

2. 第三是熟悉对象的序列化和反序列化

   作为类的方法来实现下面二个方法

   ◦public abstract void writeObject(ObjectOutputStream out);

   ◦public abstract void readObject(ObjectInputStream in);

   

3. 要熟悉对文件，特别是文本文件的操作

   如果读取文本文件，推荐使用BufferedReader

   如果写文本文件，推荐使用PrintWriter，当创建好PrintWriter对象后，调用其println和print方法可以将字符串一行行的写入到文本文件，使用方法与System.out.println, System.out.print完全一样 

   具体使用方法，请见hust.cs.javacourse.search.util.FileUtils类的read方法和write方法

4. 设计的设计模式：装饰者模式和策略模式

   

#### 实验1需完成目标:

1. 实现hust.cs.javacourse.search.index

   即倒排索引的数据结构和建立倒排索引

2. 实现hust.cs.javacourse.parser

   将内容切分成一个个的单词

   过滤掉其中一些不需要的单词,例如数字、停用词（the, is and这样的单词）、过短或过长的单词（例如长度小于3或长度大于20的单词）

   

3. 实现hust.cs.javacourse.search.query

   基于构建好的索引，实现单个搜索关键词的全文检索

   基于构建好的索引，实现二个搜索关键词的全文检索

   

#### 作为HashMap的key的自定义类需要重写hashCode()和equals()

hashCode默认比较是其地址值，如果是比较对象的属性，则需要重写

一般先写hashCode再写equals

重写equal的原则是先判断是不是同一类再判断对象的成员属性是不是都equal

```java
 class Person {  

2.    

3.      //都一样，变化的就是下面的  

4.      public int hashCode() {  

5.          return name.hashCode() + age * 10;  

6.      }  

7.    

8.      public boolean equals(Object obj) {  

9.          if (!(obj instanceof Person))  

10.             throw new ClassCastException("类型不匹配");  

11.         Person p = (Person) obj;  

12.         return this.name.equals(p.getName()) && this.age == p.getAge();  

13.     }  

14. }  


————————————————
版权声明：本文为CSDN博主「郑学炜」的原创文章，遵循CC 4.0 BY-SA版权协议，转载请附上原文出处链接及本声明。
原文链接：https://blog.csdn.net/u014590757/article/details/79501332
```

一般对于存放到Set集合或者Map中键值对的元素，都需要按需要重写hashCode

与equals方法来保证唯一性

如果只是重写了equals而hashCode没有重写则会出现两个对象内容相等的情况在Map中从而不满足唯一性，因为hashMap是使用哈希表的链地址法来解决hash冲突的

equals是用来判断HashMap的中键值对的Value是否相等，先用hashCode来在数组中定位，然后在链表中找对应的key，默认的equals可能不符合要求，从而需要重写



#### Java doc注释

https://www.runoob.com/java/java-documentation.html

/**开始 */结束



HashMap中键值对内容相同，但顺序不同也是相等的

所以不能用(this.positions.equals(((Posting) obj).positions))而是应该用

```
(this.positions.containsAll(((Posting) obj).positions)) &&
(((Posting) obj).positions.containsAll(this.positions))
```

即containsAll来比较,且需要是双向的



AbstractDocument中文档的绝对路径需要修改



要满足自动测试需要

抽 象 类 AbstractIndex 的 数 据 成 员
docIdToDocPathMapping、 termToPostingListMapping 的访问控制权限要改成公有  

改成公有的原因是这二个数据成员没有定义公有的 getter 和 setter， 因此测试代码无法
检测其中内容的正确性  