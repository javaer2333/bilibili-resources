package com.charles.utils;


import com.charles.conf.Constant;

import java.io.*;

public class FileUtil {

    static String author = "薛之谦";

    public static void main(String[] args) {

        changeFileNameBatch("/Users/fanjiawei/Documents/杂/音乐/"+author);

        System.out.println("批量任务处理完毕 ！");
    }

    /**
     * 批量调用
     * @param filePath
     */
    public static void changeFileNameBatch(String filePath){
        File parentFile = new File(filePath);
        File[] files = parentFile.listFiles();
        for (File file : files) {
            if (file.getName().startsWith(".D") ) {//  || file.getName().contains(author)
                continue;
            }
            changeFileName(file.getAbsolutePath());
        }
    }

    /**
     * 单个文件改名字
     * @param filePath
     */
    public static void changeFileName(String filePath){
        File oldFile = new File(filePath);

        String name = oldFile.getName();
        String parentPath = oldFile.getParentFile().getAbsolutePath();
        int i = name.lastIndexOf(".");

        System.out.println("name = " + name + ",lastIndex = " + i);
        String newName = name.substring(2,i) + "-" + author + name.substring(i);//
        //String newName = name.replace("许嵩","-许嵩");//
        File newFile = new File(parentPath + File.separator + newName);

        boolean isRenamed = oldFile.renameTo(newFile);
        if (isRenamed) {
            System.out.println("File renamed successfully.newName="+newName);
        } else {
            System.out.println("Failed to rename file.");
        }

    }


    // 创建视频路径
    public static String createMoviePath(String title) {
        String movieName = title + ".mp4";
        //创建路径
        File dir = new File(Constant.MOVIE_PATH);
        if (!dir.exists()) {
            dir.mkdirs();
        }
        String fileName = dir + File.separator + movieName;
        return fileName;
    }

    public static void inputStreamToFile(InputStream inputStream, String imagePath) {
        int i = 1;
        try {
            BufferedInputStream bis = new BufferedInputStream(inputStream);
            BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(imagePath));
            byte[] bytes = new byte[1024];
            int len = 0;
            while ((len = bis.read(bytes)) != -1) {
                bos.write(bytes, 0, len);
            }
            bis.close();
            bos.close();
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("inputStream转换异常");
        }
    }
}
