package pj.tri;
import java.io.IOException;

import java.util.regex.*;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import pj.TriText;

//过滤所有非中文字符后逐字处理
public class maptri_per_word extends Mapper<LongWritable, Text, TriText, IntWritable>{
	private IntWritable num = new IntWritable(1);
	@Override
	protected void map(LongWritable key,Text value,Context context)throws IOException,InterruptedException{
		String rawline = value.toString();
		for(String line :rawline.split("\n")) {
			String[] t1 = line.split("<[\\\\\\w/]+>");
		
			Pattern p = Pattern.compile("([^\u4e00-\u9fa5])+");
			if(t1.length<=1)return;
			Matcher m = p.matcher(t1[1]);
			String[] li = m.replaceAll("&").split("");
			int len = li.length;if (len<=2) return;
			   
			for(int i= 2; i<len;i++) {
				String w0 = li[i-2];
				String w1 = li[i-1];
				String w2 = li[i];
				if(w0=="&" && w2=="&") continue;
			    TriText w = new TriText(w0,w1,w2);
			    context.write(w, num);
			}
		}
	}
}
