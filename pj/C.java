package pj;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapred.jobcontrol.JobControl;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.jobcontrol.ControlledJob;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

import pj.countdistinct.combinecount_distinct;
import pj.countdistinct.mapcount_distinct;
import pj.countdistinct.partioncount_distinct;
import pj.countdistinct.reducecount_distinct;

//JUST FOR TEST!!!!
//FOR STUPID PURPOSE ONLY!!!


//public class C {
//
//	@SuppressWarnings("deprecation")
//	public static void main(String[] args)throws Exception{
//		Configuration conf = new Configuration();
//		
//		Job job3 = new Job(conf,"dcA");
//		job3.setJarByClass(mapcount_distinct.class);
//		job3.setMapperClass(mapcount_distinct.class);
//		job3.setCombinerClass(combinecount_distinct.class);
//		job3.setNumReduceTasks(1);
//		job3.setPartitionerClass(partioncount_distinct.class);
//		job3.setReducerClass(reducecount_distinct.class);
//		job3.setOutputKeyClass(Text.class);
//		job3.setOutputValueClass(IntWritable.class);
//		FileInputFormat.addInputPath(job3, new Path("try5/trigramA"));
//		FileOutputFormat.setOutputPath(job3, new Path("try5/distinctA"));
//		ControlledJob cjob3 = new ControlledJob(conf);
//		cjob3.setJob(job3);
//		
//		Job job4 = new Job(conf,"dcB");
//		job4.setJarByClass(mapcount_distinct.class);
//		job4.setMapperClass(mapcount_distinct.class);
//		job4.setCombinerClass(combinecount_distinct.class);
//		job4.setNumReduceTasks(1);
//		job4.setPartitionerClass(partioncount_distinct.class);
//		job4.setReducerClass(reducecount_distinct.class);
//		job4.setOutputKeyClass(Text.class);
//		job4.setOutputValueClass(IntWritable.class);
//		FileInputFormat.addInputPath(job4, new Path("try5/trigramB"));
//		FileOutputFormat.setOutputPath(job4, new Path("try5/distinctB"));
//		ControlledJob cjob4 = new ControlledJob(conf);
//		cjob4.setJob(job4);
//		
//		
//		JobControl gjob =  new JobControl("i_love_python");
//		gjob.addJob(cjob3);gjob.addJob(cjob4);
//		Thread t = new Thread(gjob);
//		t.start();
//		
//		while(true){  
//            if(gjob.allFinished()){  
//                System.out.println(gjob.getSuccessfulJobList());  
//                gjob.stop();  
//                return;  
//            }  
//            if(gjob.getFailedJobList().size() > 0){  
//                System.out.println(gjob.getFailedJobList());  
//                gjob.stop();  
//                return;  
//            }  
//        }
//		}
//	}


public class C {

//	@SuppressWarnings("deprecation")
	public static void main(String[] args)throws Exception{
		System.out.println(("&".hashCode()&Integer.MAX_VALUE)%24);
	}
}
//job.waitForCompletion(true);