package pj.tri;
import java.io.IOException;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

import pj.TriText;

public class reducetri extends Reducer<TriText, IntWritable, TriText, IntWritable>{
		
		public void reduce(TriText key,Iterable<IntWritable> vals,Context context)
				throws IOException,InterruptedException{
			int count = 0;
			for (@SuppressWarnings("unused") IntWritable val: vals) count++;
			context.write(key, new IntWritable(count));
		}
	}