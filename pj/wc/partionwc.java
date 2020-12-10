package pj.wc;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Partitioner;

public class partionwc extends Partitioner<Text, IntWritable>{
	@Override
	public int getPartition(Text key, IntWritable val, int pnum) {
		return 0;
	}
}
