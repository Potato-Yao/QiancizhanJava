package com.potato.OCRUtil;

import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import com.potato.Word.Word;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class WordListParser
{
    private BaiduOCRParser parser;

    public WordListParser()
    {
        this.parser = new BaiduOCRParser();
    }

    public List<Word> recognizeToWordList(File image)
    {
        JSONArray wordArray = parser.recognizeImage(image);
        List<Word> wordList = new ArrayList<>();

        for (int i = 0; i < wordArray.size(); i++)
        {
            String wordName = wordArray.getJSONObject(i).getString("words");
            wordName = filterString(wordName);

            Word word = new Word.WordBuilder().name(wordName).build();
            wordList.add(word);
        }

        return wordList;
    }

    /**
     * 过滤文本
     * 识别出的文本可能包含一些奇怪的字符，该函数可以将其全部过滤掉
     *
     * @param text 需要过滤的文本
     * @return 过滤后的文本
     */
    private String filterString(String text)
    {
        String result = text;

        // 对于英语书，识别结果会包括单词后的音标
        // 因此识别后就会有/音标/的部分
        // 这里将其全部去掉
        int j = text.indexOf("/");
        if (j != -1)
        {
            result = text.substring(0, j);
        }

        // 这里去掉其它不是英语字母的文本
        // 之所以不在这里去除音标，是因为音标也被识别为了英语字母
        result = result.replace("[^a-zA-Z]", "");

        return result;
    }
}
