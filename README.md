# 分布式系统 期末作业
### 作业选题
通过Mapreduce框架构建简单Tri-gram模型
***

### 作业简介
&emsp;&emsp;本作业在JavaSE-1.8下编写。需额外将hadoop包加入本地项目依赖项。项目分为两个部分，A部分额外引用了[`jieba分词`](https://github.com/huaban/jieba-analysis) "结巴分词(java版) jieba-analysis" ，通过中文分词的截断句子创建，B部分对中文句使用逐字的截断。

​		两者均对句子中出现的标点符号、数字、英文及它们的组合直接替换为替代字符“&”。对于三元组中出现任两位置出现“&”的，不计入至最终结果。因此在二元组中不会出现“& &”的项。

​		作业进行了简单的add-one smooth，同时制作了简单的python脚本用于概率计算与中文预测。由于语料数据并不充分，A部分（按词划分）并不能得到很好的结果，因此只针对B部分进行了计算。
***

### 文件结构
```
src
├── com.huaban.analysis.jieba               //中文分词相关
├── dict.big.txt                            //中文分词相关
├── dict.txt                                //中文分词相关
├── idf_dict.txt                            //中文分词相关
├── jieba.java.code.style.xml               //中文分词相关
├── prob_emit.txt                           //中文分词相关
├── stop_words.txt                          //中文分词相关
├── pj
│   ├── C.java                              //测试文件
│   ├── countdistinct
│   │   ├── combinecount_distinct.java      //剔除重复
│   │   ├── mapcount_distinct.java          //从三元组中统计不同的字/词有多少
│   │   ├── partioncount_distinct.java      //
│   │   └── reducecount_distinct.java       //输出不同的字/词数
│   ├── DualText.java                       //二元组数据结构
│   ├── tri
│   │   ├── combinetri.java                 //
│   │   ├── maptri.java                     //按词建立三元组
│   │   ├── maptri_per_word.java            //按字建立三元组
│   │   ├── partiontri.java                 //
│   │   └── reducetri.java                  //输出三元组与数目 
│   ├── TriText.java                        //三元组数据结构
│   ├── tritodual
│   │   ├── combinetri_to_dualgram.java     //
│   │   ├── maptri_to_dualgram.java         //从三元组文件构建二元组
│   │   ├── partiontri_to_dualgram.java     //
│   │   └── reducetri_to_dualgram.java      //输出二元组与数目
│   └── wc
│       ├── combinewc.java                  //
│       ├── mapwc.java                      //wordcount
│       ├── partionwc.java                  //
│       └── reducewc.java                   //仅有总字数输出
├── pjA
│   └── A.java                              //按词划分，入口函数
├── pjB
│   └── B.java                              //按字划分，入口函数
└── README.md                               
```

### 数据平滑
在得到全部Tri-gram后，对于形式为 `(a, b, c)`具有次数 `val` 的Tri-gram对，对c进行加和求得Bi-gram`(a,b)`具有次数$\sum\nolimits_{c} val$。最后对全文进行一次`word-count`，求得模糊的总字数$\sum\limits _{voc}$。  
在最终计算时才进行平滑处理，而非在源文件中体现平滑。使用的公式为  
$$ P(w_2|w_0w_1)=\frac{c((w_0,w_1,w_2))+1}{c((w_0,w_1))+|V|}$$
对于$c((w_0,w_1)) = 0$的情况，做模糊处理。
$$ P(w_2|w_0w_1)=\frac{1}{{|V|}}$$
相应的<u>句首第二字</u>概率近似为：
$$P(w_1|w_0)=\frac{c(w_0,w_1)+1}{c(w_0)+|V|}$$
以及在$w_0$不存在时
$$P(w_1|w_0)=\frac{1}{|V|}$$
<u>句首第一字</u>近似概率为：
$$P(w_0) = \frac{c(w_0)}{\sum_{w\in V} c(w)+1}$$
一旦句首字未出现，直接采用下列近似
$$P(w_0) = 1/{\sum_{w\in V} c(w)+1}$$
可以看到，这种概率处理容易过拟合，因此非常需要更大量的数据进行拟合。
***

### 概率预测  
实际上的概率预测就是取出tri_gram中比较多的几个，在脚本中设置为了四个，尽量返回的都是非标点字符。
***



