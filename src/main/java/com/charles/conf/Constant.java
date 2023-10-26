package com.charles.conf;

public class Constant {
    //TODO 自己的cookie
    public static final String COOKIE="【自己B站的cookie】";
    //下载的视频地址
    public static String MOVIE_PATH = "BiliBili/movie";
    //下载的MP3地址
    public static String MP3_PATH = "BiliBili/mp3";
    //TODO FFmpeg可执行文件路径,ffmpeg下载地址：https://evermeet.cx/ffmpeg/
    public static String FFMPEG_PATH = "/Users/fanjiawei/Documents/software/ffmpeg";
    //FFmpeg执行命令
    public static String FFMPEG_COMMAND = "%s -i %s -vn -acodec libmp3lame -ab 256k -ar 44100 -y %s";

    //下载格式，目前仅支持两种：MP4 和 MP3
    public static String MP4 = "mp4";
    public static String MP3 = "mp3";

    //下载视频质量 112=高清1080P+,   80=高清1080P,   64=高清720P,  32=清晰480P,  16=流畅360P，最高支持 1080p，1080P+不支持
    public static Integer QN = 80;



    public static String ZHOU_JIE_LUN_BV_ID = "BV1Fh411w76v";//周杰伦bvid
    public static String XUE_ZHI_QIAN_BV_ID = "BV1av4y1779z";//薛之谦bvid
    public static String XI_SONG_BV_ID = "BV1fK411R7JS";//许嵩bvid
    public static String CHEN_YI_XIN_BV_ID = "BV1Gt411U73U";//陈奕迅bvid
    public static String CHEN_LI_BV_ID = "BV1nN411674f";//陈粒bvid
    public static String DENG_ZI_QI_BV_ID = "BV1xN411x76o";//邓紫棋bvid
    public static String LIN_JIN_JIE_BV_ID = "BV1J14y1w7v5";//林俊杰bvid
    public static String REN_RAN_BV_ID = "BV1aa4y1j7ey";//任然bvid

    public static String WANG_YI_YUN1_BV_ID = "BV1Kv4y117sR";//网易云热歌1-99首bvid
    public static String WANG_YI_YUN2_BV_ID = "BV12g411n7Tr";//网易云热歌1-200首bvid 网易云热评10w+歌曲合集(带歌词 分集播放 持续更新）
    public static String WANG_YI_YUN3_BV_ID = "BV1f84y1w7j7";//网易云热歌1-93首bvid 网易云超过10W+评论 74首精选合集 静下心来欣赏美景
    public static String WANG_YI_YUN4_BV_ID = "BV1j24y1g7FD";//网易云热歌1-99首bvid 【带上耳机-感受无损】网易云热评10w+|100首合集（可后台播放）
    public static String WANG_YI_YUN5_BV_ID = "BV1J84y1D7hD";//网易云热歌1-99首bvid [2023网易云飙升榜歌单]精选100首宝藏歌曲 热搜歌曲 无损音质 华语经典歌曲合集
}
