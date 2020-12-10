package pj.tri;

import java.io.IOException;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.mapreduce.Reducer;

import pj.TriText;

public class combinetri extends Reducer<TriText, IntWritable, TriText, IntWritable>{
	@Override
	public void reduce(TriText key,Iterable<IntWritable> vals,Context context)
			throws IOException,InterruptedException{
		int count = 0;
		for (@SuppressWarnings("unused") IntWritable val: vals) count++;
		context.write(key, new IntWritable(count));
	}
}
