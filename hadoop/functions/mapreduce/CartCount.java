import java.io.IOException;
import java.time.LocalDate;
import java.util.*;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

public class CartCount {
    static int now = LocalDate.now().getYear();
    // 맵
    public static class TokenizerMapper extends Mapper<Object, Text, Text, IntWritable> { // input key type / value type, ouput key type, value type
        private Text word = new Text();

        public void map(Object key, Text value, Context context) throws IOException, InterruptedException {
            // 입력이 들어오면 key, one 형태로 분할해야함
            // tokens = [gender,age,family,job,rid] 순서
            List<String> tokens = logParser(value.toString());
            IntWritable rid = new IntWritable(Integer.parseInt(tokens.get(4)));
            // combination key
            int flag = -1;
            while (++flag < 16) {
                // 플래그 분석하여 key 생성
                StringBuilder sb = new StringBuilder();
                for (int i = 0; i < 4; i++) {
                    if ((flag & (1 << i)) != 0) {
                        sb.append(tokens.get(i));
                    } else {
                        sb.append("-");
                    }
                    sb.append(",");
                }
                sb.setLength(sb.length()-1);
                word.set(sb.toString());

                context.write(word, rid);
            }

        }

        static List<String> logParser(String log) {
            String[] parseSB = log.split("\\[");
            List<String> list = new ArrayList<>();
            String[] parseCB = parseSB[3].split("\\{");
            if (parseCB[1].contains("}")) {
                parseCB[1] = parseCB[1].substring(0, parseCB[1].indexOf('}'));
            }
            String[] parseCL;
            parseCL = parseCB[3].split("\\:|\\}");
            for (int i = 1; i <= 5; i++) {
                if (i == 2 || i == 4)
                    continue;
                if (parseCL[i].contains(",")) {
                    parseCL[i] = parseCL[i].substring(0, parseCL[i].indexOf(','));
                } else {
                    parseCL[i] = parseCL[i].substring(0, parseCL[i].indexOf('}'));
                }
                list.add(parseCL[i]);
            }
            String[] date = list.get(1).split("\\-");
            list.remove(1);
            list.add(1, "" + ((now - Integer.parseInt(date[0])) / 10 * 10));
            list.add(2, parseCL[6]);
            parseCL = parseCB[2].split("\\:");
            parseCL[1] = parseCL[1].substring(0, parseCL[1].indexOf(','));
            list.add(parseCL[1]);

            return list;
        }
    }

    // 리듀스 
    public static class IntSumReducer extends Reducer<Text, IntWritable, Text, Text> {
        private Text resultText = new Text();

        // 키(관심사)별로 전달된 레시피 id별 개수를 텍스트로 반환
        public void reduce(Text key, Iterable<IntWritable> values, Context context) throws IOException, InterruptedException {
            // 각 value별 map을 생성하여 값 넣어주기
            // (rid, count)
            Map<Integer, Integer> map = new HashMap<>();
            for (IntWritable val : values) {
                Integer prev = map.putIfAbsent(val.get(), 1);
                if (prev != null) {
                    map.put(val.get(), prev + 1);
                }
            }
            // map을 value 기준으로 정렬
            List<Map.Entry<Integer, Integer>> entries = new ArrayList<>(map.entrySet());
            entries.sort((o1, o2) -> -o1.getValue().compareTo(o2.getValue()));

            // 결과 Text 만들기
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i< entries.size() && i < 10 ;i++) {
                Map.Entry<Integer, Integer> entry = entries.get(i);
                sb.append(entry.getKey()).append(":").append(entry.getValue());
                sb.append(",");
            }
            sb.setLength(sb.length()-1);

            resultText.set(sb.toString());
            context.write(key, resultText);
        }
    }

    public static void main(String[] args) throws Exception {
        // Job 객체를 이용하여 하둡 작업을 실행 
        Configuration conf = new Configuration();
        Job job = Job.getInstance(conf, "cartcount");
        job.setJarByClass(CartCount.class);
        job.setMapperClass(TokenizerMapper.class);
        job.setReducerClass(IntSumReducer.class);
        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(Text.class);
        job.setMapOutputKeyClass(Text.class);
        job.setMapOutputValueClass(IntWritable.class);
        FileInputFormat.addInputPath(job, new Path(args[0]));  // 입력 파일위치
        FileOutputFormat.setOutputPath(job, new Path(args[1]));  // 출력 파일위치 
        System.exit(job.waitForCompletion(true) ? 0 : 1);
    }
}

/*
-   37:7,26:7,27:7
M   37:8,26:8,27:8
 */