package com.gaoxian.Constant;

/**
 * 网络请求字段
 * Created by Administrator on 2015/9/12.
 */
public class NetWorkConstant {
    //服务端API的URL地址
    public static final String API_SERVER_URL = "http://125.64.14.237:5115";

    //登录
    public static final String name = "name";
    public static final String password = "password";
    public static final String apikey = "apikey";
    public static final String areacode = "areacode";


    //获取水质信息
    public static final String stid = "stid";

    //生产过程 界面的 获取生产控制数据信息 网络请求 后 回传的字段
    public static final String JSZD01 = "JSZD01";//进水浊度
    public static final String WNHD01 = "WNHD01";//污泥厚度01
    public static final String GSYL01 = "GSYL01";//供水压力01
    public static final String JSLL01 = "JSLL01";//进水流量
    public static final String CSLL01 = "CSLL01";//出水流量
    public static final String PSJYW01 = "PSJYW01";//配水井液位1号
    public static final String PSJYW02 = "PSJYW02";//配水井液位2号
    public static final String QSCYW01 = "QSCYW01";//清水池液位1号
    public static final String QSCYW02 = "QSCYW02";//清水池液位2号


    public static final int SCKZState_open = 1;//开
    public static final int SCKZState_close = 0;//关
    public static final int SCKZState_error = 2;//错误

    //生产过程 界面的 获取生产控制状态信息 网络请求 后 回传的字段
    public static final String PWF01 = "PWF01";//排污阀01
    public static final String PWF02 = "PWF02";//排污阀02
    public static final String PWF03 = "PWF03";//排污阀03
    public static final String PWF04 = "PWF04";//排污阀04
    public static final String PWF05 = "PWF05";//排污阀05
    public static final String PWF06 = "PWF06";//排污阀06
    public static final String PWF07 = "PWF07";//排污阀07
    public static final String PWF08 = "PWF08";//排污阀08
    public static final String PWF09 = "PWF09";//排污阀09
    public static final String PWF10 = "PWF10";//排污阀10
    public static final String PWF11 = "PWF11";//排污阀11
    public static final String PWF12 = "PWF12";//排污阀12
    public static final String PWF13 = "PWF13";//排污阀13
    public static final String PWF14 = "PWF14";//排污阀14
    public static final String PWF15 = "PWF15";//排污阀15
    public static final String PWF16 = "PWF16";//排污阀16
    public static final String PWF17 = "PWF17";//排污阀17
    public static final String PWF18 = "PWF18";//排污阀18
    public static final String KGF01 = "KGF01";//进水开关阀
    public static final String QSB01 = "QSB01";//取水泵01
    public static final String GSB01 = "GSB01";//供水泵01
    public static final String GSB02 = "GSB02";//供水泵01
    public static final String GSB03 = "GSB03";//供水泵03


}
