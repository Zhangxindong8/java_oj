package common;

import java.io.*;
//这是一个简单的工具类，方便我们更方便的读写文件
public class FileUtil {
    //读文件：一下把整个文件内容都读到String中
    public static String readFile(String filePath){
        //当前涉及到的编译错误，标准输出结果等文件内容都是文本文件，此处使用字符流的方式来实现
        try(FileReader fileReader = new FileReader(filePath);
            BufferedReader bufferedReader = new BufferedReader(fileReader)){
            StringBuilder stringBuilder = new StringBuilder();
            //按行读取文件内容
            String line = "";
            while((line = bufferedReader.readLine()) != null){
                stringBuilder.append(line);
            }
            return stringBuilder.toString();
        }catch (IOException e){
            e.printStackTrace();
        }
        return null;
    }
    //写文件：一下把整个文件都写到指定文件中
    //filePath表示要把文件写到那个文件中，content表示写的文件内容
    public static void writeFile(String filePath,String content){
        try(FileWriter fileWriter = new FileWriter(filePath)){
            fileWriter.write(content);
        }catch(IOException e){
            e.printStackTrace();
        }
    }
}
