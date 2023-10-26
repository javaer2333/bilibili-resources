package com.charles;

import com.charles.conf.Constant;
import com.charles.utils.BiliBiliUtil;
import com.charles.utils.VideoToMP3Util;

/**
 *  请在Constant类中填写自己 的cookie;
 */
public class TestDownload {

    public static void main(String[] args) {
        //downloadMovie();

        downloadMovieBatch();

        //downloadMP3Batch();

        //MP4ToMP3Batch();
    }

    /**
     * 测试下载单个视频
     */
    public static void downloadMovie() {
        BiliBiliUtil.downloadBatch(null,Constant.ZHOU_JIE_LUN_BV_ID,null, Constant.MP4);
    }

    /**
     * 测试批量下载视频
     */
    public static void downloadMovieBatch() {
        BiliBiliUtil.downloadBatch(null,Constant.ZHOU_JIE_LUN_BV_ID,"1,3-5,9,10", Constant.MP4);
    }

    /**
     * 测试批量下载MP3音乐
     */
    public static void downloadMP3Batch() {
        BiliBiliUtil.downloadBatch(null,Constant.ZHOU_JIE_LUN_BV_ID,"1,3-5,9,10", Constant.MP3);
    }

    /**
     * 批量MP4转MP3
     */
    public static void MP4ToMP3Batch() {
        VideoToMP3Util.MP4ToMP3Batch(Constant.MOVIE_PATH,Constant.MP3_PATH);
    }


}
