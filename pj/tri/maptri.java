package pj.tri;
import java.io.IOException;
import java.util.List;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import com.huaban.analysis.jieba.JiebaSegmenter;
import com.huaban.analysis.jieba.SegToken;

import com.huaban.analysis.jieba.JiebaSegmenter.SegMode;

import pj.TriText;

//使用中文分词的手段
public class maptri extends Mapper<LongWritable, Text, TriText, IntWritable>{
	private IntWritable num = new IntWritable(1);
	@Override
	protected void map(LongWritable key,Text value,Context context)throws IOException,InterruptedException{
		String rawline = value.toString();
		System.out.println(0);
		JiebaSegmenter segmenter = new JiebaSegmenter();
		for(String line :rawline.split("\n")) {
			String[] t1 = line.split("<[\\\\\\w/]+>");
			if(t1.length<=1)return;
			List<SegToken> li=segmenter.process(t1[1], SegMode.SEARCH);
			int len = li.size();if (len<=2) return;
			for(int i= 2; i<len;i++) {
				SegToken st0 = li.get(i-2);
				SegToken st1 = li.get(i-1);
				SegToken st2 = li.get(i);
				String w0 = (st0.properties=="")?"&":st0.word;
			    String w1 = (st1.properties=="")?"&":st1.word;
			    String w2 = (st2.properties=="")?"&":st2.word;
			    if(w0=="&" && (w1=="&" || w2=="&") || w1=="&" && w2=="&") continue;
			    TriText w = new TriText(w0,w1,w2);
			    context.write(w, num);
			}
		}
	}
}
