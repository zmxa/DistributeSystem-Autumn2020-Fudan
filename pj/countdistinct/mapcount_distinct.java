package pj.countdistinct;

import java.io.IOException;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

//approximation, but fast!
public class mapcount_distinct extends Mapper<LongWritable, Text, Text, IntWritable> {
	@Override
	protected void map(LongWritable key,Text value,Context context)throws IOException,InterruptedException{
		String line = value.toString();
		//only work for tritext only!
		//can be modified to fit other text.class!
		String[] temp = line.split("\\s+");
		context.write(new Text(temp[2]), new IntWritable(1));
	}
}