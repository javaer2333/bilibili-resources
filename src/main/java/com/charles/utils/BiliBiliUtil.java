package com.charles.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.charles.conf.Constant;
import org.jsoup.Connection;
import org.jsoup.Jsoup;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BiliBiliUtil {

    /**
     * 批量下载、单个下载MP4
     * @param avid
     * @param bvid
     * @param p 下载第几集，输入1,2,6-8代表第1、2、6、7、8集，默认下载第一集
     */
    public static void downloadBatch(Integer avid,String bvid,String p,String type){
        if(p==null){
            p="1";
        }
        long start = System.currentTimeMillis();
        //建立连接，先获取到json
        String cidJson = BiliBiliUtil.createConnectionToJson(avid,bvid);
        //根据json下载对应资源
        BiliBiliUtil.JsonGetCid(cidJson, avid, bvid,p,Constant.MP3.equals(type));

        long end = System.currentTimeMillis();
        System.err.println("总共耗时：" + (end - start) / 1000 + "s");
    }


    // 1. 建立连接拿到 json 数据
    public static String createConnectionToJson(Integer avid,String bvid) {

        String cidUrl = null;
        if(avid!=null) {
            cidUrl = "https://api.bilibili.com/x/web-interface/view?aid=" + avid;
        }else{
            cidUrl = "https://api.bilibili.com/x/web-interface/view?bvid=" + bvid;
        }
        //放完 movie地址
        String cidJson = BiliBiliUtil.createConnection(cidUrl);
        return cidJson;
    }

    // 2. 获取到的json选择出cid，只能选择出一个cid，还有标题
    public static void JsonGetCid(String cidJson,Integer avid,String bvid,String p,Boolean transferToMP3) {
        //转换成json
        JSONObject jsonObject = JSON.parseObject(cidJson);
        //cid
        JSONObject jsonData = jsonObject.getJSONObject("data");

        JSONArray jsonArray = jsonData.getJSONArray("pages");
        List<Integer> list = expandNumbers(p);//目标章节
        for (int i = 0; i < list.size(); i++) {
            Integer n = list.get(i);
            Map<String, Object> map = (Map)jsonArray.get(n-1);
            String title = (String) map.get("part");
            if(title.contains(" ") || title.contains(".")) {
                title = removeAllLeadingDots(title);
            }
            // 根据cid拼接成完整的请求参数,并执行下载操作
            System.out.println("当前下载进度：" + (i+1) + "/"+list.size()+"，title=" + title + "，avid=" + avid + "，cid=" + map.get("cid"));
            downloadMovie(avid, bvid, (Integer)map.get("cid"), title);
            if(transferToMP3){
                VideoToMP3Util.MP4ConverterMP3(Constant.MOVIE_PATH + File.separator + title + ".mp4",Constant.MP3_PATH + File.separator + title + ".mp3");
                File deleteFile = new File(Constant.MOVIE_PATH + File.separator + title + ".mp4");
                deleteFile.delete();
            }
            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 将输入的1,2,5-7格式数字，输出List<Integer>格式，内容为1,2,5,6,7
     * @param input
     * @return
     */
    public static List<Integer> expandNumbers(String input) {
        List<Integer> result = new ArrayList<>();
        String[] parts = input.split(",");

        for (String part : parts) {
            if (part.contains("-")) {
                String[] range = part.split("-");
                int start = Integer.parseInt(range[0]);
                int end = Integer.parseInt(range[1]);

                for (int i = start; i <= end; i++) {
                    result.add(i);
                }
            } else {
                result.add(Integer.parseInt(part));
            }
        }

        return result;
    }

    public static void downloadMovie(Integer avid,String bvid, Integer cid,String title) {
        Integer qn = Constant.QN;
        String paraUrl = null;
        if(avid!=null) {
            paraUrl = "https://api.bilibili.com/x/player/playurl?cid=" + cid + "&fnver=0&qn=" + qn + "&otype=json&avid=" + avid + "&fnval=2&player=1";
        }else{
            paraUrl = "https://api.bilibili.com/x/player/playurl?cid=" + cid + "&fnver=0&qn=" + qn + "&otype=json&bvid=" + bvid + "&fnval=2&player=1";
        }
        //System.out.println("构建的url为：" + paraUrl);
        System.out.println("开始下载avid=" + avid + "，cid=" + cid + "，title=" + title);
        // 获取到的是json，然后筛选出里面的视频资源：url
        String jsonText = BiliBiliUtil.createConnection(paraUrl);

        JSONObject jsonObject = JSON.parseObject(jsonText);
        JSONArray jsonArray = jsonObject.getJSONObject("data").getJSONArray("durl");

        Map<String, String> dUrlMap = (Map) jsonArray.get(0);
        String movieUrl = dUrlMap.get("url");

        // 根据获取的title 创建文件
        String moviePath = FileUtil.createMoviePath(title);
        //建立连接
        InputStream inputStream = createInputStream(movieUrl,avid);
        //开始流转换
        FileUtil.inputStreamToFile(inputStream, moviePath);
    }

    //0. 建立连接,返回页面中的json
    public static String createConnection(String url) {
        String jsonText = null;
        Connection connection = Jsoup.connect(url).timeout(3000).ignoreContentType(true);
        Map<String, String> heads = new HashMap<>();
        heads.put("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3");
        heads.put("Accept-Encoding", "gzip, deflate, br");
        heads.put("Accept-Language", "en,zh-CN;q=0.9,zh;q=0.8");
        heads.put("Cache-Control", "max-age=0");
        heads.put("Connection", "keep-alive");

        heads.put("Cookie", Constant.COOKIE);
        heads.put("Host", "api.bilibili.com");
        heads.put("Sec-Fetch-Mode", "navigate");
        heads.put("Sec-Fetch-Site", "none");
        heads.put("Sec-Fetch-User", "?1");
        heads.put("Upgrade-Insecure-Requests", "1");
        heads.put("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/77.0.3865.90 Safari/537.36");
        connection.headers(heads);
        try {
            jsonText = connection.get().text();
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("建立获取cid连接失败");
        }
        return jsonText;
    }



    // 3-2  建立URL连接请求
    private static InputStream createInputStream(String movieUrl,Integer avid) {
        InputStream inputStream = null;
        try {
            URL url = new URL(movieUrl);
            URLConnection urlConnection = url.openConnection();
            String refererUrl = "https://www.bilibili.com/video/av" + avid;
            urlConnection.setRequestProperty("Referer",refererUrl );
            urlConnection.setRequestProperty("Sec-Fetch-Mode", "no-cors");
            urlConnection.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/77.0.3865.90 Safari/537.36");
            urlConnection.setConnectTimeout(10 * 1000);

            inputStream = urlConnection.getInputStream();
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("获取inputStream失败");
        }
        return inputStream;
    }

    public static String removeAllLeadingDots(String str) {
        str = str.replace(".","");
        str = str.replace(" ","");
        return str;
    }
}
