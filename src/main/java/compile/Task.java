package compile;

import common.FileUtil;

import java.io.File;
import java.io.IOException;

//描述一次编译运行过程
public class Task {
    //所有的临时文件都放在tmp目录中
    private static final String WORK_DIR = "./tmp/";
    //要编译代码的类名
    private static final String CLASS = "Solution";
    //编译代码的文件名，和类名一致
    private static final String CODE = WORK_DIR + "Solution.java";
    //标准输入对应的文件（没用到）
    private static final String STDIN = WORK_DIR+ "stdin.txt";
    //标准输出对应的文件（编译代码输出结果保存到这个文件中）
    private static final String STDOUT = WORK_DIR+ "stdout.txt";
    //标准错误对应的文件（编译代码输出的结果保存到这个文件中）
    private static final String STDERR = WORK_DIR+ "stderr.txt";
    //编译错误对应的文件（编译出错的原因）
    private static final String COMPILE_ERROR = WORK_DIR+ "compile_error.txt";

    public Answer compileAndRun(Question question) throws IOException, InterruptedException {
        Answer answer = new Answer();
        //先创建好存放临时文件的目录
        File workDir = new File(WORK_DIR);
        if(!workDir.exists()){
            workDir.mkdirs();
        }
        //1.根据Question对象，构造需要的一些临时文件
        FileUtil.writeFile(CODE,question.getCode());
        FileUtil.writeFile(STDIN,question.getStdin());
        //2.构造编译命令，并执行  javac -encodeing utf-8 ./tmp/Solution.java    -d ./tmp/
        // String cmd = "javac -encodeing utf-8" + "CODE" + "-d" + WORK_DIR;
        String cmd = String.format("javac -encoding utf-8 %s -d %s",CODE,WORK_DIR);
        System.out.println("编译命令：" + cmd);
        CommandUtil.run(cmd,null,COMPILE_ERROR);
        //判定一下编译是否出错，如果出错，就不需要继续运行了
        String compileError = FileUtil.readFile(COMPILE_ERROR);
        if(!"".equals(compileError)){
            System.out.println("编译出错");
            answer.setError(1);
            answer.setReason(compileError);
            return answer;
        }
        //3.构造运行命令，并执行
        //为了能够找到 .class文件，需要指定加载路径-classpath
        cmd = String.format("java -classpath %s %s",WORK_DIR,CLASS);
        System.out.println("运行命令：" + cmd);
        CommandUtil.run(cmd,STDOUT,STDERR);

        String stdError =  FileUtil.readFile(STDERR);
        if(!"".equals(stdError)){
            System.out.println("运行出错");
            answer.setError(2);
            answer.setReason(stdError);
            answer.setStderr(stdError);
            return answer;
        }

        //4.将最终运行结果包装到Answer中
        answer.setError(0);
        answer.setStdout(FileUtil.readFile(STDOUT));
        return answer;
    }

    public static void main(String[] args) throws IOException, InterruptedException {
        // 验证一下 Task 是否能正确运行
        Question question = new Question();
        question.setCode(
                "public class Solution {\n" +
                        "   public static void main(String[] args) {\n" +
                        "       String s = null;" +
                        "       System.out.println(s.length());\n" +
                        "   }\n" +
                        "}\n"
        );
        question.setStdin("");
        Task task = new Task();
        Answer answer = task.compileAndRun(question);
        System.out.println(answer);
    }
}
