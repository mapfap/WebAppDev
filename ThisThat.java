import java.io.IOException;
import java.util.StringTokenizer;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

public class ThisThat {

      public static class TokenizerMapper
    extends Mapper<Object, Text, Text, Text>{

    private final static IntWritable one = new IntWritable(1);
    private Text word = new Text("all this");
    private Text word2 = new Text("all that");

    public void map(Object key, Text value, Context context
        ) throws IOException, InterruptedException {
//        StringTokenizer itr = new StringTokenizer(value.toString());
//        while (itr.hasMoreTokens()) {
//      word.set(itr.nextToken());
//      context.write(word, one);
//       }
    String line = value.toString().toLowerCase();
    if (line.contains("all this")) {
       context.write(word, value);
    }
    if (line.contains("all that")) {
              context.write(word2, value);
    }

    
    }
      }

      public static class IntSumReducer
    extends Reducer<Text,Text,Text,Text> {
    private IntWritable result = new IntWritable();

    public void reduce(Text key, Iterable<Text> values,
                                  Context context
           ) throws IOException, InterruptedException {
        String sum = "";
        for (Text val : values) {
      sum += val.toString() + ",";
        }
      //  result.set(sum);
        context.write(key, new Text(sum));
    }
      }

    public static void main(String[] args) throws Exception {
  Configuration conf = new Configuration();
  Job job = Job.getInstance(conf, "word count");
  job.setJarByClass(ThisThat.class);
  job.setMapperClass(TokenizerMapper.class);
  job.setCombinerClass(IntSumReducer.class);
  job.setReducerClass(IntSumReducer.class);
  job.setOutputKeyClass(Text.class);
  job.setOutputValueClass(Text.class);
  FileInputFormat.addInputPath(job, new Path(args[0]));
  FileOutputFormat.setOutputPath(job, new Path(args[1]));
  System.exit(job.waitForCompletion(true) ? 0 : 1);
    }
}