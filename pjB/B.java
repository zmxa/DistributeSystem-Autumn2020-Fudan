package pjB;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapred.jobcontrol.JobControl;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.jobcontrol.ControlledJob;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

import pj.*;
import pj.countdistinct.*;
import pj.tri.*;
import pj.tritodual.*;
import pj.wc.*;
//这个也是正式使用的！！！
//B用的是按字分割的运算！！
public class B {

	@SuppressWarnings("deprecation")
	public static void main(String[] args)throws Exception{
		Configuration conf = new Configuration();
		/*
		String[] otherArgs = new GenericOptionsParser(conf, args).getRemainingArgs();
		if(otherArgs.length!=3){
			System.err.println("Usage:InvertedIndex<in><out>");
			System.exit(2);
		}
		*/
		
		//
		
		Job job1 = new Job(conf,"trigram_createB");
		job1.setJarByClass(maptri.class);
		job1.setMapperClass(maptri_per_word.class);
		job1.setCombinerClass(combinetri.class);
		job1.setNumReduceTasks(24);
		job1.setPartitionerClass(partiontri.class);
		job1.setReducerClass(reducetri.class);
		job1.setOutputKeyClass(TriText.class);
		job1.setOutputValueClass(IntWritable.class);
		FileInputFormat.addInputPath(job1, new Path("/corpus/news_sohusite.xml"));
		FileOutputFormat.setOutputPath(job1, new Path("try5/trigramB"));
		ControlledJob cjob1 = new ControlledJob(conf);
		cjob1.setJob(job1);
		
		
		
		Job job2 = new Job(conf,"trigram_countB");
		job2.setJarByClass(maptri_to_dualgram.class);
		job2.setMapperClass(maptri_to_dualgram.class);
		//job2.setCombinerClass(combinetri_to_dualgram.class);
		job2.setNumReduceTasks(24);
		job2.setPartitionerClass(partiontri_to_dualgram.class);
		job2.setReducerClass(reducetri_to_dualgram.class);
		job2.setOutputKeyClass(DualText.class);
		job2.setOutputValueClass(IntWritable.class);
		FileInputFormat.addInputPath(job2, new Path("try5/trigramB"));
		FileOutputFormat.setOutputPath(job2, new Path("try5/tricountB"));
		ControlledJob cjob2 = new ControlledJob(conf);
		cjob2.setJob(job2);
		cjob2.addDependingJob(cjob1);
		
		//count distinct words.
		Job job3 = new Job(conf,"distinct_countB");
		job3.setJarByClass(mapcount_distinct.class);
		job3.setMapperClass(mapcount_distinct.class);
		job3.setCombinerClass(combinecount_distinct.class);
		job3.setNumReduceTasks(1);
		job3.setPartitionerClass(partioncount_distinct.class);
		job3.setReducerClass(reducecount_distinct.class);
		job3.setOutputKeyClass(Text.class);
		job3.setOutputValueClass(IntWritable.class);
		FileInputFormat.addInputPath(job3, new Path("try5/trigramB"));
		FileOutputFormat.setOutputPath(job3, new Path("try5/distinctB"));
		ControlledJob cjob3 = new ControlledJob(conf);
		cjob3.setJob(job3);
		cjob3.addDependingJob(cjob1);
		
		//word count
		Job job4 = new Job(conf,"TAT again");
		job4.setJarByClass(mapwc.class);
		job4.setMapperClass(mapwc.class);
		job4.setCombinerClass(combinewc.class);
		job4.setNumReduceTasks(1);
		job4.setPartitionerClass(partionwc.class);
		job4.setReducerClass(reducewc.class);
		job4.setOutputKeyClass(Text.class);
		job4.setOutputValueClass(IntWritable.class);
		FileInputFormat.addInputPath(job4, new Path("/corpus/news_sohusite.xml"));
		FileOutputFormat.setOutputPath(job4, new Path("try5/wordcountB"));
		ControlledJob cjob4 = new ControlledJob(conf);
		cjob4.setJob(job4);
		JobControl gjob =  new JobControl("i_love_python");
		gjob.addJob(cjob1);gjob.addJob(cjob2);gjob.addJob(cjob3);gjob.addJob(cjob4);
		
		Thread t = new Thread(gjob);
		t.start();
		
		while(true){  
            if(gjob.allFinished()){  
                System.out.println(gjob.getSuccessfulJobList());  
                gjob.stop();  
                return;  
            }  
            if(gjob.getFailedJobList().size() > 0){  
                System.out.println(gjob.getFailedJobList());  
                gjob.stop();  
                return;  
            }  
        }
		
	}
}
