package pj.wc;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

//approximation, but fast!
public class mapwc extends Mapper<LongWritable, Text, Text, IntWritable> {
	@Override
	protected void map(LongWritable key,Text value,Context context)throws IOException,InterruptedException{
		String rawline = value.toString();
		for(String line :rawline.split("\n")) {
			String[] t1 = line.split("<[\\\\\\w/]+>");
		
			Pattern p = Pattern.compile("([^\u4e00-\u9fa5])+");
			if(t1.length<=1)return;
			Matcher m = p.matcher(t1[1]);
			String[] li = m.replaceAll("&").split("");
			for(String s:li)context.write(new Text(s), new IntWritable(1));
	}
}}