package com.potato.OCRUtil;

import com.alibaba.fastjson2.JSONArray;
import com.baidu.aip.ocr.AipOcr;
import com.potato.Config;
import com.potato.Log.Log;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class BaiduOCRParser extends OCRParser
{
    private static String APP_ID;
    private static String APP_KEY;
    private static String APP_SECRET_KEY;
    private static AipOcr client;
    private static HashMap<String, String> options;

    public BaiduOCRParser()
    {
        // 设置APP_ID、APP_KEY和APP_SECRET_KEY，如果用户没有自定义那就用我的
        if (Config.ocrAppId.isEmpty() || Config.ocrAppKey.isEmpty() || Config.ocrSecretKey.isEmpty())
        {
            APP_ID = "35339593";
            APP_KEY = "Ad5C3C8bxLBeVn7YiDLy1o4e";
            APP_SECRET_KEY = "lqvCae8jX5QfYGpaavlo6NVIXDGbNF9x";
            Log.i(getClass().toString(), "使用系统自带APP_ID开启OCR");
        }
        else
        {
            APP_ID = Config.ocrAppId;
            APP_KEY = Config.ocrAppKey;
            APP_SECRET_KEY = Config.ocrSecretKey;
            Log.i(getClass().toString(), "使用用户自定义APP_ID开启OCR");
        }

        client = new AipOcr(APP_ID, APP_KEY, APP_SECRET_KEY);
        options = new HashMap<>(); // 设置
        options.put("recognize_granularity", "big");  // 识别粒度
        options.put("language_type", "ENG");  // 识别语言
        options.put("detect_direction", "false");  // 是否给出文字方向
        options.put("detect_language", "false");  // 是否给出文字语言
        options.put("vertexes_location", "false");  // 是否给出顶点位置
        options.put("probability", "true");  // 是否给出置信度
        options.put("paragraph", "false");  // 是否按照段落分开
    }

    @Override
    public List<String> recognizeImage(File image)
    {
        JSONObject res = client.general(image.getAbsolutePath(), options);
        String resText = res.toString(2);
        com.alibaba.fastjson2.JSONObject jsonObject =
                com.alibaba.fastjson2.JSONObject.parse(resText);

        JSONArray wordsArray = jsonObject.getJSONArray("words_result");
        List<String> result = new ArrayList<>();

        for (int i = 0; i < wordsArray.size(); i++)
        {
            result.add(wordsArray.getJSONObject(i).getString("words"));
        }

        Log.v(getClass().toString(), String.format("图像%s识别完成", image.getName()));
        return result;
    }
}
