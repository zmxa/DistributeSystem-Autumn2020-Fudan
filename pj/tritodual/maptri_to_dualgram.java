package pj.tritodual;

import java.io.IOException;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import pj.DualText;

//for tri-gram-doc only, which should be called on job 2.
public class maptri_to_dualgram extends Mapper<LongWritable, Text, DualText, IntWritable> {
	@Override
	protected void map(LongWritable key,Text value,Context context)throws IOException,InterruptedException{
		String line = value.toString();
		String[] words = line.split("\\s+");
		DualText d = new DualText(words[0],words[1]);
		context.write(d, new IntWritable(Integer.parseInt(words[3])));
	}
}
