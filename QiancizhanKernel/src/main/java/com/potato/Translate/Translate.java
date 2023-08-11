package com.potato.Translate;

import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import com.potato.Config;

import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Translate是翻译器
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
        }
        else
        {
            this.APP_ID = Config.baiduAppId;
            this.SECURITY_KEY = Config.baiduAppKey;
        }

        transApi = new TransApi(APP_ID, SECURITY_KEY);
    }

    /**
     * 翻译文本
     * zh代表汉语，en代表英语，auto代表自动识别
     *
     * @param query 需要翻译的内容
     * @param from  来自某语言
     * @param to    翻译为某语言
     * @return 翻译结果
     */
    public String translate(String query, String from, String to)
    {
        return getFormat(transApi.getTransResult(query, from, to));
    }

    /**
     * 过滤器，用于将提取中json字符串中的翻译结果
     *
     * @param source 需要提取的字符串
     * @return 翻译结果
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
     * 格式化程序，将unicode转成一般语言
     * 这个方法是星火GPT告诉我的，我也不懂是怎么实现的
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
