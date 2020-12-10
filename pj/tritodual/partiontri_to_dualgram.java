package pj.tritodual;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.mapreduce.Partitioner;

import pj.DualText;

public class partiontri_to_dualgram extends Partitioner<DualText, IntWritable>{
	static int testnum = 0;
	@Override
	public int getPartition(DualText key, IntWritable val, int pnum) {
		return (key.w0.hashCode() & Integer.MAX_VALUE)%pnum;
	}
}
