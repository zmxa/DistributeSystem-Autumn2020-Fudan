package pj.tritodual;

import java.io.IOException;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.mapreduce.Reducer;

import pj.DualText;

public class combinetri_to_dualgram extends Reducer<DualText, IntWritable, DualText, IntWritable>{
	@Override
	public void reduce(DualText key,Iterable<IntWritable> vals,Context context)
			throws IOException,InterruptedException{
		int count = 0;
		for (IntWritable val: vals) count+=val.get();
		context.write(key, new IntWritable(count));
	}
}
