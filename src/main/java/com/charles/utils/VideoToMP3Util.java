package com.charles.utils;

import com.charles.conf.Constant;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * MP4转MP3
 */
public class VideoToMP3Util {

    /**
     * 批量
     * @param inputFilePath
     * @param outputFilePath
     */
    public static void MP4ToMP3Batch(String inputFilePath,String outputFilePath) {
        File[] files = new File(inputFilePath).listFiles();
        File outputFile = new File(outputFilePath);
        if(!outputFile.exists()){
            outputFile.mkdir();
        }
        for (int i = 0; i < files.length; i++) {
            File file = files[i];
            if(file.isDirectory()|| file.getName().startsWith(".DS")){
                continue;
            }
            String name = file.getName();
            String absolutePath = file.getAbsolutePath();
            if(name.contains(" ")){
                name = removeLeadingDots(name);
                absolutePath = file.getParentFile().getAbsolutePath() + "/" + name;
                System.out.println("修改后的文件路径 = " + absolutePath);
                File newFile = new File(absolutePath);
                boolean isRenamed = file.renameTo(newFile);
                if (isRenamed) {
                    System.out.println("File renamed successfully.");
                } else {
                    System.out.println("Failed to rename file.");
                }
            }
            String outname = name.substring(0,name.length()-1)+"3";
            outname = removeLeadingDots(outname);
            System.out.print("开始转换 = " + outname+",当前进度："+(i+1)+"/"+files.length+ "  ");
            MP4ConverterMP3(absolutePath,outputFilePath + "/" + outname);
        }

    }


    public static void MP4ConverterMP3(String inputFilePath,String outputFilePath) {
        try {
            File outputFile = new File(Constant.MP3_PATH);
            if(!outputFile.exists()){
                outputFile.mkdir();
            }
            // 构建 FFmpeg 命令
            String command = String.format(Constant.FFMPEG_COMMAND, Constant.FFMPEG_PATH, inputFilePath, outputFilePath);

            // 执行命令
            Process process = Runtime.getRuntime().exec(command);

            // 获取命令输出
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }

            // 等待命令执行完成
            int exitCode = process.waitFor();
            if (exitCode == 0) {
                String fileName = outputFilePath.substring(outputFilePath.lastIndexOf("/") + 1);
                System.out.println(fileName + "转换成功！");
            } else {
                System.out.println("转换失败！command = " + command);
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    //去掉文件名中的"."和" "
    public static String removeLeadingDots(String str) {
        int lastIndex = str.lastIndexOf(".");

        if (lastIndex == -1) {
            return str;
        }
        int tool=str.length()-lastIndex;
        str = str.replace(".","");
        str = str.replace(" ","");
        int length = str.length();
        String prestring = str.substring(0, length-tool + 1);
        String endstring = str.substring(length-tool + 1, length);
        str = prestring + "." + endstring;
        return str;
    }

}
