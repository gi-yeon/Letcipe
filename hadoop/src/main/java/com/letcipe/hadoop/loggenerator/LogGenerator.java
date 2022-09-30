import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Hello world!
 */
public class LogGenerator {
    static int now = LocalDate.now().getYear();

    public static void main(String[] args) throws IOException {
        addLog(args[0],Integer.parseInt(args[1]));
    }

    public enum JOB {
        FREE, WORKER, COOK, JUBU
    }

    public static void addLog(String base,int loopCount) throws IOException {
        // loop
        Random rnd = new Random();
        // 파일 디렉토리 생성
        // 지난 한달 사이의 랜덤한 날짜로 뽑기
        for (int day = 10; day < 30; day++) {
            String date = String.format("2022-09-%d", day);

            String path = base + date;
            for (int i = 0; i < 10; i++) {
                // 파일 생성
                File dir = new File(path);
                if (!dir.exists())
                    dir.mkdirs();
                File file = new File(path + String.format("/cartlog.%s.%d.log", date, i));
                BufferedWriter bw = new BufferedWriter(new FileWriter(file));

                for (int l = 0; l < loopCount; l++) {
                    // 랜덤하게 로그 한줄씩 추가
                    StringBuilder sb = new StringBuilder();
                    int h = rnd.nextInt(14) + 10;
                    int m = rnd.nextInt(50) + 10;
                    int s = rnd.nextInt(50) + 10;


                    String time = String.format("%d:%d:%d", h, m, s);
                    int rid = rnd.nextInt(1000);
                    String category = "R" + rnd.nextInt(15);
                    int uid = rnd.nextInt(1000);
                    String gender = rnd.nextBoolean() ? "F" : "M";
                    String birth = String.format("199%d-0%d-22", rnd.nextInt(9) + 1, rnd.nextInt(9) + 1);
                    JOB job = JOB.values()[rnd.nextInt(4)];
                    int family = rnd.nextInt(4) + 1;
                    String log = String.format("{RECIPE:{id:%d,category:%s},USER:{gender:%s,nickname:admin,birth:%s,id:%d,job:%s,family:%d}}", rid, category, gender,
                            birth, uid, job.toString(), family);
                    // 파일 저장
                    sb.append(String.format("[INFO ] [%s %s] [%s]", date, time, log));
                    bw.write(sb.toString());
                    bw.write("\n");
                    // loop end
                }
                bw.flush();
                bw.close();
            }
        }
    }

    public void combinationTest() {
        System.out.println("Hello World!");
        String value = "[INFO ] [2022-09-28 13:27:46] [{RECIPE:{id:26,category:R0001},USER:{gender:M,nickname:admin,birth:1997-03-17,id:1,job:WORKER,family:4}}]";
        List<String> tokens = logParser(value);
        StringBuilder result = new StringBuilder();
        int flag = 0;
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
            sb.setLength(sb.length() - 1);
            result.append(sb.toString()).append("\n");
        }
        System.out.println(result.toString());
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
