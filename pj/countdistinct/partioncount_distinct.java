package pj.countdistinct;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Partitioner;

public class partioncount_distinct extends Partitioner<Text, IntWritable>{
	@Override
	public int getPartition(Text key, IntWritable val, int pnum) {
		return 0;
	}
}
