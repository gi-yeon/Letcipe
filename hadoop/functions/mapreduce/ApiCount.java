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

public class ApiCount {

    // 맵
    public static class TokenizerMapper extends Mapper<Object, Text, Text, IntWritable> {

        private final static IntWritable one = new IntWritable(1);
        private Text word = new Text();

        public void map(Object key, Text value, Context context) throws IOException, InterruptedException {
            String methodName = logParser(value.toString());
            word.set(methodName);
            context.write(word,one);
        }
        public static String logParser(String log) {
            String[] tokens = log.split(" \\[")[2].split("\\{");
            String[] split = tokens[2].split(",");
            String[] methodToken = split[2].split(":");
            String methodName = methodToken[1].substring(1, methodToken[1].length() - 1);
            return methodName;
        }
    }

    // 리듀스
    public static class IntSumReducer extends Reducer<Text, IntWritable, Text, IntWritable> {
        private IntWritable result = new IntWritable();

        // 키(메서드)별로 전달된 문자의 개수를 모두 더하여 출력
        public void reduce(Text key, Iterable<IntWritable> values, Context context) throws IOException, InterruptedException {
            int sum = 0;
            for (IntWritable val : values) {
                sum += val.get();
            }
            result.set(sum);
            context.write(key, result);
        }
    }

    public static void main(String[] args) throws Exception {
        // Job 객체를 이용하여 하둡 작업을 실행
        Configuration conf = new Configuration();
        Job job = Job.getInstance(conf, "apicount");
        job.setJarByClass(ApiCount.class);
        job.setMapperClass(TokenizerMapper.class);
        job.setCombinerClass(IntSumReducer.class);
        job.setReducerClass(IntSumReducer.class);
        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(IntWritable.class);
        FileInputFormat.addInputPath(job, new Path(args[0]));  // 입력 파일위치
        FileOutputFormat.setOutputPath(job, new Path(args[1]));  // 출력 파일위치
        System.exit(job.waitForCompletion(true) ? 0 : 1);
    }
}