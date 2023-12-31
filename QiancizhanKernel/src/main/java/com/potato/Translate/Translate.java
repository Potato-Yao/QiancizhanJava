package com.potato.Translate;

import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import com.potato.Config;
import com.potato.Log.Log;

import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Translate是翻译器类，用于翻译文本
 * 该类使用的是百度翻译
 */
public class Translate
{
    private String APP_ID;
    private String SECURITY_KEY;

    TransApi transApi;

    /**
     * Translate构造器
     */
    public Translate()
    {
        // 如果用户没有提供APP_ID，那么就用我的
        if (Objects.equals(Config.baiduAppId, "") || Objects.equals(Config.baiduAppKey, ""))
        {
            this.APP_ID = "20200826000552094";
            this.SECURITY_KEY = "9Lzaco6skEAWVS9dEvK6";
            Log.i(getClass().toString(), "使用系统自带APP_ID开启翻译器");
        }
        else
        {
            this.APP_ID = Config.baiduAppId;
            this.SECURITY_KEY = Config.baiduAppKey;
            Log.v(getClass().toString(), "使用用户自定义APP_ID开启翻译器");
        }

        transApi = new TransApi(APP_ID, SECURITY_KEY);
    }

    /**
     * 翻译文本
     * <p> zh代表汉语，en代表英语，auto代表自动识别
     * <p> 注意：这个方法的返回值是翻译API给出的原始数据，即JSON格式的数据
     * <p> 若要只获取翻译的结果文本，请见{@link Translate}的filter方法
     *
     * @param query 需要翻译的内容
     * @param from  翻译的源自语言
     * @param to    翻译的去向语言
     * @return 翻译结果
     */
    public String translate(String query, String from, String to)
    {
        return getFormat(transApi.getTransResult(query, from, to));
    }

    /**
     * 这个过滤器用于从翻译API给出的JSON格式的原始数据提取出翻译后的文本
     *
     * @param source 需要提取的JSON字符串
     * @return 提取出的翻译后文本
     */
    public static String filter(String source)
    {
        JSONObject jsonObject = JSONObject.parse(source);
        JSONArray jsonArray = jsonObject.getJSONArray("trans_result");
        StringBuilder text = new StringBuilder();

        for (int i = 0; i < jsonArray.size(); i++)
        {
            text.append(jsonArray.getJSONObject(i).getString("dst"));
            text.append("\n");
        }

        return text.toString();
    }

    /**
     * 该方法用于将unicode形式的字符转成一般形式
     * <p> 这个方法是星火GPT告诉我的，我也不懂是怎么实现的
     *
     * @param unicodeString 需要转换的字符串
     * @return 转换结果
     */
    private static String getFormat(String unicodeString)
    {
        Pattern pattern = Pattern.compile("(\\\\u(\\p{XDigit}{4}))");
        Matcher matcher = pattern.matcher(unicodeString);
        char ch;

        while (matcher.find())
        {
            ch = (char) Integer.parseInt(matcher.group(2), 16);
            unicodeString = unicodeString.replace(matcher.group(1), String.valueOf(ch));
        }

        return unicodeString;
    }
}
