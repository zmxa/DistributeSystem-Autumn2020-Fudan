package pj.tri;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.mapreduce.Partitioner;

import pj.TriText;

public class partiontri extends Partitioner<TriText, IntWritable>{
	static int testnum = 0;
	@Override
	public int getPartition(TriText key, IntWritable val, int pnum) {
		return (key.w0.hashCode() & Integer.MAX_VALUE)%pnum;
	}
}
